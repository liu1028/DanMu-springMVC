package com.liu.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.liu.entity.DanMu;
import com.liu.services.DanMuService;

public class ShootDanmuController extends AbstractController {

	private DanMuService danMuService=null;
	public void setDanMuService(DanMuService danMuService) {
		this.danMuService = danMuService;
	}

	public ShootDanmuController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equals("GET"))
			return new ModelAndView("shootDanmu");
		
		String content=request.getParameter("content");
		
		Integer f=danMuService.addDanMu(new DanMu(content, null));
		
		PrintWriter writer=response.getWriter();
		if(f>0)
			writer.write("{\"status\":true}");
		else 
			writer.write("{\"status\":false}");
		
		writer.flush();
		writer.close();
		
		return null;
	}

}
