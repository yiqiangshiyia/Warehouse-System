package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goodstype;
import com.wms.entity.Storage;
import com.wms.service.GoodstypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器：物品分类管理
 * </p>
 *
 * @author linsuwen
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {

    @Autowired
    private GoodstypeService goodstypeService;
    
    /*
     * 新增物品分类
     * @author linsuwen
     * @date 2023/1/5 20:39
     */
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype){
        return goodstypeService.save(goodstype)?Result.success():Result.fail();
    }
    
    /*
     * 更新物品分类
     * @author linsuwen
     * @date 2023/1/5 20:41
     */
    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype){
        return goodstypeService.updateById(goodstype)?Result.success():Result.fail();
    }
    
    /*
     * 删除物品分类
     * @author linsuwen
     * @date 2023/1/5 20:43
     */
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return goodstypeService.removeById(id)?Result.success():Result.fail();
    }
    
    /*
     * 查询物品分类列表
     * @author linsuwen
     * @date 2023/1/5 21:06
     */
    @GetMapping("/list")
    public Result list(){
        List list = goodstypeService.list();
        return Result.success(list);
    }

    /*
     * 模糊查询：根据输入查询物品分类并以分页的形式展示
     * @author linsuwen
     * @date 2023/1/5 21:13
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<Goodstype> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goodstype> queryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like(Goodstype::getName,name);
        }

        IPage result = goodstypeService.pageCC(page,queryWrapper);
        return Result.success(result.getRecords(),result.getTotal());
    }
    
}
