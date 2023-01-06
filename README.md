# 1. 项目概述

项目是基于SpringBoot+Vue前后端分离的仓库管理系统

后端：SpringBoot + MybatisPlus

前端：Node.js + Vue + element-ui

数据库：mysql



# 2. 创建后端项目

## 2.1 创建模块

1. 创建项目模块

   ![QQ截图20230102145345](https://img.yiqiangshiyia.cn/blog/QQ%E6%88%AA%E5%9B%BE20230102145345.png)

2. 导入项目依赖

   pom.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
   	<modelVersion>4.0.0</modelVersion>
   	<parent>
   		<groupId>org.springframework.boot</groupId>
   		<artifactId>spring-boot-starter-parent</artifactId>
   		<version>2.7.5</version>
   		<relativePath/> <!-- lookup parent from repository -->
   	</parent>
   	<groupId>com.wms</groupId>
   	<artifactId>Warehouse-System</artifactId>
   	<version>0.0.1-SNAPSHOT</version>
   	<name>Warehouse-System</name>
   	<description>Warehouse management system</description>
   	<properties>
   		<java.version>11</java.version>
   	</properties>
   	<dependencies>
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter</artifactId>
   		</dependency>
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-test</artifactId>
   			<scope>test</scope>
   		</dependency>
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-web</artifactId>
   		</dependency>
   		<dependency>
   			<groupId>mysql</groupId>
   			<artifactId>mysql-connector-java</artifactId>
   			<version>8.0.30</version>
   			<scope>runtime</scope>
   		</dependency>
   		<dependency>
   			<groupId>org.projectlombok</groupId>
   			<artifactId>lombok</artifactId>
   			<optional>true</optional>
   		</dependency>
   		<!--mybatisPlus-->
   		<dependency>
   			<groupId>com.baomidou</groupId>
   			<artifactId>mybatis-plus-boot-starter</artifactId>
   			<version>3.4.1</version>
   		</dependency>
   		<!--代码生成器-->
   		<dependency>
   			<groupId>com.baomidou</groupId>
   			<artifactId>mybatis-plus-generator</artifactId>
   			<version>3.4.1</version>
   		</dependency>
   		<dependency>
   			<groupId>org.freemarker</groupId>
   			<artifactId>freemarker</artifactId>
   			<version>2.3.30</version>
   		</dependency>
   		<dependency>
   			<groupId>com.spring4all</groupId>
   			<artifactId>spring-boot-starter-swagger</artifactId>
   			<version>1.5.1.RELEASE</version>
   		</dependency>
   	</dependencies>
   
   	<build>
   		<plugins>
   			<plugin>
   				<groupId>org.springframework.boot</groupId>
   				<artifactId>spring-boot-maven-plugin</artifactId>
   			</plugin>
   		</plugins>
   	</build>
   
   </project>
   ```



## 2.2 加入MybatisPlus支持

1. 导入依赖

   ```xml
   <!--mybatisPlus-->
   <dependency>
       <groupId>com.baomidou</groupId>
       <artifactId>mybatis-plus-boot-starter</artifactId>
       <version>3.5.2</version>
   </dependency>
   ```

2. 创建并连接数据库

   ![QQ截图20230102150422](https://img.yiqiangshiyia.cn/blog/QQ%E6%88%AA%E5%9B%BE20230102150422.png)

3. 配置端口和数据源

   application.yml

   ```yml
   server:
     port: 8002
   
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/wms?useUnicode=true&characterEncoding=utf-8&serveTimezone=UTC
       driver-class-name: com.mysql.cj.jdbc.Driver
       username: root
       password: 123456
   ```

4. 编写实体类

   ```java
   @Data
   public class User {
       private int id;
       private String no;
       private String name;
       private String password;
       private int sex;
       private int roleId;
       private String phone;
       private String isvalid;
   }
   ```

5. 编写Mapper接口

   ```java
   @Mapper
   public interface UserMapper extends BaseMapper<User> {
       public List<User> selectAll();
   }
   ```

6. 编写Service接口

   ```java
   public interface UserService extends IService<User> {
       public List<User> selectAll();
   }
   ```

7. 编写Service实现类

   ```java
   @Service
   public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
   
       @Autowired
       private UserMapper userMapper;
   
       public List<User> selectAll(){
           return userMapper.selectAll();
       }
   }
   ```

8. 编写配置文件

   UserMapper.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.wms.mapper.UserMapper">
       <select id="selectAll" resultType="com.wms.entity.User">
           select * from user
       </select>
   </mapper>
   ```

9. 编写测试代码

   ```java
   @RestController
   public class testController {
       @Autowired
       private UserService userService;
   
       @GetMapping
       public List<User> test(){
           return userService.selectAll();
       }
   }
   ```

