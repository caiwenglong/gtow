package com.yby.uInterface.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yby.common.entity.TbWebsite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-03
 */
@Mapper
public interface TbWebsiteMapper extends BaseMapper<TbWebsite> {

}
