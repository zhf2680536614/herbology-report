package com.herbology.mapper;

import com.herbology.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2025-07-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
