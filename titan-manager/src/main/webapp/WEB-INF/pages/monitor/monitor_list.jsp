<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="layui-container" style="width: 100%;" id="monitorListPage">
	<div class="layui-row titan-content-div">
		<!-- <div class="layui-col-md6 layui-elem-quote">
			监控集管理
		</div> -->
		<div class="layui-col-md12" style="text-align: right;">
			<a class="layui-btn layui-btn-small titan-btn titan-btn-add">
				<i class="layui-icon">&#xe608;</i>新增
			</a> 
			<a class="layui-btn layui-btn-small titan-btn titan-btn-del"> 
				<i class="layui-icon">&#xe640;</i>删除
			</a> 
		</div>
	</div>
	<div class="layui-row">
		<fieldset class="layui-elem-field">
			<legend>监控集列表</legend>
			<div class="layui-field-box layui-form">
				<table class="layui-table titan-data-table" lay-skin="line">
					<thead>
						<tr>
							<th style="text-align: center;width:32px;">
								<input lay-skin="primary" type="checkbox"><div class="layui-unselect layui-form-checkbox" lay-skin="primary"><i class="layui-icon"></i></div>
							</th>
							<th>场景名称</th>
							<th>内网IP</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</fieldset>
		<div class="titan-pagebar"></div>
	</div>
