package com.herbology.mapper;

import com.herbology.entity.RAConfig;
import com.herbology.entity.RoleAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色-权限关系表 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {
    List<RAConfig> query(String auth);
}
