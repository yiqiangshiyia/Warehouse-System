package com.wms.common;

import lombok.Data;

import java.util.HashMap;

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
