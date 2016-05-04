<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path=request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发射弹幕</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="viewport" id="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	
	<link rel="stylesheet" href="<%=path %>/css/bootstrap.css" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/css/bootstrap-theme.css" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/css/sweetalert.css" type="text/css" />
		
  </head>

<body>
	<div class="container">
		<form class="form-signin">
			<h2 class="form-signin-heading">弹幕系统1.0</h2>
			<div class="form-group">
				<label for="dan_con">请输入弹幕：</label>
				 <input type="text" class="form-control" id="dan_con"
					placeholder="森神最帅！">
			</div>
			<button type="button" class="btn btn-primary" id="btn_shoot">发射</button>
		</form>

	</div>
	<!-- /container -->

	<div id="footer" class="container">
		<nav class="navbar navbar-default navbar-fixed-bottom">
		    <div class="navbar-inner navbar-content-center">
		        <p class="text-muted credit" style="padding: 10px;text-align:center;">
		           森哥&copy; 2016 copyright
		        </p>
		    </div>
		</nav>
	</div>
	
	
	<script src="<%=path %>/js/jquery2.js"></script>
	<script src="<%=path %>/js/bootstrap.js"></script>
	<script src="<%=path %>/js/sweetalert.min.js"></script>
	
	<script type="text/javascript">
    	$(function(){
    		
    		$('#btn_shoot').click(function(){
    			var con=$('#dan_con').val();
    			if($.trim(con)==''){
    				swal("这是一个警告！", "请输入内容在发送!");
    				return;
    			}
    			$.ajax({
    				type:'post',
    				url:'<%=path%>/spring/sendDanmu',
    				data:{content:con},
    				success:function(r){
    					var json=$.parseJSON(r);
    					if(json.status){
    						swal("干得漂亮！", "发送成功哦！", "success");
    						$('#dan_con').val('');
    					}else
    						swal("提示！", "系统忙，请稍候再试吧!")
    				}
    			});
    			
    		});
    		
    	})
    </script>
</body>
</html>
