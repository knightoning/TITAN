<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="layui-container" style="width: 100%;" id="sceneListPage">
	<div class="layui-row titan-content-div">
		<div class="layui-col-md5">
			<div class="layui-row">
				<i class="layui-icon titan-search-input-icon">&#xe615;</i>
				<input type="text" placeholder="请输入场景名称" class="layui-input titan-search-input">
			</div>
		</div>
		<div class="layui-col-md7" style="text-align: right;">
			<a class="layui-btn layui-btn-small titan-btn" id="add-scene-btn">
				<i class="layui-icon">&#xe608;</i>新增
			</a> 
			<a class="layui-btn layui-btn-small titan-btn titan-btn-del"> 
				<i class="layui-icon">&#xe640;</i>删除
			</a>
		</div>
	</div>
	<div class="layui-row">
		<fieldset class="layui-elem-field">
			<legend>场景列表</legend>
			<div class="layui-field-box layui-form">
				<table class="layui-table titan-data-table" lay-skin="line">
					<thead>
						<tr>
							<th style="text-align: center;">
								<input lay-skin="primary" type="checkbox"><div class="layui-unselect layui-form-checkbox" lay-skin="primary"><i class="layui-icon"></i></div>
							</th>
							<th>场景ID</th>
							<th>场景名称</th>
							<th>创建时间</th>
							<th>修改时间</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</fieldset>
		<div class="titan-pagebar"></div>
	</div>
</div>
<script>
$(function() { 
	sceneListPage();
});
function sceneListPage(){
	$mainContent.removeData('sceneid');
	refreshData(0,titanPageSize,0);
	bindCheckboxAllClickEvent();
	bindKeydownEvent();
	bindDeleteEvent();
	//1、刷新数据
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
			    			dataHtml = dataHtml 
					    			+'<tr>'
					    			+'<td style="text-align: center;width:32px;">'
					    			+'<input lay-skin="primary" type="checkbox">'
					    			+'<div class="layui-unselect layui-form-checkbox" lay-skin="primary"><i class="layui-icon" ></i></div>'
					    			+'</td>'
					    			+'<td class="titan-data-id">' + item.sceneId + '</td>'
					    			+'<td class="titan-data-name">' + item.sceneName + '</td>'
					    			+'<td>' + titanFormdate(item.createTime) + '</td>'
					    			+'<td>' + titanFormdate(item.modifyTime) + '</td>'
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
		    	$("#sceneListPage table.titan-data-table").find("tbody").html(dataHtml);
		    	//checkbox绑定点击事件
	 	    	bindCheckboxClickEvent();
		    	//数据名称绑定点击事件
		    	toDataDetailPage();
		    },
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
		    }
		}); 
	}
	function toDataDetailPage(){
		$("#sceneListPage table.titan-data-table").find("tbody").find('td.titan-data-name').on("click",function(){
			var id = $(this).parents("tr").find('td.titan-data-id').text();
			$mainContent.data('sceneid',id).empty().load('${pageContext.request.contextPath}/pages/pressure/scene_add');
		});
	}
	function toPage(totalCount,pageSize,selectorId){
		laypage.render({
	    	elem:selectorId, 
	    	limit:pageSize,
	    	count: totalCount, 
	    	jump: function(obj, first){
	    		if(!first){
	    			refreshData(obj.curr-1,obj.limit,totalCount,$.trim($("#sceneListPage input.titan-search-input").val()));
	    		}
	    	}
		});
	}
	function bindCheckboxAllClickEvent(){
		$("#sceneListPage table.titan-data-table").find("thead").find('div.layui-form-checkbox').on("click",function(){
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
	function bindCheckboxClickEvent(){
		//绑定一次即清空所有之前选中样式(分页)
		$("#sceneListPage table.titan-data-table").find('div.layui-form-checkbox').removeClass('layui-form-checked');
		//单选点击
		$("#sceneListPage table.titan-data-table").find("tbody").find('div.layui-form-checkbox').on("click",function(){
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
		 	if(titanPageSize == titanDataIdArr.length){
		 		all_temp.addClass('layui-form-checked');
		 	}else{
		 		all_temp.removeClass('layui-form-checked');
		 	}
		});
	}
	function bindKeydownEvent(){
		$("#sceneListPage input.titan-search-input").keydown(function(e){
			if(e.keyCode==13){
				var value = $.trim($(this).val());
				refreshData(0,titanPageSize,0,value);
			}
		});
	}
	function bindDeleteEvent(){
		$("#sceneListPage a.titan-btn-del").on("click",function(){
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
							    url:'${pageContext.request.contextPath}/scene/del',
							    type:'post', 
							    async:false, 
							    dataType:'json', 
							    data:{
							    	ids:titanDataIdArr.join(",")
								},   
							    success:function(result,textStatus){
							    	if(result.success){
							        	layer.alert("删除成功", {icon: 1});
							        	refreshData(0,titanPageSize,0,$.trim($("#sceneListPage input.titan-search-input").val()));
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
	}
}
</script>