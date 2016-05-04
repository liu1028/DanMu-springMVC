package com.liu.entity;

import java.io.Serializable;
import java.util.Date;

public class DanMu implements Serializable {
	
	private String text;
	private Date  stime;

	public DanMu() {
	}

	public DanMu(String content, Date stime) {
		super();
		this.text = content;
		this.stime = stime;
	}


	public String getContent() {
		return text;
	}
	public void setContent(String content) {
		this.text = content;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	
	

}
