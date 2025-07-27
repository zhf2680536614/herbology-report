package com.herbology.service.impl;

import com.herbology.annotation.Auth;
import com.herbology.entity.Authority;
import com.herbology.mapper.AuthorityMapper;
import com.herbology.service.IAuthorityService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public void saveOrUpdateAuth(List<Authority> auths) {
		List<Authority> saveList = new ArrayList<>();
		List<Authority> updateList = new ArrayList<>();
		auths.forEach(auth -> {
			Authority q = getOne(QueryWrapper.create().eq(Authority::getName, auth.getName()));
			if (q != null) {
				auth.setId(q.getId());
				updateList.add(auth);
			} else {
				saveList.add(auth);
			}
		});
		saveBatch(saveList);
		updateBatch(updateList);
	}
}
