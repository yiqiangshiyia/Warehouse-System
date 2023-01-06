package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linsuwen
 * @since 2023-01-02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage pageC(IPage<User> page);

    IPage pageCC(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
