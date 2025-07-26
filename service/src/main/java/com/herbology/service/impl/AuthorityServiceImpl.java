package com.herbology.service.impl;

import com.herbology.entity.Authority;
import com.herbology.mapper.AuthorityMapper;
import com.herbology.service.IAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService {

}
