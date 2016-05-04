package com.liu.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.entity.DanMu;
import com.liu.services.DanMuService;

public class AsynSendDanMuController extends AbstractController {

	private DanMuService danMuService=null;
	public void setDanMuService(DanMuService danMuService) {
		this.danMuService = danMuService;
	}

	public AsynSendDanMuController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter writer=response.getWriter();
		HttpSession session=request.getSession();
		if(session.getAttribute("stime")==null){
			writer.write("{\"status\":false}");
		}
		
		Date start=(Date)session.getAttribute("stime");
		Date end=new Date();
		session.setAttribute("stime", end);
		
		/**Debug**/
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("start:"+format.format(start)+";end:"+format.format(end));
		
		List<DanMu> list=danMuService.findDanMus(start, end);
		
		ObjectMapper objmapper=new ObjectMapper();
		JsonGenerator generator=	objmapper.getFactory().createGenerator(writer);
		
		generator.writeStartArray();
		for(DanMu d:list){
			generator.writeString(d.getContent());
		}
		generator.writeEndArray();
		
		generator.flush();
	//	System.out.println(Arrays.to);
		
		return null;
	}

}
