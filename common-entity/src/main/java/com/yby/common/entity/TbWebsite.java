package com.yby.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TbWebsite对象", description="")
public class TbWebsite implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "网站id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "网站名称")
    private String name;

    @ApiModelProperty(value = "网站地址")
    private String url;

    @ApiModelProperty(value = "网站logo")
    private String logo;

    @ApiModelProperty(value = "分类表ID")
    private String idCategory;

    @ApiModelProperty(value = "关键字表ID")
    private String keywords;

    @ApiModelProperty(value = "管理员表ID")
    private String idAdmin;

    @ApiModelProperty(value = "统计表ID")
    private String idStatistics;

    @ApiModelProperty(value = "竞价表ID")
    private String idBiding;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
