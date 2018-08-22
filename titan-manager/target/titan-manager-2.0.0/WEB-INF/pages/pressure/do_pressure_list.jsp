<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.layui-this{
	border-top: 1px solid #0B6295;
	margin-top: -1px;
} 
</style>
<div class="layui-container" style="width: 100%;" id="pressureListPage">
	<div class="layui-row titan-content-div">
		<div class="layui-col-md5">
			<div class="layui-row">
				<i class="layui-icon titan-search-input-icon">&#xe615;</i>
				<input type="text" placeholder="请输入场景名称" class="layui-input titan-search-input">
			</div>
		</div>
	</div>
	<div class="layui-row">
		<div class="layui-tab" lay-filter="pressureSceneListTab">
			  <ul class="layui-tab-title">
			    <li class="layui-this" lay-id="1">压测场景列表</li>
			    <li lay-id="2">自动化压测任务列表</li>
			  </ul>
			  <div class="layui-tab-content">
				    <div class="layui-tab-item layui-show">
					    	<fieldset class="layui-elem-field">
								<!-- <legend>压测场景列表</legend> -->
								<div class="layui-field-box layui-form">
									<table class="layui-table titan-data-table" lay-skin="line">
										<thead>
											<tr>
												<th>场景ID</th>
												<th>场景名称</th>
												<th>创建时间</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody></tbody>
									</table>
								</div>
							</fieldset>
							<div class="titan-pagebar"></div>
				    </div>
				    <div class="layui-tab-item">
					    	<fieldset class="layui-elem-field">
								<div class="layui-field-box layui-form">
									<table class="layui-table titan-data-table2" lay-skin="line">
										<thead>
											<tr>
												<th>场景ID</th>
												<th>场景名称</th>
												<th>定时压测开始时间</th>
												<th>压测次数</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody></tbody>
									</table>
								</div>
							</fieldset>
							<div class="titan-pagebar2"></div>
				    </div>
			  </div>
		</div>
	</div>
</div>
<!-- 自动化压测任务新增、编辑页 -->
<div id="automaticEditPage" style="display:none;padding-top: 16px;" >
		<form class="layui-form">
			<div class="layui-form-item">
			    <label class="layui-form-label">压测场景:</label>
			    <div class="layui-input-block" style="width: 340px;">
			      <input type="text" name="scene_name" autocomplete="off" class="layui-input" readonly="readonly">
			    </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">开始时间:</label>
				<div class="layui-input-block" style="width: 340px;">
					<input type="text" name="start_time" placeholder="请选择时间" autocomplete="off" class="layui-input" id="taskTimeInput" readonly="readonly">
				</div>
			</div>
			  
			<div class="layui-form-item">
			    <label class="layui-form-label">执行次数:</label>
			    <div class="layui-input-block" style="width: 340px;">
			      <input type="number" name="pressure_times" placeholder="请输入压测次数" value="1" autocomplete="off" class="layui-input">
			    </div>
			</div>
			<div class="layui-form-item">
				 <div class="layui-input-block" >
				     <a class="layui-btn titan-btn-save" style="width: 162px;">保&nbsp;&nbsp;存</a>
      				 <a class="layui-btn layui-btn-primary titan-btn-cancel" style="width: 162px;">取&nbsp;&nbsp;消</a>
				 </div>
			</div>
		</form>
