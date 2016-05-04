package com.liu.dao;

import java.util.Date;
import java.util.List;

import com.liu.entity.DanMu;

public interface DanMuDao {

	Integer addDanMu(DanMu danmu);
	
	Integer deleteDanMu(String guid);
	Integer deleteDanMu(DanMu deletModel);
	
	Integer updateDanMu(DanMu updModel);
	
	DanMu findSingleDanMu(DanMu searchModel);
	List<DanMu> findDanMus(DanMu searchModel);
	List<DanMu> findDanMus(Date start,Date end);
}
