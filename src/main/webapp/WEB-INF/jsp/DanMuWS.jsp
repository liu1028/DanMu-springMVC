<%@page language="java"  import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path=request.getContextPath();
	request.getSession().setAttribute("stime", new Date());
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf8" />
		<meta name="viewport" id="viewport" content="width=device-width, initial-scale=1">
		<title>弹幕练习 </title>
		
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/DanMu.css" />
	</head>
	<body>
		<a href="javascript:void(0)" id="openDm" style="margin:5px;display:inline-block;float:left;font:normal bold 18px/20px '宋体'">打开弹幕</a>
		<div style="float:right;margin-top:20px;margin-right:60px;text-align:center;">
			<img src="<%=path %>/img/liantu.png">
			<div style="font:normal bold 15px/20px '微软雅黑'">扫二维码 发弹幕</div>
		</div>
		
		<div class="dm">
			<div class="dm_screen">
				<div class="s_mask"></div>
				<a href="javascript:void(0)" id="dm_del">X</a>
				<div class="dm_con">
					<div class="s">你好，这是弹幕系统1.0-ShotSnap</div>
					<div>哈哈，你可以扫描右上角的二维码来手机发送弹幕</div>
					<div>233333333333333333</div>
					<div>我赵日天上日天下日地</div>
					<div>我本不想引起一番江湖的腥风血雨，是你逼我的</div>
					<div>啦啦啦啦啦啦啦，大家好</div>
					<div>这弹幕没有毒</div>
					<div>来来来，哥分分钟教你做女人！！！</div>
					<div>所有为攻击我而造的武器都必将被摧毁，所有在审判中诋毁我的言论都必将被定罪！</div>
				</div>
			</div>
			<div class="dm_bottom">
				<div class="bottom_mask"></div>
				<input type="text" name="dm_content" id="dm_content" /><input type="button" value="发射" id="btn_shoot" />
			</div>
		</div>
		
		<script src="<%=path%>/js/jquery2.js"></script>
		<script>
			
			$(function(){
				var IsOpen=0;
				var intervalId=0;
				var colors=["red","blue","pink","lightblue","green","orange","purple"];
				
				$('#openDm,#dm_del').click(function(){
					$('.dm').toggle(800);
					if(IsOpen==0){
						init_screen();
						IsOpen=1;
						initWebSocketTask();
					}else{
						IsOpen=0;
						websocket.close();
					}
				});
				
				function init_screen(){
					var ytop=10;
					$('.dm_con > div').each(function(){
						var xleft=$(window).width();
						var winHeight=$(window).height();
						ytop+=50;
						
						if(ytop>winHeight-$('.dm_bottom').height())
							ytop=10;
						
						$(this).css({left:xleft+"px",top:ytop+"px",color:colors[Math.round(Math.random()*6)]});
						
						var time=30000;
						if($(this).index()%2==0){
							time=20000;
							if($(this).index()%3==0)
								time=25000;
						}
						$(this).animate({left:"-"+$(this).width()+"px"},time,'linear',function(){
							$(this).remove();
						});
					});
				}
				
				$('#btn_shoot').click(function(){
					var con=$('#dm_content').val();
					if(con=="")
						return;
													
	    			$.ajax({
	    				type:'post',
	    				url:'<%=path%>/spring/sendDanmu',
	    				data:{content:con},
	    				success:function(r){
	    					var json=$.parseJSON(r);
	    					if(json.status){
	    						$('#dm_content').val('');
	    					}
	    				}
	    			});
	    			

				});
				
				function addDm(content){				
					var left=$(window).width();
					var top=(Math.random()*8)*50+10;
					
					var el=$('<div></div>');
					el.text(content);
					el.css({left:left,top:top,color:colors[Math.round(Math.random()*6)]});
					
					$('.dm_con').append(el);
					
					var time=20000;
					if($(this).index()%2==0){
						time=10000;
						if($(this).index()%3==0)
							time=15000;
					}
					
					el.animate({left:"-"+$(this).width()+"px"},time,'linear',function(){
							$(this).remove();
					});
					
				}
				
				
	
				/***WebSocket start****/
				  var wsUri = "ws://localhost:8080/DanMu/ws/danmu";
				  var websocket;
				  
				  function initWebSocketTask()
				  {
				    websocket = new WebSocket(wsUri);
				    websocket.onopen = function(evt) { onOpen(evt) };
				    websocket.onclose = function(evt) { onClose(evt) };
				    websocket.onmessage = function(evt) { onMessage(evt) };
				    websocket.onerror = function(evt) { onError(evt) };
				  }

				  function onOpen(evt)
				  {
				    //writeToScreen("CONNECTED");
				    //doSend("WebSocket rocks");
				  }

				  function onClose(evt)
				  {
				   // writeToScreen("DISCONNECTED");
				  }

				  function onMessage(evt)
				  {
					    var j=$.parseJSON(evt.data);
						$.each(j,function(i,o){
							//alert(o);
							addDm(o);
						});
				  }

				  function onError(evt)
				  {
				    //writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
				  }

				  function doSend(message)
				  {
				    //writeToScreen("SENT: " + message);
				    //websocket.send(message);
				  }

				  function writeToScreen(message)
				  {
				    var pre = document.createElement("p");
				    pre.style.wordWrap = "break-word";
				    pre.innerHTML = message;
				    output.appendChild(pre);
				  }

				 
			})
			
			
		</script>	
	</body>
</html>