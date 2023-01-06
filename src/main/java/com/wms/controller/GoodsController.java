package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器：物品管理
 * </p>
 *
 * @author linsuwen
 * @since 2023-01-06
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    
    /*
     * 新增物品
     * @author linsuwen
     * @date 2023/1/6 12:12
     */
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        return goodsService.save(goods)?Result.success():Result.fail();
    }

    /*
     * 更新物品
     * @author linsuwen
     * @date 2023/1/6 13:22
     */
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){
        return goodsService.updateById(goods)?Result.success():Result.fail();
    }

    /*
     * 删除物品
     * @author linsuwen
     * @date 2023/1/6 13:24
     */
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return goodsService.removeById(id)?Result.success():Result.fail();
    }

    /*
     * 模糊查询：根据输入查询物品并以分页的形式展示
     * @author linsuwen
     * @date 2023/1/6 13:31
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String goodstype = (String)param.get("goodstype");
        String storage = (String)param.get("storage");

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like(Goods::getName,name);
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            queryWrapper.like(Goods::getGoodstype,goodstype);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.like(Goods::getStorage,storage);
        }

        IPage result = goodsService.pageCC(page,queryWrapper);
        return Result.success(result.getRecords(),result.getTotal());
    }
}