10. 测试结果

    ![QQ截图20230102161434](https://img.yiqiangshiyia.cn/blog/QQ%E6%88%AA%E5%9B%BE20230102161434.png)



# 3. 代码生成器

> 简化开发：删除之前编写的实体类、接口、实现类、配置文件以及测试类，利用MyBatisPlus代码生成器自动生成代码

1. 导入依赖

   > 注意：MybatisPlus版本用3.4.1，3.5版本的MybatisPlus会报错！

   ```xml
   <!--代码生成器-->
   <dependency>
       <groupId>com.baomidou</groupId>
       <artifactId>mybatis-plus-generator</artifactId>
       <version>3.4.1</version>
   </dependency>
   <dependency>
       <groupId>org.freemarker</groupId>
       <artifactId>freemarker</artifactId>
       <version>2.3.30</version>
   </dependency>
   ```

2. 编写代码生成器

   > 参考MybatisPlus官网：https://baomidou.com/pages/d357af/

   ```java
   package com.wms.common;
   
   import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
   import com.baomidou.mybatisplus.core.toolkit.StringPool;
   import com.baomidou.mybatisplus.core.toolkit.StringUtils;
   import com.baomidou.mybatisplus.generator.AutoGenerator;
   import com.baomidou.mybatisplus.generator.InjectionConfig;
   import com.baomidou.mybatisplus.generator.config.*;
   import com.baomidou.mybatisplus.generator.config.po.TableInfo;
   import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
   import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
   
   import java.util.ArrayList;
   import java.util.List;
   import java.util.Scanner;
   
   public class CodeGenerator {
   
       /**
        * <p>
        * 读取控制台内容
        * </p>
        */
       public static String scanner(String tip) {
           Scanner scanner = new Scanner(System.in);
           StringBuilder help = new StringBuilder();
           help.append("请输入" + tip + "：");
           System.out.println(help.toString());
           if (scanner.hasNext()) {
               String ipt = scanner.next();
               if (StringUtils.isNotBlank(ipt)) {
                   return ipt;
               }
           }
           throw new MybatisPlusException("请输入正确的" + tip + "！");
       }
   
       public static void main(String[] args) {
           // 代码生成器
           AutoGenerator mpg = new AutoGenerator();
   
           // 全局配置
           GlobalConfig gc = new GlobalConfig();
           String projectPath = System.getProperty("user.dir");
           gc.setOutputDir(projectPath + "/src/main/java");
           gc.setAuthor("linsuwen");
           gc.setOpen(false);
           gc.setSwagger2(true); //实体属性 Swagger2 注解
           gc.setBaseResultMap(true); // XML ResultMap
           gc.setBaseColumnList(true); // XML columList
           //去掉service接口首字母的I, 如DO为User则叫UserService
           gc.setServiceName("%sService");
           mpg.setGlobalConfig(gc);
   
           // 数据源配置
           DataSourceConfig dsc = new DataSourceConfig();
           dsc.setUrl("jdbc:mysql://localhost:3306/wms?useUnicode=true&characterEncoding=utf-8&serveTimezone=UTC");
           // dsc.setSchemaName("public");
           dsc.setDriverName("com.mysql.cj.jdbc.Driver");
           dsc.setUsername("root");
           dsc.setPassword("123456");
           mpg.setDataSource(dsc);
   
           // 包配置
           PackageConfig pc = new PackageConfig();
           //pc.setModuleName(scanner("模块名"));
           //模块配置
           pc.setParent("com.wms")
                   .setEntity("entity")
                   .setMapper("mapper")
                   .setService("service")
                   .setServiceImpl("service.Impl")
                   .setController("controller");
           mpg.setPackageInfo(pc);
   
           // 自定义配置
           InjectionConfig cfg = new InjectionConfig() {
               @Override
               public void initMap() {
                   // to do nothing
               }
           };
   
           // 如果模板引擎是 freemarker
           String templatePath = "/templates/mapper.xml.ftl";
           // 如果模板引擎是 velocity
           // String templatePath = "/templates/mapper.xml.vm";
   
           // 自定义输出配置
           List<FileOutConfig> focList = new ArrayList<>();
           // 自定义配置会被优先输出
           focList.add(new FileOutConfig(templatePath) {
               @Override
               public String outputFile(TableInfo tableInfo) {
                   // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                   return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                           + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
               }
           });
   
           cfg.setFileOutConfigList(focList);
           mpg.setCfg(cfg);
   
           // 配置模板
           TemplateConfig templateConfig = new TemplateConfig();
   
           templateConfig.setXml(null);
           mpg.setTemplate(templateConfig);
   
           // 策略配置
           StrategyConfig strategy = new StrategyConfig();
           strategy.setNaming(NamingStrategy.underline_to_camel);
           strategy.setColumnNaming(NamingStrategy.underline_to_camel);
           //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
           strategy.setEntityLombokModel(true);
           strategy.setRestControllerStyle(true);
           // 公共父类
           //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
           // 写于父类中的公共字段
           //strategy.setSuperEntityColumns("id");
           strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
           strategy.setControllerMappingHyphenStyle(true);
           //strategy.setTablePrefix(pc.getModuleName() + "_");
           mpg.setStrategy(strategy);
           mpg.setTemplateEngine(new FreemarkerTemplateEngine());
           mpg.execute();
       }
   
   }
   ```

3. 运行代码生成器自动生成代码

   生成结果：

   ![QQ截图20230102185756](https://img.yiqiangshiyia.cn/blog/QQ%E6%88%AA%E5%9B%BE20230102185756.png)



# 4. 用户的增删改查

## 4.1 新增用户

```java
/*
 * 新增用户
 * @author linsuwen
 * @date 2023/1/2 19:11
 */
@PostMapping("/save")
public boolean save(@RequestBody User user){
    return userService.save(user);
}
```



## 4.2 删除用户

```java
/*
 * 删除用户
 * @author linsuwen
 * @date 2023/1/2 19:15
 */
@GetMapping("/delete")
public boolean delete(Integer id){
    return userService.removeById(id);
}
```



## 4.3 更新用户

```java
/*
 * 更新用户
 * @author linsuwen
 * @date 2023/1/2 19:11
 */
@PostMapping("/update")
public boolean update(@RequestBody User user){
    return userService.updateById(user);
}
```

```java
/*
 * 新增或修改：存在用户则修改，否则新增用户
 * @author linsuwen
 * @date 2023/1/2 19:12
 */
@PostMapping("/saveOrUpdate")
public boolean saveOrUpdate(@RequestBody User user){
    return userService.saveOrUpdate(user);
}
```



## 4.4 查询用户

```java
/*
 * 查询全部用户
 * @author linsuwen
 * @date 2023/1/2 19:26
 */
@GetMapping("/list")
public List<User> list(){
    return userService.list();
}
```

```java
/*
 * 模糊查询
 * @author linsuwen
 * @date 2023/1/2 19:36
 */
@PostMapping("/listP")
public List<User> query(@RequestBody User user){
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(User::getName,user.getName());
    return userService.list(wrapper);
}
```



# 5. 分页的处理

1. 参数的封装

   > 注：可以不封装，在controller层用HashMap接收参数

   ```java
   package com.wms.common;
   /*
    * 分页参数的封装类
    * @author linsuwen
    * @date 2023/1/2 19:53
    */
   @Data
   public class QueryPageParam {
       //设置默认值
       private static int PAGE_SIZE=20;
       private static int PAGE_NUM=1;
   
       private int pageSize=PAGE_SIZE;
       private int pageNum=PAGE_NUM;
   
       private HashMap param = new HashMap();
   
   }
   ```

2. 添加分页拦截器

   ```java
   /*
    * MybatisPlus分页拦截器
    * @author linsuwen
    * @date 2023/1/2 20:06
    */
   @Configuration
   public class MybatisPlusConfig {
       @Bean
       public MybatisPlusInterceptor mybatisPlusInterceptor() {
           MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
           interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
           return interceptor;
       }
   }
   ```

3. 编写分页的Mapper方法

   ```java
   /*
    * 分页查询
    * @author linsuwen
    * @date 2023/1/2 19:48
    */
   @PostMapping("/lsitPage")
   public Result page(@RequestBody QueryPageParam query){
       HashMap param = query.getParam();
       String name = (String)param.get("name");
   
       Page<User> page = new Page();
       page.setCurrent(query.getPageNum());
       page.setSize(query.getPageSize());
   
       LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
       wrapper.like(User::getName,name);
   
       IPage result = userService.page(page,wrapper);
       return Result.success(result.getRecords(),result.getTotal());
   }
   ```

4. 自定义SQL使用Wrapper



# 6. 返回前端数据的封装

> 让前端收到统一的数据，方便处理

```java
package com.wms.common;
import lombok.Data;
/*
 * 返回前端统一数据的封装类
 * @author linsuwen
 * @date 2023/1/2 20:36
 */
@Data
public class Result {

    private int code;  //编码 200/400
    private String msg;  //成功/失败
    private Long total;  //总记录数
    private Object data;  //数据

    public static Result fail(){
        return result(400,"失败",0L,null);
    }
    
    public static Result success(){
        return result(200,"成功",0L,null);
    }

    public static Result success(Object data){
        return result(200,"成功",0L,data);
    }

    public static Result success(Object data,Long total){
        return result(200,"成功",total,data);
    }

    private static Result result(int code,String msg,Long total,Object data){
        Result res = new Result();
        res.setData(data);
        res.setMsg(msg);
        res.setCode(code);
        res.setTotal(total);
        return res;
    }

}
```



# 7. 创建前端项目

1. 创建一个名为 Warehouse-System-Web 的工程

   ```shell
   npm init  #项目初始化命令
   #如果想直接生成 package.json 文件，那么可以使用命令
   npm init -y
   ```

2. 安装依赖

   ```shell
   #进入工程目录
   cd Warehouse-System-Web
   #安装vue-router
   npm install vue-router --save-dev
   #安装element-ui
   npm i element-ui -S
   #安装依赖
   npm install
   #安装SASS加载器
   cnpm install sass-loader node-sass --save-dev
   ```

3. 启动项目

   ```shell
   #启动测试
   npm run serve
   ```

   ![QQ截图20230102220417](https://img.yiqiangshiyia.cn/blog/QQ%E6%88%AA%E5%9B%BE20230102220417.png)



# 8. 编写前端页面

## 8.1 搭建页面布局

> 参考Element-ui Container 布局容器：https://element.eleme.cn/#/zh-CN/component/container

Index.vue：整体页面布局 Vue 组件



## 8.2 页面布局拆分

从 Index.vue 中拆分出 Aside.vue 和 Header.vue 组件后，然后在 Index.vue 再导入拆分出去的组件

```js
import Aside from "./Aside";
import Header from "./Header";
```



## 8.3 编写头部页面

> 编写步骤：

1. dropdown下拉
2. 菜单伸缩图标
3. 欢迎字样
4. 去除背景，加入下拉框

> 代码实现：

```vue
<!--头部组件-->
<template>
    <div style="display: flex;line-height: 60px;">
        <div style="margin-top: 8px;">
            <!--菜单伸缩-->
            <i :class="icon" style="font-size: 20px;cursor: pointer;" @click="collapse"></i>
        </div>
        <div style="flex: 1;text-align: center;font-size: 34px;">、
            <!--欢迎字样-->
            <span>欢迎来到仓库管理系统</span>
        </div>
        <el-dropdown>
            <!--dropdown下拉-->
            <span>{{user.name}}</span><i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>

    </div>
</template>

<script>
    export default {
        //...
    }
</script>

<style scoped>

</style>
```



## 8.4 编写导航菜单页面

```vue
<!--侧边菜单栏组件-->
<template>
    <el-menu
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            style="height: 100%;"
            default-active="/Home"
            :collapse="isCollapse"
            :collapse-transition="false"
            router
    >
       <el-menu-item index="/Home">
           <i class="el-icon-s-home"></i>
           <span slot="title">首页</span>
       </el-menu-item>

        <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
            <i :class="item.menuicon"></i>
            <span slot="title">{{item.menuname}}</span>
        </el-menu-item>

    </el-menu>
</template>

<script>
    export default {
        //...
    }
</script>

<style scoped>

</style>
```



## 8.5 菜单导航页面伸缩

菜单导航页面伸缩思路：

1. header点击图标提交
2. 父组件改变
3. aside子组件（collapse）



# 9. 安装axios与跨域处理

> **Axios：**Axios是一个基于promise 的 HTTP 库，可以用在浏览器和 node.js中。
>
> **Ajax：**Ajax即**A**synchronous **J**avascript **A**nd **X**ML（异步JavaScript和[ XML]）在 2005年被Jesse James Garrett提出的新术语，用来描述一种使用现有技术集合的'新'方法，包括：HTML 或 XHTML，CSS，JavaScript，DOM，XML，XSLT以及最重要的 XMLHttpRequest。使用Ajax技术网页应用能够快速地将增量更新呈现在用户界面上，而不需要重载（刷新）整个页面，这使得程序能够更快地回应用户的操作。
>
> **Axios与Ajax的区别：**
>
> 1. axios是一个基于Promise的HTTP库，而ajax是对原生XHR的封装。
> 2. ajax技术实现了局部数据的刷新，而axios实现了对ajax的封装。

> **什么是跨域访问：**说到跨域访问，必须先解释一个名词：同源策略。所谓同源策略就是在浏览器端出于安全考量，向服务端发起请求必须满足：协议相同、Host（ip）相同、端口相同的条件，否则访问将被禁止，该访问也就被称为跨域访问。
>
> 虽然跨域访问被禁止之后，可以在一定程度上提高了应用的安全性，但也为开发带来了一定的麻烦。比如：我们开发一个前后端分离的易用，页面及js部署在一个主机的nginx服务中，后端接口部署在一个tomcat应用容器中，当前端向后端发起请求的时候一定是不符合同源策略的，也就无法访问。
>
> **SpringBoot下解决跨域问题的四种方式：**
>
> 1. 使用CorsFilter进行全局跨域配置
> 2. 重写WebMvcConfigurer的addCorsMappings方法（全局跨域配置）
> 3. 使用CrossOrigin注解（局部跨域配置）
> 4. 使用HttpServletResponse设置响应头（局部跨域配置）
>
> 参考文章：https://cloud.tencent.com/developer/news/472954

**安装axios与跨域处理：**

1. 安装axios

   ```shell
   npm install axios --save
   ```

2. 在main.js全局引⼊axios

   ```shell
   import axios from "axios";
   Vue.prototype.$axios =axios;
   ```

3. 解决跨域问题

   重写WebMvcConfigurer的addCorsMappings方法（全局跨域配置）：

   ```java
   package com.wms.common;
   /*
    * 解决跨域问题：重写WebMvcConfigurer的addCorsMappings方法（全局跨域配置）
    * @author linsuwen
    * @date 2023/1/3 1:30
    */
   @Configuration
   public class CorsConfig implements WebMvcConfigurer {
    
       @Override
       public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/**")
                   //是否发送Cookie
                   .allowCredentials(true)
                   //放行哪些原始域
                   .allowedOriginPatterns("*")
                   .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                   .allowedHeaders("*")
                   .exposedHeaders("*");
       }
   }
   ```

4. 测试请求

   get请求的使⽤：

   ```js
   this.$axios.get('http://localhost:8002/user/list').then(res=>{
       console.log(res)
   })
   ```

   post请求的使用：

   ```js
   this.$axios.post('http://localhost:8002/user/query',{}).then(res=>{
       console.log(res)
   })
   ```

5. 将地址设置为全局

   main.js

   ```js
   Vue.prototype.$httpUrl='http://localhost:8002'  //将地址设置为全局
   ```



# 10. 登录退出功能

## 10.1 登录功能

1. 编写登录页面

   Login.vue

   ![QQ截图20230103144539](https://img.yiqiangshiyia.cn/blog/QQ%E6%88%AA%E5%9B%BE20230103144539.png)

2. 后台查询登录代码

   ```java
   /*
    * 用户登录
    * @author linsuwen
    * @date 2023/1/3 14:08
    */
   @PostMapping("/login")
   public Result login(@RequestBody User user){
       //匹配账号和密码
       List<User> list = userService.lambdaQuery()
               .eq(User::getNo,user.getNo())
               .eq(User::getPassword,user.getPassword())
               .list();
       return list.size()>0?Result.success(list.get(0)):Result.fail();
   }
   ```

3. 登录页面的路由跳转

   安装路由插件

   ```shell
   npm i vue-router@3.5.4
   ```

   创建路由文件（router目录下的index.js文件），访问路由跳转到登录页面

   ```js
   import VueRouter from 'vue-router';
   
   const routes = [
       {
           path:'/',
           name:'login',
           component:()=>import('../components/Login')
       }
   ]
   
   const router = new VueRouter({
       mode:'history',
       routes
   })
   
   export  default router;
   ```

   在main.js中注册路由

   ```js
   import VueRouter from 'vue-router';
   import router from './router';
   Vue.use(VueRouter);
   new Vue({
     router,
     render: h => h(App),
   }).$mount('#app')
   ```

4. 主页的路由（接收路由）

   App.vue

   ```vue
   <template>
     <div id="app">
        <router-view/>
     </div>
   </template>
   
   <script>
   
   
   export default {
     name: 'App',
     components: {
   
     }
   }
   </script>
   
   <style>
   #app {
       height: 100%;
   }
   </style>
   ```

5. 登录成功后页面跳转到首页

   ...

   http://localhost:8081/ 跳转到 http://localhost:8081/index



## 10.2 退出登录功能

1. 展示名字（Header.vue）

   ```vue
   <el-dropdown>
       <!--dropdown下拉-->
       <span>{{user.name}}</span><i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
       <el-dropdown-menu slot="dropdown">
           <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
           <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
       </el-dropdown-menu>
   </el-dropdown>
   
   data(){
       return {
           user : JSON.parse(sessionStorage.getItem('CurUser'))
       }
   }
   ```

2. 退出登录事件

   ```vue
   <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
   ```

3. 退出跳转、清空相关数据以及退出确认

   ```js
   logout(){
       console.log('logout')
   
       this.$confirm('您确定要退出登录吗？', '提示', {
           confirmButtonText: '确定',  //确认按钮的文字显示
           type: 'warning',
           center: true, //文字居中显示
   
       })
           .then(() => {
               this.$message({
                   type:'success',
                   message:'退出登录成功！'
               })
   
               this.$router.push("/")
               sessionStorage.clear()
           })
           .catch(() => {
               this.$message({
                   type:'info',
                   message:'已取消退出登录！'
               })
           })
   
   }
   ```



## 10.3 个人中心

1. 编写页面

2. 路由跳转（Header.vue）

   ```vue
   <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
   methods:{
           toUser(){
               console.log('to_user')
   
               this.$router.push("/Home")
           }
   }
   ```

3. 路由错误解决（router/index.js）

   ```js
   const VueRouterPush = VueRouter.prototype.push
   VueRouter.prototype.push = function push (to) {
       return VueRouterPush.call(this, to).catch(err => err)
   }
   ```



# 11. 菜单展示

## 11.1 菜单跳转

1. 菜单增加router、高亮

   ```vue
   <el-menu
           background-color="#545c64"
           text-color="#fff"
           active-text-color="#ffd04b"
           style="height: 100%;"
           default-active="/Home"
           :collapse="isCollapse"
           :collapse-transition="false"
           router
   >
   ```

2. 配置子菜单

   ```js
   data(){
           return {
               menu:[
                   {
                       menuClick:'Admin',
                       menuName:'管路员管理',
                       menuIcon:'el-icon-s-custom'
                   },{
                       menuClick:'User',
                       menuName:'用户管理',
                       menuIcon:'el-icon-user-solid'
                   }
               ]
           }
       }
   }
   ```

3. 模拟动态menu

   ```vue
   <el-menu-item :index="'/'+item.menuClick" v-for="(item,i) in menu" :key="i">
       <i :class="item.menuIcon"></i>
       <span slot="title">{{item.menuName}}</span>
   </el-menu-item>
   ```



## 11.2 动态路由

> **vuex状态管理：**
>
> vuex是专为vue.js应用程序开发的状态管理模式。它采用集中存储管理应用的所有组件的状态，并以相应的规则保证状态以一种可预测的方式发生变化。
>
> Vuex 的状态存储是响应式的。当 Vue 组件从 store 中读取状态的时候，若 store 中的状态发生变化，那么相应的组件也会相应地得到高效更新。
>
> 状态管理有5个核心，分别是state、getter、mutation、action以及module。

> **动态路由的实现：**

1. 设计menu表和数据

   ```mysql
   CREATE TABLE `menu` (
     `id` int NOT NULL,
     `menuCode` varchar(8) DEFAULT NULL COMMENT '菜单编码',
     `menuName` varchar(16) DEFAULT NULL COMMENT '菜单名字',
     `menuLevel` varchar(2) DEFAULT NULL COMMENT '菜单级别',
     `menuParentCode` varchar(8) DEFAULT NULL COMMENT '菜单的父code',
     `menuClick` varchar(16) DEFAULT NULL COMMENT '点击触发的函数',
     `menuRight` varchar(8) DEFAULT NULL COMMENT '权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用',
     `menuComponent` varchar(200) DEFAULT NULL,
     `menuIcon` varchar(100) DEFAULT NULL,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
   ```

   ```mysql
   INSERT INTO `menu` VALUES (1, '001', '管理员管理', '1', NULL, 'Admin', '0', 'admin/AdminManage.vue', 'el-icon-s-custom');
   INSERT INTO `menu` VALUES (2, '002', '用户管理', '1', NULL, 'User', '0,1', 'user/UserManage.vue', 'el-icon-user-solid');
   INSERT INTO `menu` VALUES (3, '003', '仓库管理', '1', NULL, 'Storage', '0,1', 'storage/StorageManage', 'el-icon-office-building');
   INSERT INTO `menu` VALUES (4, '004', '物品分类管理', '1', NULL, 'Goodstype', '0,1', 'goodstype/GoodstypeManage', 'el-icon-menu');
   INSERT INTO `menu` VALUES (5, '005', '物品管理 ', '1', NULL, 'Goods', '0,1,2', 'goods/GoodsManage', 'el-icon-s-management');
   INSERT INTO `menu` VALUES (6, '006', '记录管理', '1', NULL, 'Record', '0,1,2', 'record/RecordManage', 'el-icon-s-order');
   ```

2. 生成menu对应的后端代码

   ```java
   /*
    * 根据用户身份获取菜单列表
    * @author linsuwen
    * @date 2023/1/3 20:48
    */
   @GetMapping("/list")
   public Result list(@RequestParam String roleId){
       List list = menuService.lambdaQuery()
               .like(Menu::getMenuright,roleId)
               .list();
       return Result.success(list);
   }
   ```

3. 返回前端数据

   思路：登录的时候一并查询 menu，这样只需要异步查询一次即可将菜单页面展示出来

   修改后端UserController中的登录代码，将用户角色数据和用户角色对应的菜单数据返回给前端

   ```java
   /*
    * 用户登录
    * @author linsuwen
    * @date 2023/1/3 14:08
    */
   @PostMapping("/login")
   public Result login(@RequestBody User user){
       //匹配账号和密码
       List<User> list = userService.lambdaQuery()
               .eq(User::getNo,user.getNo())
               .eq(User::getPassword,user.getPassword())
               .list();
   
       if(list.size()>0){
           User user1 = list.get(0);
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
   ```

4. vuex状态管理

   安装vuex状态管理

   ```shell
   npm i vuex@3.0.0
   ```

   编写store

   ```js
   import vue from 'vue'
   import Vuex from 'vuex'
   vue.use(Vuex)
   //...
   ```

   编写vue状态管理（store/index.js）

   ```js
   export default new Vuex.Store({
       state: {
           menu: []
       },
       mutations: {
           setMenu(state,menuList) {
               state.menu = menuList
   
               addNewRoute(menuList)
           }
       },
       getters: {
           getMenu(state) {
               return state.menu
           }
       }
   })
   ```

   在main.js中注册

   ```js
   import store from "./store"
   ```

5. 存储数据（Login.vue）

   将登录时从后台查询到的菜单数据存储到vuex状态管理中

   ```js
   //存储
   sessionStorage.setItem("CurUser",JSON.stringify(res.data.user))
   console.log(res.data.menu)
   this.$store.commit("setMenu",res.data.menu)
   ```

6. 生成menu数据

   Aside.vue

   ```vue
   <!--动态获取菜单-->
   <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
       <i :class="item.menuicon"></i>
       <span slot="title">{{item.menuname}}</span>
   </el-menu-item>
   ```

7. 生成路由数据

   获取路由列表（store/index.js）

   ```js
   let routes = router.options.routes
   ```

   组装路由

   ```js
   routes.forEach(routeItem=>{
       if(routeItem.path=="/Index"){
           menuList.forEach(menu=>{
               let childRoute =  {
                   path:'/'+menu.menuclick,
                   name:menu.menuname,
                   meta:{
                       title:menu.menuname
                   },
                   component:()=>import('../components/'+menu.menucomponent)
               }
   
               routeItem.children.push(childRoute)
           })
       }
   })
   ```

   合并路由

   ```js
   router.addRoutes(routes)
   ```

   错误处理

   ```js
   export function resetRouter() {
       router.matcher = new VueRouter({
           mode:'history',
           routes: []
       }).matcher
   }
   ```



## 11.3 菜单展示

![QQ截图20230104224949](https://img.yiqiangshiyia.cn/blog/QQ%E6%88%AA%E5%9B%BE20230104224949.png)



# 12. 管理员管理

## 12.1 列表展示

1. 列表数据

   后端给前端返回列表数据

2. ⽤tag转换列

   数据库字段sex（0,1）=> 前端性别显示（男，女）

   ```vue
   <template slot-scope="scope">
       <el-tag
               :type="scope.row.sex === 1 ? 'primary' : 'success'"
               disable-transitions>{{scope.row.sex === 1 ? '男' : '女'}}</el-tag>
   </template>
   ```

3. header-cell-style设置表头样式

   ```vue
   <el-table :data="tableData"
            :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
       >
   ```

4. 加上边框

   ```vue
   <el-table :data="tableData"
            :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
            border
       >
   ```

5. 按钮（编辑、删除）

   ```vue
   <el-table-column prop="operate" label="操作">
       <template slot-scope="scope">
           <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
           <el-popconfirm
                   title="确定删除吗？"
                   @confirm="del(scope.row.id)"
                   style="margin-left: 5px;"
           >
               <el-button slot="reference" size="small" type="danger" >删除</el-button>
           </el-popconfirm>
       </template>
   </el-table-column>
   ```

6. 后端返回结果封装（Result）

   ```java
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
   ```



## 12.2 分页处理

1. 页面加上分页代码

   ```vue
   <el-pagination
           @size-change="handleSizeChange"
           @current-change="handleCurrentChange"
           :current-page="pageNum"
           :page-sizes="[5, 10, 20,30]"
           :page-size="pageSize"
           layout="total, sizes, prev, pager, next, jumper"
           :total="total">
   </el-pagination>
   ```

2. 修改查询方法和参数

   ```js
   loadPost(){
       this.$axios.post(this.$httpUrl+'/user/listPageC1',{
           pageSize:this.pageSize,
           pageNum:this.pageNum,
           param:{
               name:this.name,
               sex:this.sex
           }
       }).then(res=>res.data).then(res=>{
           console.log(res)
           if(res.code==200){
               this.tableData=res.data
               this.total=res.total
           }else{
               alert('获取数据失败')
           }
   
       })
   }
   ```

3. 处理翻页、设置条数逻辑

   ```js
   handleSizeChange(val) {
       console.log(`每页 ${val} 条`);
       this.pageNum=1
       this.pageSize=val
       this.loadPost()
   },
   handleCurrentChange(val) {
       console.log(`当前页: ${val}`);
       this.pageNum=val
       this.loadPost()
   },
   ```

4. 后端逻辑处理

   ```java
   @PostMapping("/listPageC")
   public List<User> listPageC(@RequestBody QueryPageParam query){
       HashMap param = query.getParam();
       String name = (String)param.get("name");
       System.out.println("name=>"+(String)param.get("name"));
   
       Page<User> page = new Page();
       page.setCurrent(query.getPageNum());
       page.setSize(query.getPageSize());
   
       LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
       lambdaQueryWrapper.like(User::getName,name);
   
       IPage result = userService.pageCC(page,lambdaQueryWrapper);
   
       System.out.println("total=>"+result.getTotal());
   
       return result.getRecords();
   }
   ```



## 12.3 查询功能

1. 查询的布局（包含查询、重置按钮）

   ```vue
   <div style="margin-bottom: 5px;">
       <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
       <el-button type="success" @click="resetParam">重置</el-button>
   
       <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>
   </div>
   ```

2. 输入框

   ```vue
   <el-input v-model="name" placeholder="请输入名字" suffix-icon="el-icon-search" style="width: 200px;"
                 @keyup.enter.native="loadPost"></el-input>
   ```

3. 下拉框

   ```vue
   <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 5px;">
       <el-option
               v-for="item in sexs"
               :key="item.value"
               :label="item.label"
               :value="item.value">
       </el-option>
   </el-select>
   ```

4. 回车事件（查询）

   ```js
   @keyup.enter.native="loadPost"
   ```

5. 重置处理

   ```vue
   <el-button type="success" @click="resetParam">重置</el-button>
   ```

6. 后端逻辑处理

   ```java
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
   
       System.out.println("total=="+result.getTotal());
   
       return Result.success(result.getRecords(),result.getTotal());
   }
   ```



## 12.4 新增功能

1. 新增按钮（Main.vue）

   ```vue
   <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>
   ```

2. 弹出窗口

   ```js
   add(){
       this.centerDialogVisible = true
       this.$nextTick(()=>{
           this.resetForm()
       })
   }
   ```

3. 编写表单（Main.vue）

   ```vue
   <el-dialog
           title="提示"
           :visible.sync="centerDialogVisible"
           width="30%"
           center>
   
       <el-form ref="form" :rules="rules" :model="form" label-width="80px">
           <el-form-item label="账号" prop="no">
               <el-col :span="20">
                   <el-input v-model="form.no"></el-input>
               </el-col>
           </el-form-item>
           <el-form-item label="名字" prop="name">
               <el-col :span="20">
                   <el-input v-model="form.name"></el-input>
               </el-col>
           </el-form-item>
           <el-form-item label="密码" prop="password">
               <el-col :span="20">
                   <el-input v-model="form.password"></el-input>
               </el-col>
           </el-form-item>
           <el-form-item label="年龄" prop="age">
               <el-col :span="20">
                   <el-input v-model="form.age"></el-input>
               </el-col>
           </el-form-item>
           <el-form-item label="性别">
               <el-radio-group v-model="form.sex">
                   <el-radio label="1">男</el-radio>
                   <el-radio label="0">女</el-radio>
               </el-radio-group>
           </el-form-item>
           <el-form-item label="电话" prop="phone">
               <el-col :span="20">
                   <el-input v-model="form.phone"></el-input>
               </el-col>
           </el-form-item>
       </el-form>
       <span slot="footer" class="dialog-footer">
   <el-button @click="centerDialogVisible = false">取 消</el-button>
   <el-button type="primary" @click="save">确 定</el-button>
   </span>
   </el-dialog>
   ```

4. 提交数据（提示信息、列表刷新）

   前端提交数据（Main.vue）

   ```js
   doSave({
   this.$axios.post(this.$httpUrl+'/user/save',this.form).then(res=>res.data).then(res=>{
           console.log(res)
           if(res.code==200){
   
               this.$message({
                   message: '操作成功！',
                   type: 'success'
               });
               this.centerDialogVisible = false
               this.loadPost()  //成功后刷新加载数据
               this. resetForm()
           }else{
               this.$message({
                   message: '操作失败！',
                   type: 'error'
               });
           }
   
       })
   }
   ```

   后端接收前端提交的数据并将数据存入数据库中

   ```java
   /*
    * 新增用户
    * @author linsuwen
    * @date 2023/1/2 19:11
    */
   @PostMapping("/save")
   public Result save(@RequestBody User user){
       return userService.save(user)?Result.success():Result.fail();
   }
   ```

5. 数据的检查

   检查所输入数据（Main.vue）

   ```js
   rules: {
       no: [
           {required: true, message: '请输入账号', trigger: 'blur'},
           {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
           {validator:checkDuplicate,trigger: 'blur'}
       ],
       name: [
           {required: true, message: '请输入名字', trigger: 'blur'}
       ],
       password: [
           {required: true, message: '请输入密码', trigger: 'blur'},
           {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
       ],
       age: [
           {required: true, message: '请输入年龄', trigger: 'blur'},
           {min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur'},
           {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数字',trigger: "blur"},
           {validator:checkAge,trigger: 'blur'}
       ],
       phone: [
           {required: true,message: "手机号不能为空",trigger: "blur"},
           {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
       ]
   }
   ```

   ```js
   let checkAge = (rule, value, callback) => {
       if(value>150){
           callback(new Error('年龄输入过大'));
       }else{
           callback();
       }
   };
   ```

6. 账号的唯一验证

   检查账号是否已经存在（Main.vue）

   ```js
   let checkDuplicate =(rule,value,callback)=>{
       if(this.form.id){
           return callback();
       }
       this.$axios.get(this.$httpUrl+"/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
           if(res.code!=200){
               callback()
           }else{
               callback(new Error('账号已经存在'));
           }
       })
   };
   ```

   后端查询用户

   ```java
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
   ```

7. 表单重置



## 12.5 编辑功能

1. 传递数据到表单（Main.vue）

   ```vue
   <el-button slot="reference" size="small" type="danger" >删除</el-button>
   ```

   ```js
   mod(row){
       console.log(row)
   
       this.centerDialogVisible = true
       this.$nextTick(()=>{
           //赋值到表单
           this.form.id = row.id
           this.form.no = row.no
           this.form.name = row.name
           this.form.password = ''
           this.form.age = row.age +''
           this.form.sex = row.sex +''
           this.form.phone = row.phone
           this.form.roleId = row.roleId
       })
   },
   ```

2. 提交数据到后台

   ```js
   doMod(){
   this.$axios.post(this.$httpUrl+'/user/update',this.form).then(res=>res.data).then(res=>{
           console.log(res)
           if(res.code==200){
   
               this.$message({
                   message: '操作成功！',
                   type: 'success'
               });
               this.centerDialogVisible = false
               this.loadPost()
               this. resetForm()
           }else{
               this.$message({
                   message: '操作失败！',
                   type: 'error'
               });
           }
   
       })
   }
   ```

3. 后端逻辑处理

   ```java
   /*
    * 更新用户
    * @author linsuwen
    * @date 2023/1/2 19:11
    */
   @PostMapping("/update")
   public Result update(@RequestBody User user){
       return userService.updateById(user)?Result.success():Result.fail();
   }
   ```

4. 表单重置（异步）

   ```js
   mod(row){
       console.log(row)
   
       this.centerDialogVisible = true
       this.$nextTick(()=>{
           //赋值到表单
       })
   },
   ```



## 12.6 删除功能

1. 获取数据（id）

   ```js
   scope.row.id
   ```

2. 删除确认（Main.vue）

   ```vue
   <el-popconfirm
           title="确定删除吗？"
           @confirm="del(scope.row.id)"
           style="margin-left: 5px;"
   >
       <el-button slot="reference" size="small" type="danger" >删除</el-button>
   </el-popconfirm>
   ```

3. 提交到后台

   ```js
   del(id){
       console.log(id)
   
       this.$axios.get(this.$httpUrl+'/user/del?id='+id).then(res=>res.data).then(res=>{
           console.log(res)
           if(res.code==200){
   
               this.$message({
                   message: '操作成功！',
                   type: 'success'
               });
               this.loadPost()
           }else{
               this.$message({
                   message: '操作失败！',
                   type: 'error'
               });
           }
   
       })
   },
   ```

4. 后端处理

   ```java
   /*
    * 删除用户
    * @author linsuwen
    * @date 2023/1/2 19:15
    */
   @GetMapping("/del")
   public Result delete(Integer id){
       return userService.removeById(id)?Result.success():Result.fail();
   }
   ```



# 13. 用户管理

用户管理和管理员管理的区别在于 roleId 不同，所以代码可以复用



# 14. 仓库管理

1. 仓库表设计

   ```mysql
   CREATE TABLE `storage` (
     `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
     `name` varchar(100) NOT NULL COMMENT '仓库名',
     `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
   ```

2. 根据表生成后端代码

   使用代码生成器自动生成代码

3. 编写后端增删改查代码

   ```java
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
   ```

4. postman测试查询代码

5. 编写前端相关代码（storage/StorageManage.vue）

   复用 user/UserManage.vue 部分代码，稍加修改



# 15. 物品分类管理

1. 物品分类表设计

   ```mysql
   CREATE TABLE `goodstype` (
     `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
     `name` varchar(100) NOT NULL COMMENT '分类名',
     `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
   ```

2. 根据表生成后端代码

   使用代码生成器自动生成代码

3. 编写后端增删改查代码

   ```java
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
   ```

4. postman测试查询代码

5. 编写前端相关代码（goodstype/GoodstypeManage.vue）

   复用 StorageManage.vue 部分代码，稍加修改



# 16. 物品管理

1. 物品表设计

   ```mysql
   CREATE TABLE `goods` (
     `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
     `name` varchar(100) NOT NULL COMMENT '货名',
     `storage` int NOT NULL COMMENT '仓库',
     `goodsType` int NOT NULL COMMENT '分类',
     `count` int DEFAULT NULL COMMENT '数量',
     `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
   ```

2. 根据表生成后端代码

3. 编写后端增删改查代码

   ```java
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
   ```

4. postman测试查询代码

5. 编写前端相关代码（goods/GoodsManage.vue）

   ```js
   count: [
       {required: true, message: '请输⼊数量', trigger: 'blur'},
       {pattern: /^([1-9][0-9]*){1,4}$/,message: '数量必须为正整数字',trigger: "blur"},			{validator:checkCount,trigger: 'blur'}
   ],
   ```

   ```js
   let checkCount = (rule, value, callback) => {
       if(value>9999){
           callback(new Error('数量输入过大'));
       }else{
           callback();
       }
   };
   ```

6. 仓库和分类列表展示

   ```vue
   <el-table-column prop="storage" label="仓库" width="160" :formatter="formatStorage">
   </el-table-column>
   <el-table-column prop="goodstype" label="分类" width="160" :formatter="formatGoodstype">
   </el-table-column>
   ```

   ```js
   formatStorage(row){
       let temp =  this.storageData.find(item=>{
           return item.id == row.storage
       })
   
       return temp && temp.name
   },
   formatGoodstype(row){
       let temp =  this.goodstypeData.find(item=>{
           return item.id == row.goodstype
       })
   
       return temp && temp.name
   },
   ```

7. 查询条件中增加仓库和分类的条件

8. 表单中仓库和分类下拉实现

   ```vue
   <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 5px;">
       <el-option
               v-for="item in storageData"
               :key="item.id"
               :label="item.name"
               :value="item.id">
       </el-option>
   </el-select>
   <el-select v-model="goodstype" placeholder="请选择分类" style="margin-left: 5px;">
       <el-option
               v-for="item in goodstypeData"
               :key="item.id"
               :label="item.name"
               :value="item.id">
       </el-option>
   </el-select>
   ```



# 17. 记录管理

1. 记录表设计

   ```mysql
   CREATE TABLE `record` (
     `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
     `goods` int NOT NULL COMMENT '货品id',
     `userId` int DEFAULT NULL COMMENT '取货人/补货人',
     `admin_id` int DEFAULT NULL COMMENT '操作人id',
     `count` int DEFAULT NULL COMMENT '数量',
     `createtime` datetime DEFAULT NULL COMMENT '操作时间',
     `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
   ```

2. 根据表生成后端代码

3. 编写后端查询代码

4. 编写前端相关代码

5. 优化

6. 列表展示商品名、仓库、分类名

7. 按物品名查询、仓库、分类查询



# 18. 出入库管理

1. 表单编写
2. 入库操作（记录、更新物品数量、自动填充时间）
3. 用户选择



# 19. 权限控制优化

1. 出入库权限控制
2. 记录查询权限控制



# 20. 项目部署

SpringBoot + Vue项目部署到服务器上



# 21. vuex持久化后刷新数据丢失

> 系统Bug：刷新系统后会导致数据丢失

Bug分析：vuex持久化后，每当浏览器刷新就会丢失state中的数据

解决方法：保存这个state的数据

具体解决方法：

1. 解决菜单丢失问题

   安装插件vuex-persistedstate：

   ```shell
   npm i vuex-persistedstate
   ```

   引入（store/index.js）：

   ```js
   import createPersistedState from 'vuex-persistedstate'
   ```

   使用（store/index.js）：

   ```js
   plugins:[createPersistedState()]
   ```

2. 解决路由丢失问题（App.vue）

   ```js
   //解决前端刷新页面路由丢失问题
   data(){
     return{
       user : JSON.parse(sessionStorage.getItem('CurUser'))
     }
   },
   watch:{
     '$store.state.menu':{
       handler(val,old){
         console.log(val)
         if(!old && this.user && this.user.no){
           this.$store.commit('setMenu',val)
         }
       },
       immediate: true
     }
   }
   ```



