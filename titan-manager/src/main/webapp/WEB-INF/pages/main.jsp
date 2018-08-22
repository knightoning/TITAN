<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>TITAN</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/layui/css/layui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/titan.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style type="text/css">
.logout>a{
	line-height: 20px;
	font-size: 14px;
	color: #777;
}
.logout>a:hover{
	color: #fff;
}
.sayUser{
	line-height: 30px;
}
.laypage-main a{
	padding: 0 20px;
    line-height: 36px;
    border-right: 1px solid #E2E2E2;
    border-bottom: 1px solid #E2E2E2;
    font-size: 14px;
    display: inline-block;
   	vertical-align: top;
}
.laypage-main a:hover {
    color: #0B6295;
    transition: all .3s;
}
.laypage-main{
    border: 1px solid #E2E2E2;
    border-right: none;
    border-bottom: none;
    font-size: 0;
    display: inline-block;
   	vertical-align: top;
   	margin-top:20px;
   	float:right;
}
</style>
</head>
<body>
	<div class="layui-layout layui-layout-admin" >
		<div class="layui-row titan-index-header">
	 		<div class="layui-col-md2">
	  			<span class="titan-index-header-logo"><img src="../images/logo2.png" style="height: 40px;padding-left: 16px;"></span>
	  		</div>
	  		<div class="layui-col-md10" style="text-align: right;padding-right: 16px;font-size: 14px;">
	  			<span><a id="logoutBtn" style="cursor:pointer;color: #fff;">退出</a></span>
	  		</div>
	    </div> 

		<div class="layui-side layui-bg-black" style="top: 42px;">
			<div class="layui-side-scroll">
				<ul class="layui-nav layui-nav-tree" id="yj_left_sidebar" lay-filter="test">
					<li class="layui-nav-item  layui-this"><a childpage="${pageContext.request.contextPath}/pages/overview" style="cursor:pointer;">概览</a></li>
					<li class="layui-nav-item layui-nav-itemed">
						<a class="" href="javascript:;">压测管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a id="linkPageBtn" childpage="${pageContext.request.contextPath}/pages/pressure/link_list" style="cursor:pointer;padding-left: 3em;">链路管理</a>
							</dd>
							<dd>
								<a id="scenePageBtn" childpage="${pageContext.request.contextPath}/pages/pressure/scene_list" style="cursor:pointer;padding-left: 3em;">场景管理</a>
							</dd>
							<dd>
								<a id="pressurePageBtn" childpage="${pageContext.request.contextPath}/pages/pressure/do_pressure_list" style="cursor:pointer;padding-left: 3em;">执行压测</a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item"><a id="monitorListPageBtn" childpage="${pageContext.request.contextPath}/pages/monitor/monitor_list" style="cursor:pointer;">监控集管理</a></li>
					<li class="layui-nav-item"><a id="pressureReportPageBtn" childpage="${pageContext.request.contextPath}/pages/report/report_list" style="cursor:pointer;">压测报告</a></li>
				</ul>
			</div>
		</div>

		<div class="layui-body" style="top: 42px;">
			<!-- 内容主体区域 -->
			<div class="layui-tab admin-nav-card layui-tab-brief" lay-filter="admin-tab" id="mainContent"></div>
		</div>
		<div class="layui-footer">
			<ul>
		  		<li>
			  		<span>Power by云集研发中心基础架构组 Copyright &copy; 2017~2018</span>
			  		<span><a href="https://github.com/yunjiweidian/TITAN" target="_black"><img alt="github" src="../images/github.png" style="height: 32px;"></a></span>
			  		<span style="float: right;"><i class="layui-icon" title="当前登录用户">&#xe612;</i>&nbsp;&nbsp;<label id="main_username"></label></span>
		  		</li>
		  	</ul>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/cookie.js"></script>
	<script src="${pageContext.request.contextPath}/js/common.js"></script>
	<script>
		//加载layer
		layui.use([ 'element', 'form','laypage','table','upload','laydate'], function() {
		    element = layui.element;
			form = layui.form;
			laypage = layui.laypage;
			table = layui.table;
			upload = layui.upload; 
			laydate = layui.laydate;
		});
		var $mainContent = $('#mainContent');
		//1、加载默认展示页
		$mainContent.load('${pageContext.request.contextPath}/pages/overview');
		
		//2、菜单项点击
		$('[childpage]').on('click',function(){
			titanInitParam();
			var $this = $(this);
			if($this.hasClass('layui-this')){
				return;
			}
			$('.layui-this').removeClass('layui-this');
			$this.parent().addClass('layui-this');
			var linkUrl = $this.attr('childpage');
			$mainContent.empty().load(linkUrl);
		});
		
		//3、读取当前登录用户名
		$("#main_username").text(getCookie("username"));
		
		//4、新增按钮
		$(document).on('click.link',"#add_link_btn",function(){
			titanInitParam();
			$mainContent.empty().load('${pageContext.request.contextPath}/pages/pressure/link_add');
		}).on('click.scene',"#add-scene-btn",function(){
			titanInitParam();
			$mainContent.empty().load('${pageContext.request.contextPath}/pages/pressure/scene_add');
		});
		
		//5、logo
    	$("span.titan-index-header-logo").bind("contextmenu", function(){
    	    return false;
    	});
         $(".titan-index-header-logo").mousedown(function(e) {
            if(3 == e.which){
            	layer.open({
					  content: '确认重置场景状态吗？',
					  icon: 3,
					  offset: '200px',
					  shadeClose :true,
					  shade:0,
					  time: 2000,
					  offset:  ['42px', '0px'],
					  btn: ['是', '否'],
					  yes: function(index, layero){
						  layer.closeAll('dialog');
						  $.ajax({
							    url:'${pageContext.request.contextPath}/scene/reset',
							    type:'post', 
							    success:function(result,textStatus){
							    	if(result.success){
							    		layer.alert("强制重置成功", {icon: 1});
							    	}else{
							    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
							    	}
							    },
							    error:function(XMLHttpRequest, textStatus, errorThrown){
							    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
							    }
						 });
					  }
				});
            }
        });
         //退出
        $("#logoutBtn").on("click",function(){
        	 $.ajax({
				    url:'${pageContext.request.contextPath}/user/logout',
				    type:'post', 
				    sync:false,
				    success:function(result,textStatus){
				    	if(result.success){
				    		window.location.href = "${pageContext.request.contextPath}/index.html";
				    	}else{
				    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
				    	}
				    },
				    error:function(XMLHttpRequest, textStatus, errorThrown){
				    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
				    }
			 });
         });
	</script>
</body>
</html>