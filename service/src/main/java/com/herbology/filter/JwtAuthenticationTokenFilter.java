package com.herbology.filter;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.herbology.config.security.SecurityConfig;
import com.herbology.config.security.UserDetailsImpl;
import com.herbology.constant.JwtClaimsConstant;
import com.herbology.context.UserContext;
import com.herbology.entity.Role;
import com.herbology.entity.User;
import com.herbology.entity.UserRole;
import com.herbology.enumeration.RoleAuthorityEnum;
import com.herbology.enumeration.UserTypeEnum;
import com.herbology.properties.JwtProperties;
import com.herbology.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;

    private final UserContext userContext;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            //获取请求路径
            String uri = request.getRequestURI();
            //判断当前路径是否为不需要认证的路径，如果是直接放行
            List<String> notAuthorities = SecurityConfig.notAuthorities;
            if (notAuthorities.contains(uri)) {
                System.out.println("无需认证");
                filterChain.doFilter(request, response);
                return;
            }
            String typename = uri.split("/")[2];
            Long userId;
            if (typename.equals(UserTypeEnum.USER.getValue())) {

                String token = request.getHeader(jwtProperties.getUserTokenName());
                if (ObjectUtil.isEmpty(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                //当前登录的是用户
                log.info("用户jwt校验{}", token);
                Claims claims = JwtUtil.parseJwt(jwtProperties.getUserSecretKey(), token);
                userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
                log.info("当前用户的id为{}", userId);
                Map<Long, String> map = new HashMap<>();
                map.put(userId, UserTypeEnum.USER.getValue());
                userContext.setUser(map);
            } else {
                String token = request.getHeader(jwtProperties.getManageTokenName());
                if (ObjectUtil.isEmpty(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                log.info("管理员jwt校验{}", token);
                Claims claims = JwtUtil.parseJwt(jwtProperties.getManageSecretKey(), token);
                userId = Long.valueOf(claims.get(JwtClaimsConstant.MANAGE_ID).toString());
                log.info("当前管理员的id为{}", userId);
                Map<Long, String> map = new HashMap<>();
                map.put(userId, UserTypeEnum.MANAGE.getValue());
                userContext.setUser(map);
            }

            User user = Db.lambdaQuery(User.class)
                    .eq(User::getId, userId)
                    .one();

            UserDetailsImpl loginUser = new UserDetailsImpl();
            loginUser.setUser(user);

            List<UserRole> list = Db.lambdaQuery(UserRole.class)
                    .eq(UserRole::getUserId, userId)
                    .eq(UserRole::getStatus, RoleAuthorityEnum.NORMAL.getKey())
                    .list();
            List<Long> roleIdList = list.stream().map(UserRole::getRoleId).toList();

            List<String> roleNameList = Db.lambdaQuery(Role.class)
                    .in(Role::getId, roleIdList)
                    .eq(Role::getStatus, RoleAuthorityEnum.NORMAL.getKey())
                    .list()
                    .stream()
                    .map(Role::getName)
                    .toList();

            List<GrantedAuthority> authorities = roleNameList.stream()
                    .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
            //如果是有效的jwt，那么设置该用户为认证后的用户
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }catch (ArrayIndexOutOfBoundsException e) {
            //response.setStatus(HttpServletResponse.SC_FOUND);
        }
    }
}
