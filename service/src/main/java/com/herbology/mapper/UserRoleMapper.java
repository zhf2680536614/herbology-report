package com.herbology.mapper;

import com.herbology.entity.UserRole;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户-角色关系表 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
