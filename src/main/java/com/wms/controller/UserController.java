package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.service.MenuService;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器：用户管理和管理员管理模块
 * </p>
 *
 * @author linsuwen
 * @since 2023-01-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    /*
     * 查询全部用户
     * @author linsuwen
     * @date 2023/1/2 19:26
     */
    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    /*
     * 根据账号查找用户
     * @author linsuwen
     * @date 2023/1/4 14:53
     */
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list = userService.lambdaQuery()
                .eq(User::getNo,no)
                .list();
        return list.size()>0?Result.success(list):Result.fail();
    }

    /*
     * 新增用户
     * @author linsuwen
     * @date 2023/1/2 19:11
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.success():Result.fail();
    }

    /*
     * 更新用户
     * @author linsuwen
     * @date 2023/1/2 19:11
     */
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user)?Result.success():Result.fail();
    }

    /*
     * 用户登录：登录的时候一并将菜单信息也查询出来
     * @author linsuwen
     * @date 2023/1/3 14:08
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        //匹配账号和密码
        List list = userService.lambdaQuery()
                .eq(User::getNo,user.getNo())
                .eq(User::getPassword,user.getPassword())
                .list();

        if(list.size()>0){
            User user1 = (User)list.get(0);
            List<Menu> menuList = menuService.lambdaQuery()
                    .like(Menu::getMenuright,user1.getRoleId())
                    .list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menuList);
            return Result.success(res);
        }
        return Result.fail();
    }

    /*
     * 修改用户
     * @author linsuwen
     * @date 2023/1/4 15:02
     */
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }
    
    /*
     * 新增或修改：存在用户则修改，否则新增用户
     * @author linsuwen
     * @date 2023/1/2 19:12
     */
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody User user){
        return userService.saveOrUpdate(user)?Result.success():Result.fail();
    }

    /*
     * 删除用户
     * @author linsuwen
     * @date 2023/1/2 19:15
     */
    @GetMapping("/del")
    public Result delete(Integer id){
        return userService.removeById(id)?Result.success():Result.fail();
    }

    /*
     * 模糊查询
     * @author linsuwen
     * @date 2023/1/2 19:36
     */
    @PostMapping("/listP")
    public Result query(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getName())){
            wrapper.like(User::getName,user.getName());
        }
        return Result.success(userService.list(wrapper));
    }

    /*
     * 分页查询
     * @author linsuwen
     * @date 2023/1/2 19:48
     */
//    @PostMapping("/listPage")
//    public Result page(@RequestBody QueryPageParam query){
//        HashMap param = query.getParam();
//        String name = (String)param.get("name");
//
//        Page<User> page = new Page();
//        page.setCurrent(query.getPageNum());
//        page.setSize(query.getPageSize());
//
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.like(User::getName,name);
//
//        IPage result = userService.page(page,wrapper);
//        return Result.success(result.getRecords(),result.getTotal());
//    }

    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name=>"+(String)param.get("name"));

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println("total=>"+result.getTotal());

        return result.getRecords();
    }

    /*
     * 查询功能：根据前端表单输入的信息或者下拉框选择查询用户，并以分页的形式返回前端
     * @author linsuwen
     * @date 2023/1/4 20:28
     */
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String sex = (String)param.get("sex");
        String roleId = (String)param.get("roleId");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }
        if(StringUtils.isNotBlank(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,roleId);
        }

        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=>"+result.getTotal());

        return Result.success(result.getRecords(),result.getTotal());
    }

}
