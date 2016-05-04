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
		<a href="javascript:void(0)" id="openDm" style="margin:5px;">打开弹幕</a>
		<div class="dm">
			<div class="dm_screen">
				<div class="s_mask"></div>
				<a href="javascript:void(0)" id="dm_del">X</a>
				<div class="dm_con">
					<div class="s">范围分为高4 34否</div>
					<div>f绯闻绯闻认为个人更</div>
					<div>天苍苍野茫茫</div>
					<div>噶杨个文件可归结为可据了解个人交过来可热就挂科了个人各位哥</div>
					<div>fefwerjoijihieijfoiegrjgioth</div>
					<div>233333333333</div>
					<div>mfewjl wfejfjewo wefhiwfojopjljfeffwejfowjefoiewjf</div>
					<div>23333333333333333333333333333</div>
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
						intervalId=setInterval(getDanMu,5000);
					}else{
						IsOpen=0;
						clearInterval(intervalId);
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
				
				//getDanMu();
				
				function getDanMu(){
					$.ajax({
						type:'get',
						url:'<%=path%>/spring/getdanmu',
						success:function(r){
							var j=$.parseJSON(r);
							$.each(j,function(i,o){
								//alert(o);
								addDm(o);
							});
						}
					});
				}
			})
			
			
		</script>	
	</body>
</html>