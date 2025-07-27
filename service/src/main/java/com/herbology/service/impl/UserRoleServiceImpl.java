package com.herbology.service.impl;

import com.herbology.entity.UserRole;
import com.herbology.mapper.UserRoleMapper;
import com.herbology.service.IUserRoleService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关系表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
