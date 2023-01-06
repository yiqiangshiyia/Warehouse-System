package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Storage;
import com.wms.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器：仓库管理模块
 * </p>
 *
 * @author linsuwen
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    /*
     * 新增仓库
     * @author linsuwen
     * @date 2023/1/5 19:36
     */
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage){
        return storageService.save(storage)?Result.success():Result.fail();
    }
    
    /*
     * 更新仓库
     * @author linsuwen
     * @date 2023/1/5 19:38
     */
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage){
        return storageService.updateById(storage)?Result.success():Result.fail();
    }
    
    /*
     * 删除仓库
     * @author linsuwen
     * @date 2023/1/5 19:40
     */
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return storageService.removeById(id)?Result.success():Result.fail();
    }

    /*
     * 查询仓库列表
     * @author linsuwen
     * @date 2023/1/5 19:42
     */
    @GetMapping("/list")
    public Result list(){
        List list = storageService.list();
        return Result.success(list);
    }

    /*
     * 模糊查询：根据输入查询仓库并以分页的形式展示
     * @author linsuwen
     * @date 2023/1/5 19:43
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<Storage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like(Storage::getName,name);
        }

        IPage result = storageService.pageCC(page,queryWrapper);
        return Result.success(result.getRecords(),result.getTotal());
    }

}