</div>
<!-- 监控集合新增、编辑页 -->
<div id="monitorEditPage" style="display:none;padding-top: 16px;" >
	<form class="layui-form">
			<div class="layui-form-item">
			    <label class="layui-form-label">场景名称:</label>
			    <div class="layui-input-block" style="width: 340px;">
			      <input type="text" name="scene_name" autocomplete="off" class="layui-input" placeholder="请输入场景名称">
			    </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">内网IP:</label>
				<div class="layui-input-block" style="width: 340px;">
					<input type="text" name="intranet_ip" placeholder="请输入内网IP（多个','隔开，最多3个）" autocomplete="off" class="layui-input">
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
	monitorListPage();
});
function monitorListPage(){
	refreshData(0,titanPageSize,0);
	bindCheckboxAllClickEvent();
	bindDeleteEvent();
	clickAddBtn();
	clickSaveBtn();
	var data_id,scene_id;
	function refreshData(pageIndex,pageSize,totalCount,filterCondition){
		titanInitParam();
		$.ajax({
		    url:'${pageContext.request.contextPath}/monitor/list',
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
			    			dataHtml = dataHtml 	
			    			+'<tr>'
			    			+	'<td style="text-align: center;width:32px;">'
			    			+		'<input lay-skin="primary" type="checkbox">'
			    			+		'<div class="layui-unselect layui-form-checkbox" lay-skin="primary"><i class="layui-icon" ></i></div>'
			    			+	'</td>'
			    			+'<td class="titan-data-id" style="display:none;">' + item.monitorSetId + '</td>' 
			    			+'<td style="display:none;">' + item.sceneId + '</td>'
			    			+'<td class="titan-data-name">' + item.sceneName + '</td>'
			    			+'<td>' + item.intranetIp + '</td>'
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
		    	$("#monitorListPage table.titan-data-table").find("tbody").html(dataHtml);
		    	//checkbox绑定点击事件
	 	    	bindCheckboxClickEvent(result.data.totalCount);
	 	    	//数据名称绑定点击事件
		    	editDataPage();
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
	    			refreshData(obj.curr-1,obj.limit,totalCount);
	    		}
	    	}
		});
	}
	function bindCheckboxAllClickEvent(){
		$("#monitorListPage table.titan-data-table").find("thead").find('div.layui-form-checkbox').on("click",function(){
			var _this = $(this);
			if(_this.hasClass('layui-form-checked')){
				_this.parents("table").find('div.layui-form-checkbox').removeClass('layui-form-checked');
				titanDataIdArr = [];
			}else{
				_this.parents("table").find('div.layui-form-checkbox').addClass('layui-form-checked');
				titanDataIdArr = [];
				var tabData = _this.parents("table").find("td.titan-data-id");
				for(var i=0;i<tabData.length;i++){
					titanDataIdArr.push($(tabData[i]).text());
				}
			}
		});
	}
	function bindCheckboxClickEvent(dataTotalCount){
		//绑定一次即清空所有之前选中样式(分页)
		$("#monitorListPage table.titan-data-table").find('div.layui-form-checkbox').removeClass('layui-form-checked');
		//单选点击
		$("#monitorListPage table.titan-data-table").find("tbody").find('div.layui-form-checkbox').on("click",function(){
			var _this = $(this);
			var id = _this.parents("tr").find('td.titan-data-id').text();
			if(_this.hasClass('layui-form-checked')){
				_this.removeClass('layui-form-checked');
				titanRemoveArrDataByValue(titanDataIdArr,id);
			}else{
				_this.addClass('layui-form-checked');
		 		titanDataIdArr.push(id);  
			}
			//判断全选按钮样式
			var all_temp = _this.parents("table").find("thead").find("div.layui-form-checkbox");
		 	if(titanPageSize == titanDataIdArr.length || dataTotalCount == titanDataIdArr.length){
		 		all_temp.addClass('layui-form-checked');
		 	}else{
		 		all_temp.removeClass('layui-form-checked');
		 	}
		});
	}
	function bindDeleteEvent(){
		$("#monitorListPage a.titan-btn-del").on("click",function(){
			if(0 >= titanDataIdArr.length){
				layer.alert("请选择要删除的数据", {icon: 7});
	 	 		return;
			}else{
				layer.open({
					  content: '确认删除数据吗？',
					  icon: 3,
					  offset: '200px',
					  shadeClose :true,
					  btn: ['是', '否'],
					  yes: function(index, layero){
						  layer.closeAll('dialog');
						  $.ajax({
							    url:'${pageContext.request.contextPath}/monitor/del',
							    type:'post', 
							    async:false, 
							    dataType:'json', 
							    data:{
							    	ids:titanDataIdArr.join(",")
								},   
							    success:function(result,textStatus){
							    	if(result.success){
							        	layer.alert("删除成功", {icon: 1});
							        	refreshData(0,titanPageSize,0);
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
	}
	function clickAddBtn(){
		$("#monitorListPage a.titan-btn-add").on("click",function(){
			var index = layer.open({
				type: 1,
			    title: '新建',
			    icon: 5,
			    closeBtn: false,
			    shade: 0,
			    move: false,
			    resize: false, 
			    area: ['480px', '280px'], 
			    content:$("#monitorEditPage"),
			});
		});
		//取消
		$("#monitorEditPage a.titan-btn-cancel").on("click",function(){
			layer.closeAll();
			clearInput();
		});
	}
	function editDataPage(){
		$("#monitorListPage table.titan-data-table").find("tbody").find('td.titan-data-name').on("click",function(){
			data_id = $(this).parents("tr").find('td.titan-data-id').text();
			scene_id = $(this).parents("tr").find('td.titan-data-id').next().text();
			var scene_name = $(this).text();
			var intranet_ip = $(this).next().text();
			$("#monitorEditPage input[name='scene_name']").val(scene_name);
			$("#monitorEditPage input[name='intranet_ip']").val(intranet_ip);
			var index = layer.open({
				type: 1,
			    title: '编辑',
			    closeBtn: false,
			    shade: 0,
			    move: false,
			    resize: false, 
			    area: ['480px', '280px'], 
			    content:$("#monitorEditPage"),
			});
		});
	}
	function clearInput(){
		$("#monitorEditPage input[name='scene_name']").val("");
		$("#monitorEditPage input[name='intranet_ip']").val("");
		data_id = null,scene_id=null;
	}
	function clickSaveBtn(){
		$("#monitorEditPage a.titan-btn-save").on("click",function(){
			var scene_name = $("#monitorEditPage input[name='scene_name']").val().trim();
			var intranet_ip = $("#monitorEditPage input[name='intranet_ip']").val().trim();
			if("" == scene_name){
				layer.tips('场景名不能为空', $("#monitorEditPage input[name='scene_name']"),{tips: [1, '#3da5e2'],time: 1000});
				return;
			}
			if("" == intranet_ip){
				layer.tips('内网IP不能为空', $("#monitorEditPage input[name='intranet_ip']"),{tips: [1, '#3da5e2'],time: 1000});
				return;
			}
			var ip_arr = intranet_ip.split(",");
			if(3 < ip_arr.length){
				layer.tips('内网IP不能超过3个', $("#monitorEditPage input[name='intranet_ip']"),{tips: [1, '#3da5e2'],time: 1000});
				return;
			}
			if(1 < ip_arr.length){
				for(var i = 0; i<ip_arr.length;i++){
					if(!titanValidteIP(ip_arr[i])){
						layer.tips('存在内网IP格式错误', $("#monitorEditPage input[name='intranet_ip']"),{tips: [1, '#3da5e2'],time: 1000});
						return;
					}
				}
			}else{
				if(!titanValidteIP(intranet_ip)){
					layer.tips('内网IP格式错误', $("#monitorEditPage input[name='intranet_ip']"),{tips: [1, '#3da5e2'],time: 1000});
					return;
				}
			}
			var url = '${pageContext.request.contextPath}/monitor/add';
			var params = {
		    	sceneName:scene_name,
		    	intranetIp:intranet_ip
			};
			if(data_id){
				url = '${pageContext.request.contextPath}/monitor/update';
	 			params.monitorSetId = parseInt(data_id);
	 			params.sceneId = parseInt(scene_id);
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
			    		refreshData(0,titanPageSize,0);
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
}
</script>