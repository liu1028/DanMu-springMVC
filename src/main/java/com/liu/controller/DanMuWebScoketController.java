package com.liu.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.tomcat.util.buf.ByteChunk.ByteOutputChannel;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.entity.DanMu;
import com.liu.listener.SpringCtx;
import com.liu.services.DanMuService;
import com.liu.services.impl.DanMuServiceImpl;

@WebServlet(urlPatterns="/ws/danmu")
@SuppressWarnings("deprecation")
public class DanMuWebScoketController extends WebSocketServlet {
	
	private DanMuService danMuService=null;


	public DanMuWebScoketController() {
		danMuService=(DanMuService)SpringCtx.getWebAppContext().getBean("danMuService");
	}

    private final AtomicInteger connectionIds = new AtomicInteger(0);  

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest request) {
		
		return new HelloMessageInbound(connectionIds.getAndIncrement(), request.getSession());
	}
	
	 class HelloMessageInbound extends StreamInbound {  
		  
	    private String WS_NAME;  
	    private final String FORMAT = "%s : %s";  
	    private final String PREFIX = "ws_";  
	    private String sessionId = "";  
	    private HttpSession session;
	    
	    public HelloMessageInbound(int id, HttpSession session) {  
	        this.WS_NAME = PREFIX + id;  
	        this.sessionId = session.getId();
	        this.session=session;
	        this.objmapper=new ObjectMapper();
	        this.boutput=new ByteArrayOutputStream();
	        
	        try {
				this.generator=objmapper.getFactory().createGenerator(boutput);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }  
	  
	    //客户端发送数据过来
	    @Override  
	    protected void onTextData(Reader reader) throws IOException {  
	        char[] chArr = new char[1024];  
	        int len = reader.read(chArr);  
	        System.out.println(String.copyValueOf(chArr, 0, len));
	    }  
	  
	    //webSocket关闭的时候
	    @Override  
	    protected void onClose(int status) {  
	        System.out.println(String.format(FORMAT, WS_NAME, "closing ......"));  
	        super.onClose(status);  
	        if(boutput!=null)
				try {
					System.out.println("close bouput");
					boutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        
	        if(generator!=null)
				try {
					System.out.println("close generator");
					generator.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        
	        
	    }  
	  
	    //WebSocket打开的时候，写数据到客户端
	    @Override  
	    protected void onOpen(WsOutbound outbound) {  
	        super.onOpen(outbound);  
	        
	        System.out.println("OPEN:the client is "+WS_NAME);
	                
	        try {
				SendDanMu();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	    }  
	    
	    private ObjectMapper objmapper=null;
	    private JsonGenerator generator=null;
	    private ByteArrayOutputStream boutput=null;
	    private void SendDanMu() throws InterruptedException, IOException{
			
			if(session.getAttribute("stime")==null){
				Calendar c=Calendar.getInstance();
				c.add(Calendar.MINUTE, -20);
				session.setAttribute("stime",c.getTime());
			}

			while(true){
				Thread.sleep(3000);
				
				Calendar c=Calendar.getInstance();
				Date end=c.getTime();
				c.add(Calendar.SECOND, -5);
				Date start=c.getTime();
				//Date start=(Date)session.getAttribute("stime");
				//Date end=new Date();
				//session.setAttribute("stime", end);
				
				/**Debug**/
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				System.out.println("start:"+format.format(start)+";end:"+format.format(end));
					
				
				List<DanMu> list=danMuService.findDanMus(start, end);
								
				generator.writeStartArray();
				for(DanMu d:list){
					generator.writeString(d.getContent());
				}
				generator.writeEndArray();
				
				generator.flush();
				
				String s=new String(boutput.toByteArray(), "UTF-8");
				boutput.reset();
				System.out.println(s);
				getWsOutbound().writeTextMessage(CharBuffer.wrap(s));
			
				//getWsOutbound().writeTextMessage(CharBuffer.wrap("[\"lalallala\",\"啦啦啦啦啦啦啦\",\"就是这样\"]"));
			}
	    }
	  
	    private void send(String message) throws IOException {  
	        message = String.format(FORMAT, WS_NAME, message);  
	        System.out.println(message);  
	        getWsOutbound().writeTextMessage(CharBuffer.wrap(message));  
	    }  
	  
	    @Override  
	    protected void onBinaryData(InputStream arg0) throws IOException {  
	    }

	}  

}
