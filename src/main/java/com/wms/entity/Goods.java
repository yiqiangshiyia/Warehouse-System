package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author linsuwen
 * @since 2023-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Goods对象", description="")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "货名")
    private String name;

    @ApiModelProperty(value = "仓库")
    private Integer storage;

    @ApiModelProperty(value = "分类")
    @TableField("goodsType")
    private Integer goodstype;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "备注")
    private String remark;


}
