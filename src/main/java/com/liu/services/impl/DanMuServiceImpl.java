package com.liu.services.impl;

import java.util.Date;
import java.util.List;

import com.liu.dao.DanMuDao;
import com.liu.entity.DanMu;
import com.liu.services.DanMuService;

public class DanMuServiceImpl implements DanMuService {

	private DanMuDao danMuDao=null;

	public void setDanMuDao(DanMuDao danMuDao) {
		this.danMuDao = danMuDao;
	}

	public DanMuServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public Integer addDanMu(DanMu danmu) {
		return danMuDao.addDanMu(danmu);
	}

	public Integer deleteDanMu(String guid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer deleteDanMu(DanMu deletModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer updateDanMu(DanMu updModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public DanMu findSingleDanMu(DanMu searchModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DanMu> findDanMus(DanMu searchModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DanMu> findDanMus(Date start, Date end) {
		return danMuDao.findDanMus(start, end);
	}

}
