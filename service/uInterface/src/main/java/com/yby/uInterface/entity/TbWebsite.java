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

    private String idCategory;

    private String keywords;

    private String idAdmin;

    private String idStatisitcs;

    private String idBiding;

    private Date gmtCreate;

    private Date gmtModified;


}
