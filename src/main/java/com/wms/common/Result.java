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
