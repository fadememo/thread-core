package com.bfxy.springboot;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bfxy.springboot.entity.MstDict;
import com.bfxy.springboot.mapper.MstDictMapper;
import com.bfxy.springboot.service.MstDictService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	
	@Autowired
	private MstDictMapper mstDictMapper;
	
	
	@Test
	public void testInsert() throws Exception {
		
		MstDict md1 = new MstDict();
		md1.setCode("001");
		md1.setName("苹果");
		md1.setStatus("1");
		
		MstDict md2 = new MstDict();
		md2.setCode("002");
		md2.setName("橘子");
		md2.setStatus("1");
		
		MstDict md3 = new MstDict();
		md3.setCode("003");
		md3.setName("草莓");
		md3.setStatus("1");
		
		MstDict md4 = new MstDict();
		md4.setCode("004");
		md4.setName("香蕉");
		md4.setStatus("0");
		
		mstDictMapper.insert(md1);
		mstDictMapper.insert(md2);
		mstDictMapper.insert(md3);
		mstDictMapper.insert(md4);
		
	}
	
	@Autowired
	private MstDictService mstDictService;
	
	@Test
	public void testFind() throws Exception {
		
		List<MstDict> list = mstDictService.findByStatus("1", 1, 2);
		
		for(MstDict md : list) {
			System.err.println(md.getName());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
