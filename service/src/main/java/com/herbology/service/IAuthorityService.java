package com.herbology.service;

import com.herbology.entity.Authority;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
public interface IAuthorityService extends IService<Authority> {
	void saveOrUpdateAuth(List<Authority> auths);
}
