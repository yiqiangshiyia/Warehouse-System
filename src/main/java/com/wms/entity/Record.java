package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@ApiModel(value="Record对象", description="")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "货品id")
    private Integer goods;

    @ApiModelProperty(value = "取货人/补货人")
    @TableField("userId")
    private Integer userid;

    @ApiModelProperty(value = "操作人id")
    private Integer adminId;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(exist = false)
    private String action;
}
