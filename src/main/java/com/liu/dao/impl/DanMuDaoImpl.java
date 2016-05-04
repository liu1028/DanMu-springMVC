package com.liu.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.liu.dao.DanMuDao;
import com.liu.entity.DanMu;

public class DanMuDaoImpl extends JdbcDaoSupport implements DanMuDao {

	public DanMuDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public Integer addDanMu(DanMu danmu) {
		JdbcTemplate jtemp=this.getJdbcTemplate();
		
		String content=danmu.getContent();
		String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		if(content.equals(""))
			return 0;
		
		String sql="insert into dm_con(guid,text,stime) values(UUID(),?,?) ";
		
		
		return jtemp.update(sql,content,dateStr);
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

		JdbcTemplate jtemp=this.getJdbcTemplate();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String stime=format.format(start);
		String etime=format.format(end);
		
		String sql="select text from dm_con where stime > ? and stime < ?";
		
		//return jtemp.queryForList(sql, DanMu.class, stime,etime);
		//System.out.println("start:"+stime+";end:"+etime);
		
		final List<DanMu> list=new ArrayList<DanMu>();
		jtemp.query(sql, new Object[]{stime,etime},new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				list.add(new DanMu(rs.getString("text"),null));
				while(rs.next()){
					list.add(new DanMu(rs.getString("text"),null));
				}

			}
		} );
		
		return list;
	}

}
