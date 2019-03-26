package com.bfxy.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfxy.springboot.entity.MstDict;
import com.bfxy.springboot.mapper.MstDictMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
public class MstDictService {

	@Autowired
	private MstDictMapper dictMapper;
	
	public List<MstDict> findByStatus(String status, int page, int limit) throws Exception {
		
		PageBounds pageBounds = new PageBounds(page, limit);
		return this.dictMapper.findByStatus(status, pageBounds);
	}
}
