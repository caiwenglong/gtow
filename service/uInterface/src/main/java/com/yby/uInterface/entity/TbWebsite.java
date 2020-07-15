package com.yby.uInterface.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;

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
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
