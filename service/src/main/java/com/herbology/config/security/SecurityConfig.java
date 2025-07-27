package com.herbology.config.security;

import com.herbology.entity.Authority;
import com.herbology.entity.RAConfig;
import com.herbology.entity.RoleAuthorities;
import com.herbology.enumeration.RoleAuthorityEnum;
import com.herbology.filter.JwtAuthenticationTokenFilter;
import com.herbology.mapper.RoleAuthorityMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity //此注解开启 Security 的 Web 安全功能
@RequiredArgsConstructor
public class SecurityConfig {

    private final RoleAuthorityMapper roleAuthorityMapper;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    public static List<RoleAuthorities> roleAuthorities = new ArrayList<>();

    public static List<String> notAuthorities = new ArrayList<>();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        roleAuthorities = getRoleAuthorities();
        notAuthorities = getNotAuthorities();
        http
                // 开启授权保护
                .authorizeHttpRequests((authorize) -> {
                            //不需要认证的地址
                            authorize.requestMatchers(notAuthorities.toArray(new String[0])).permitAll();
                            //基于角色的访问控制
                            roleAuthorities.forEach(ra -> authorize.requestMatchers(ra.getAuthorities().toArray(new String[0])).hasRole(ra.getRole()));
                            // 对所有请求开启授权保护
                            authorize.anyRequest().authenticated();
                        }
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//基于token不需要session
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    //获取需要认证的角色与权限信息
    public List<RoleAuthorities> getRoleAuthorities() {
        List<RAConfig> roleAuthorities = roleAuthorityMapper.query(RoleAuthorityEnum.NORMAL.getKey());
        if (roleAuthorities.isEmpty()) {
            List<RoleAuthorities> empty = new ArrayList<>();
            List<String> l = new ArrayList<>();
            l.add("/empty");
            empty.add(new RoleAuthorities("EMPTY", l));
            return empty;
        }
        Map<String, List<String>> collect = roleAuthorities.stream()
                .collect(Collectors.groupingBy(
                        RAConfig::getRole,
                        Collectors.mapping(RAConfig::getAuthority, Collectors.toList())
                ));
        return collect.entrySet().stream()
                .map(entry -> new RoleAuthorities(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    //获取不需要认证的角色与权限信息
    public List<String> getNotAuthorities() {
        List<String> list = Db.selectListByQuery(
                        QueryWrapper.create(new Authority())
                                .eq(Authority::getAuth, RoleAuthorityEnum.FAIL.getKey())
                                .eq(Authority::getStatus, RoleAuthorityEnum.NORMAL.getKey())
                ).stream().map(row -> row.toEntity(Authority.class).getName()).collect(Collectors.toList());
        if (list.isEmpty()) {
            List<String> l = new ArrayList<>();
            l.add("/**");
            return l;
        }
        return list;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
