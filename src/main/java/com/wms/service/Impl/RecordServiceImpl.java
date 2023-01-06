package com.wms.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Record;
import com.wms.mapper.RecordMapper;
import com.wms.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linsuwen
 * @since 2023-01-06
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public IPage pageCC(IPage<Record> page, Wrapper wrapper) {
        return recordMapper.pageCC(page,wrapper);
    }
}
