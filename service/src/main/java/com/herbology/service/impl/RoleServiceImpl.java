package com.herbology.service.impl;

import com.herbology.entity.Role;
import com.herbology.mapper.RoleMapper;
import com.herbology.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
