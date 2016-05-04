package com.liu.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liu.dao.DanMuDao;
import com.liu.entity.DanMu;

import junit.framework.TestCase;

public class DanMuDaoTest extends TestCase {

	public void testFindDanMus() {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		
		DanMuDao dao=(DanMuDao)context.getBean("danMuDao");
		
		Calendar ca=Calendar.getInstance();
		ca.add(Calendar.MINUTE,-30);
		//System.out.println(ca.getTime());
		List<DanMu> list=dao.findDanMus(ca.getTime(),new Date());
		
		for(DanMu d:list){
			System.out.println(d.getContent());
		}
	}

	public void testFindDanMusDateDate() {
		fail("Not yet implemented");
	}

}
