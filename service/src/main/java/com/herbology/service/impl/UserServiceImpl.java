package com.herbology.service.impl;

import com.herbology.entity.User;
import com.herbology.mapper.UserMapper;
import com.herbology.service.IUserService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
