package com.herbology.config.security;

import cn.hutool.core.util.ObjectUtil;

import com.herbology.entity.User;
import com.herbology.enumeration.UserStatusEnum;
import com.herbology.exception.BaseException;
import com.herbology.mapper.UserMapper;
import com.herbology.message.LoginMessage;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userLoginMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userLoginMapper.selectOneByQuery(
                QueryWrapper.create()
                        .eq(User::getUsername, username)
        );
        
        if (ObjectUtil.isNull(user)) {
            throw new BaseException(LoginMessage.USER_NOT_EXIST);
        }
        
        // 判断账号是否冻结
        if (ObjectUtil.equals(user.getStatus(), UserStatusEnum.FAIL.getKey())) {
            throw new BaseException(LoginMessage.USER_FAIL);
        }
        
        return new UserDetailsImpl(user);
    }
}