</div>
<script>
$(function() { 
	pressureListPage();
});
function pressureListPage(){
	refreshData(0,titanPageSize,0);
	bindKeydownEvent();
	bindClickTabEvent();
	saveAutomaticTask();
	var task_id,scene_id;
	
	//弹层输入框
	laydate.render({
	    elem: '#taskTimeInput',
	    type: 'time',
	    value: '00:00:00',
	    theme: '#0B6295'
	 });
	
	function refreshData(pageIndex,pageSize,totalCount,filterCondition){
		titanInitParam();//初始化
		$.ajax({
		    url:'${pageContext.request.contextPath}/scene/list',
		    type:'post',
		    async: false,
		    dataType:'json', 
		    data:{
		        filterCondition:filterCondition,
		        pageIndex:pageIndex,
		        pageSize:pageSize
			},   
		    success:function(result,textStatus){
		    	if(result.success){
		    		var dataLength = result.data.records.length;
		    		var dataHtml = "";
		    		if(dataLength > 0){
			    		for(var i=0;i<dataLength;i++){
			    			var item = result.data.records[i];
			    			var tagHtml = '',opHtml = '<a href="javascript:;" class="titan-btn-am"><i class="layui-icon">&#xe61f;</i>自动化压测</a>';
			    			if(1 == item.isAutomatic){
			    				tagHtml = '<span>&nbsp;<i class="layui-icon" style="color:#0B6295;font-size: 24px;">&#xe60e;</i></span>';
			    				opHtml = '<a href="javascript:;" style="color:#000000;cursor:default;"><i class="layui-icon">&#xe61f;</i>自动化压测</a>';
			    			}
			    			dataHtml = dataHtml 
					    			+'<tr>'
					    			+'<td class="titan-data-id"><span>' + item.sceneId + '</span>' + tagHtml + '</td>'
					    			+'<td class="titan-data-name">' + item.sceneName + '</td>'
					    			+'<td>' + titanFormdate(item.createTime) + '</td>'
							    	+'<td>' + getFormatHtml(item.sceneStatus) + '</td>'
							    	+'<td><a href="javascript:;" class="titan-btn-topage"><i class="layui-icon">&#xe623;</i>进入操作页</a>' + opHtml + '</td>'
					    			+'</tr>';
			    		}
		    		}else{
		    			dataHtml = '<tr><td style="text-align: center;color: #0B6295;" colspan="5">未查询到数据</td></tr>';
		    		}
		    		//分页条绑定
		    		if(totalCount != result.data.totalCount){
		    			toPage(result.data.totalCount,pageSize,$('.titan-pagebar'));
		    		} 
		    	}else{
		    		dataHtml = '<tr><td style="text-align: center;color: #0B6295;" colspan="5">' + result.errorCode + ":" + result.errorMsg + '</td></tr>';
		    	}
		    	//table填充数据
		    	$("#pressureListPage table.titan-data-table").find("tbody").html(dataHtml);
		    	toOpratePage();
		    	automationPressureTest();
		    },
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
		    }
		}); 
	}
	function toPage(totalCount,pageSize,selectorId){
		laypage.render({
	    	elem:selectorId, 
	    	limit:pageSize,
	    	count: totalCount, 
	    	jump: function(obj, first){
	    		if(!first){
	    			refreshData(obj.curr-1,obj.limit,totalCount,$.trim($("#pressureListPage input.titan-search-input").val()));
	    		}
	    	}
		});
	}
	function getFormatHtml(status){
		var formatHtml = "";
		if(0 == status){
			formatHtml = '<span>未开始</span>';
		}else if(1 == status){
			formatHtml = '<span style="color:#FF5722;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;&nbsp;</i>压测进行中</span>';
		}else if(2 == status){
			formatHtml = '<span style="color:#FFB800;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;&nbsp;</i>停止压测中</span>';
		}
		return formatHtml;
	}
	function bindKeydownEvent(){
		$("#pressureListPage input.titan-search-input").keydown(function(e){
			if(e.keyCode==13){
				element.tabChange('pressureSceneListTab', 1); //tab切回
				var value = $.trim($(this).val());
				refreshData(0,titanPageSize,0,value);
			}
		});
	}
	function toOpratePage(){
		$("#pressureListPage table.titan-data-table").find("tbody").find('td a.titan-btn-topage').on("click",function(){
			var id = $(this).parents("tr").find('td.titan-data-id').text();
			$mainContent.data('pressure',id);
			$mainContent.empty().load('${pageContext.request.contextPath}/pages/pressure/do_pressure');
		});
	}
	function automationPressureTest(){
		var index = 0;
		$("#pressureListPage table.titan-data-table").find("tbody").find('td a.titan-btn-am').on("click",function(){
			scene_id = $(this).parents("tr").find('td.titan-data-id span:eq(0)').text();
			scene_name = $(this).parents("tr").find('td.titan-data-name').text();
			$("#automaticEditPage input[name='scene_name']").val(scene_name);
			index = layer.open({
				type: 1,
			    title: '新建',
			    icon: 5,
			    closeBtn: false,
			    shade: 0,
			    move: false,
			    resize: false, 
			    area: ['480px', '280px'], 
			    content:$("#automaticEditPage"),
			});
		});
		
		//取消
		$("#automaticEditPage a.titan-btn-cancel").on("click",function(){
			layer.closeAll();
			clearInput();
		});
		
	}
	function saveAutomaticTask(){
		$("#automaticEditPage a.titan-btn-save").on("click",function(){
			var start_time = $("#automaticEditPage input[name='start_time']").val();
			var pressure_times = $("#automaticEditPage input[name='pressure_times']").val();
			var scene_name = $("#automaticEditPage input[name='scene_name']").val();
			var url = '${pageContext.request.contextPath}/task/add';
			if("" == start_time){
				start_time = "00:00:00";
			}
			if(("" == pressure_times) || (0 >= pressure_times)){
				pressure_times = 1;
			}
			if(100 < pressure_times){
				layer.tips('执行次数不能大于100次', 'input[name="pressure_times"]',{tips: [1, '#3da5e2'],time: 2000});
				return;
			}
			var params = {
				sceneId:parseInt(scene_id),
		    	sceneName:scene_name,
		    	startTime:titanFormatTimeStr(start_time), 
		        pressureTimes:parseInt(pressure_times)
			};
			if(task_id){
	 			url = '${pageContext.request.contextPath}/task/update';
	 			params.automaticTaskId = parseInt(task_id);
		 	} 
			$.ajax({
			    url:url,
			    type:'post', 
			    async:false, 
			    dataType:'json',  
			    data:params,   
			    success:function(result,textStatus){
			    	if(result.success){
			    		layer.closeAll();
			    		clearInput();
			    		element.tabChange('pressureSceneListTab', 2); 
			    	}else{
			    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
			    	} 
			    },
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	layer.closeAll('page');
			    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
			    }
			});
		});
	}
	function clearInput(){
		 $("#automaticEditPage input[name='start_time']").val("00:00:00");
		 $("#automaticEditPage input[name='pressure_times']").val("1");
		 task_id = null,scene_id = null;
	}
	function bindClickTabEvent(){
		element.on('tab(pressureSceneListTab)', function(data){
			  if(0 == data.index){
				  refreshData(0,titanPageSize,0);
			  }else if(1 == data.index){
				  refreshTaskData(0,titanPageSize,0);
			  }
		});
	}
	function refreshTaskData(pageIndex,pageSize,totalCount){
		titanInitParam();//初始化
		$.ajax({
		    url:'${pageContext.request.contextPath}/task/list',
		    type:'post',
		    async: false,
		    dataType:'json', 
		    data:{
		        pageIndex:pageIndex,
		        pageSize:pageSize
			},   
		    success:function(result,textStatus){
		    	if(result.success){
		    		var dataLength = result.data.records.length;
		    		var dataHtml = "";
		    		if(dataLength > 0){
			    		for(var i=0;i<dataLength;i++){
			    			var item = result.data.records[i];
			    			dataHtml = dataHtml
					    			+'<tr>'
					    			+'<td class="titan-data-id" style="display:none;">' + item.automaticTaskId + '</td>'
					    			+'<td>' + item.sceneId + '</td>'
					    			+'<td>' + item.sceneName + '</td>'
					    			+'<td>' + item.startTime + '</td>'
					    			+'<td>' + item.pressureTimes + '</td>'
					    			+'<td>'
					    			+'	<a href="javascript:;" class="titan-btn-am-edit"><i class="layui-icon">&#xe642;</i>编辑</a>'
					    			+'	<a href="javascript:;" class="titan-btn-am-del"><i class="layui-icon">&#xe640;</i>删除</a>'
					    			+'</td>'
					    			+'</tr>';
			    		}
		    		}else{
		    			dataHtml = '<tr><td style="text-align: center;color: #0B6295;" colspan="5">未查询到数据</td></tr>';
		    		}
		    		//分页条绑定
		    		if(totalCount != result.data.totalCount){
		    			toPage2(result.data.totalCount,pageSize,$('.titan-pagebar2'));
		    		}  
		    	}else{
		    		dataHtml = '<tr><td style="text-align: center;color: #0B6295;" colspan="5">' + result.errorCode + ":" + result.errorMsg + '</td></tr>';
		    	}
		    	//table填充数据
		    	$("#pressureListPage table.titan-data-table2").find("tbody").html(dataHtml);
		    	bindTaskOperateBtn();
		    },
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
		    }
		}); 
	}
	function toPage2(totalCount,pageSize,selectorId){
		laypage.render({
	    	elem:selectorId, 
	    	limit:pageSize,
	    	count: totalCount, 
	    	jump: function(obj, first){
	    		if(!first){
	    			refreshTaskData(obj.curr-1,obj.limit,totalCount);
	    		}
	    	}
		});
	}
	function bindTaskOperateBtn(){
		//编辑
		$("#pressureListPage table.titan-data-table2").find("tbody").find('td a.titan-btn-am-edit').on("click",function(){
			var id = $(this).parents("tr").find('td.titan-data-id').text();
			$.ajax({
			    url:'${pageContext.request.contextPath}/task/get',
			    type:'post', 
			    async:false, 
			    dataType:'json', 
			    data:{
			    	automaticTaskId:id
				},   
			    success:function(result,textStatus){
			    	if(result.success){
			    		var params = result.data;
			    		task_id = params.automaticTaskId;
			    		scene_id = params.sceneId;
						$("#automaticEditPage input[name='scene_name']").val(params.sceneName);
						$("#automaticEditPage input[name='start_time']").val(params.startTime);
						$("#automaticEditPage input[name='pressure_times']").val(params.pressureTimes);
						index = parent.layer.open({
							type: 1,
						    title: '编辑',
						    icon: 5,
						    closeBtn: false,
						    shade: 0,
						    move: false,
						    resize: false, 
						    area: ['480px', '280px'], 
						    content:$("#automaticEditPage"),
						});
			    	}else{
			    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
			    	}
			    },
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
			    }
			});
		});
		//删除
		$("#pressureListPage table.titan-data-table2").find("tbody").find('td a.titan-btn-am-del').on("click",function(){
			var id = $(this).parents("tr").find('td.titan-data-id').text();
			layer.open({
				  content: '确认删除数据吗？',
				  icon: 3,
				  offset: '200px',
				  shadeClose :true,
				  btn: ['是', '否'],
				  yes: function(index, layero){
					  layer.closeAll('dialog');
					  $.ajax({
						    url:'${pageContext.request.contextPath}/task/del',
						    type:'post', 
						    async:false, 
						    dataType:'json', 
						    data:{
						    	automaticTaskId:id
							},   
						    success:function(result,textStatus){
						    	if(result.success){
						        	layer.alert("删除成功", {icon: 1});
						        	refreshTaskData(0,titanPageSize,0);
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
		});
	}
}
</script>