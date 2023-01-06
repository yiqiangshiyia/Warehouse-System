package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Goodstype;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linsuwen
 * @since 2023-01-05
 */
public interface GoodstypeService extends IService<Goodstype> {
    IPage pageCC(IPage<Goodstype> page, Wrapper wrapper);
}
