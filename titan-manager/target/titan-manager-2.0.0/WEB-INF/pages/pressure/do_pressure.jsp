<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="layui-container" style="width: 100%;" id="doPressurePage">
	<div class="layui-row titan-content-div" style="font-size:18px;">
		<div class="layui-elem-quote">
			<label class="titan-head-label2"></label><span class="titan-head-span">场景构建成功，可以启动压测</span>
		</div>
	</div>
	<div class="layui-row">
		<button class="layui-btn" id="startBtn">启动压测</button> 
		<button class="layui-btn layui-btn-disabled" id="stopBtn">停止压测</button>
	</div>
	<div class="layui-row">
		<table class="layui-table titan-data-table-s">
			<thead>
				<tr>
					<th>并发用户数</th>
					<th>总并发请求数</th>
					<th>期待吞吐量</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<div class="layui-row">
		<table class="layui-table titan-data-table">
			<thead>
				<tr>
					<th>链路ID</th>
					<th>链路名称</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</div>
<script>
$(function() { 
	doPressurePage();
});
function doPressurePage(){
	var sceneId = $mainContent.data('pressure');
	getDataDetail(sceneId);
	bindClickEvent();
	function getDataDetail(id){
		$.ajax({
		    url:'${pageContext.request.contextPath}/scene/get',
		    type:'post', 
		    async:false,    
		    dataType:'json',  
		    data:{
		    	sceneId:parseInt(id)
		    },
		    success:function(result,textStatus){
		    	if(result.success){
		    		var params = result.data.scene;
		    		updateBtnOperateStatus(params.sceneStatus); //操作按钮
		    		$("#doPressurePage label.titan-head-label2").text(params.sceneName);
		    		var sceneHtml = '<tr>'
						    		+'<td>' + params.concurrentUser + '</td>'
						    		+'<td>' + params.totalRequest + '</td>'
						    		+'<td>' + params.expectTps + '</td>'
						    	 +'</tr>';
		    		$("#doPressurePage table.titan-data-table-s").find("tbody").html(sceneHtml);
		    		var linkListHtml = "";
		    		for(var i=0;i<result.data.linkList.length;i++){
		    			var item = result.data.linkList[i];
		    			linkListHtml += '<tr>'
										+'<td>' + item.linkId + '</td>'
										+'<td>' + item.linkName + '</td>'
										+'<td>' + titanFormdate(item.createTime) + '</td>'
										+'</tr>';
		    		}
		    		$("#doPressurePage table.titan-data-table").find("tbody").html(linkListHtml);
		    	}else{
		    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
		    	}
		    },
		    error:function(xhr,textStatus){
		        console.log('错误:' + xhr);
		    }
		});
	}
	function bindClickEvent(){
		//启动
		$("#doPressurePage #startBtn").on("click",function(){
			if(!$(this).hasClass("layui-btn-disabled")){
				 layer.open({
					  content: '确认启动压测吗？',
					  icon: 3,
					  offset: '200px',
					  shadeClose :true,
					  btn: ['是', '否'],
					  yes: function(index, layero){
						  layer.closeAll('dialog');
						  $.ajax({
							    url:'${pageContext.request.contextPath}/operate/performanceTest',
							    type:'post', 
							    async:false, 
							    dataType:'json',  
							    data:{
							    	sceneId:parseInt(sceneId),
							    	operateType:1
								},   
							    success:function(result,textStatus){
							    	if(result.success){
							    		updateBtnOperateStatus(1);
							    	}else{
							    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
							    	} 
							    },
							    error:function(xhr,textStatus){
							        console.log('错误：' + xhr);
							    }
							});
					  }
				});
			}
		});
		//停止
		$("#doPressurePage #stopBtn").on("click",function(){
			if(!$(this).hasClass("layui-btn-disabled")){
				layer.open({
					  content: '确认停止压测吗？',
					  icon: 3,
					  offset: '200px',
					  shadeClose :true,
					  btn: ['是', '否'],
					  yes: function(index, layero){
						  $.ajax({
							    url:'${pageContext.request.contextPath}/operate/performanceTest',
							    type:'post', 
							    async:false, 
							    dataType:'json',
							    timeout:5000,
							    data:{
							    	sceneId:parseInt(sceneId),
						    		operateType:2
								},   
							    success:function(result,textStatus){
							    	if(result.success){
							    		updateBtnOperateStatus(2);
							    	}else{
							    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
							    	}
							    },
							    error:function(xhr,textStatus){
							        console.log("错误：" + xhr);
							    }
							});
						  layer.closeAll('dialog');
					  }
				});
			}
		});
	}
	function checkSceneStatus() { 
		$.ajax({
		    url:'${pageContext.request.contextPath}/scene/get',
		    type:'post', 
		    data:{
		    	sceneId:parseInt(sceneId)
		    },
		    async:false,    
		    dataType:'json',  
		    success:function(result,textStatus){
		    	if(result.success){
		    		updateBtnOperateStatus(result.data.scene.sceneStatus);
		    	}else{
		    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
		    	}
		    },
		    error:function(xhr,textStatus){
		        console.log('错误：' + xhr);
		    }
		});
	}
	function updateBtnOperateStatus(value){
		if(0 == value){ //初始
			$("#doPressurePage #startBtn").removeClass("layui-btn-disabled").text("启动压测");
    		$("#doPressurePage #stopBtn").addClass("layui-btn-disabled").removeClass("layui-btn-danger").text("停止压测");
    		$("#doPressurePage label.titan-head-label2").siblings("span").text("场景构建成功，可以启动压测");
		}else if(1 == value){ //启动
			$("#doPressurePage #startBtn").addClass("layui-btn-disabled").text("压测进行中...");
    		$("#doPressurePage #stopBtn").removeClass("layui-btn-disabled").addClass("layui-btn-danger");
    		$("#doPressurePage label.titan-head-label2").siblings("span").text("场景正在压测中...");
		}else if(2 == value){ //停止
			$("#doPressurePage #startBtn").addClass("layui-btn-disabled").text("启动压测");
    		$("#doPressurePage #stopBtn").addClass("layui-btn-disabled").text("停止压测中...");
    		$("#doPressurePage label.titan-head-label2").siblings("span").text("停止压测中...");
		}
		window.clearInterval(titanTimer1); //关闭旧定时任务
		if((1 == value) || (2 == value)){
			titanTimer1 = setInterval(checkSceneStatus,2000); 
		}
	}
}
</script>