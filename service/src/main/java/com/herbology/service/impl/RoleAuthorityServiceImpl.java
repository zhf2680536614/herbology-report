package com.herbology.service.impl;

import com.herbology.entity.RoleAuthority;
import com.herbology.mapper.RoleAuthorityMapper;
import com.herbology.service.IRoleAuthorityService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-权限关系表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
@Service
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority> implements IRoleAuthorityService {

}
