package com.bfxy.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bfxy.springboot.config.database.BaseMapper;
import com.bfxy.springboot.entity.MstDict;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface MstDictMapper extends BaseMapper<MstDict> {

	List<MstDict> findByStatus(@Param("status")String status, PageBounds pageBounds);
	
}