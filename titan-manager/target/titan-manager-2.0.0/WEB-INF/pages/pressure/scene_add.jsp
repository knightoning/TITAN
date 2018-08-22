<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
.layui-form-label{
		width: 125px;
}
.layui-input-block{
	margin-left: 155px;
}
.layui-form-label font{
	color: red;
}
.titan-div-show{
	min-height:60px;
	overflow:auto;
	max-height: 100px;
}
.titan-div-show span{
	padding: 5px 15px;
    border: 1px solid #ddd;
    margin-top: 5px;
    display: inline-block;
    border-radius: 14px;
    background: #3da5e2;
    color: #fff;
    cursor: pointer;
    box-shadow: 0px 0px 8px -1px rgba(10, 76, 43, 0.5);
}
.titan-div-show i{
	font-size: 14px; 
	color: black;
}

.ajaxLinklist{
	position: absolute;
	width: 100%;
	margin-top: -20px;
	padding: 10px 0;
	z-index: 10;
}
.ajaxLinklist li{
    background: #FFFFFF;
    border-bottom:1px solid #eee;
    text-indent: 10px;
    line-height: 30px;
    cursor: pointer;
}
.ajaxLinklist li:hover {
	background: #3da5e2;
	color: #FFFFFF;
}
.titan-div-link{
	margin: 0 15px 0 15px;
	max-height:200px;
	overflow:auto;
	border-left: 1px solid #eee;
}
</style>
<div class="layui-container" style="width: 100%;" id="sceneAddPage">
	<div class="layui-row titan-content-div">
		<div class="layui-col-md7 layui-elem-quote">
			场景管理 &gt;&gt;<label class="titan-head-label">新增场景</label>
		</div>
	</div>
	<div class="layui-row">
		<div class="layui-col-md7">
			<form class="layui-form" method="post"> 
			  <div class="layui-form-item">
			    <label class="layui-form-label"><font>*</font>&nbsp;场景名：</label>
			    <div class="layui-input-block">
			      <input type="text" name="scene_name" lay-verify="required" autocomplete="off" placeholder="请输入场景名（10字符内）" class="layui-input" maxlength="10">
			    </div>
			  </div>
			  
			  <div class="layui-form-item">
			    <div class="layui-inline">
			      <label class="layui-form-label">持续时间：</label>
			      <div class="layui-input-inline" style="width: 40px;">
			        <input type="text" name="duration_hour" lay-verify="number"  autocomplete="off" class="layui-input" value="0">
			      </div>
			      <div class="layui-form-mid">时</div>
			      <div class="layui-input-inline" style="width: 40px;">
			        <input type="text" name="duration_min" lay-verify="number" autocomplete="off" class="layui-input" value="0">
			      </div>
			      <div class="layui-form-mid">分</div>
			      <div class="layui-input-inline" style="width: 40px;">
			        <input type="text" name="duration_sec" lay-verify="number" autocomplete="off" class="layui-input" value="0">
			      </div>
			      <div class="layui-form-mid">秒</div>
			    </div>
			  </div>
			  
			 <div class="layui-form-item">
				    <label class="layui-form-label"><font>*</font>&nbsp;使用agent数：</label>
				    <div class="layui-input-inline" >
				      <input type="text" name="agent_num" lay-verify="required|number" autocomplete="off" placeholder="请输入agent数" class="layui-input">
				    </div>
			  </div>
			  
			  <div class="layui-form-item">
				    <label class="layui-form-label"><font>*</font>&nbsp;并发用户数：</label>
				    <div class="layui-input-inline">
				      	<input type="text"  name="user_num" lay-verify="required|number" autocomplete="off" placeholder="请输入并发用户数" class="layui-input">
				    	<span class="titan-span-desc"></span>
				    </div>
			   </div>
			   
			    <div class="layui-form-item">
				    <label class="layui-form-label">起步并发用户数：</label>
				    <div class="layui-input-inline">
				      	<input type="text"  name="user_start_num" value="0" lay-verify="required|number" autocomplete="off" placeholder="请输入起步并发用户数" class="layui-input">
				    	<span class="titan-span-desc"></span>
				    </div>
			   </div>
			   
			   <div class="layui-form-item">
				    <label class="layui-form-label"><font>*</font>&nbsp;总并发请求数：</label>
				    <div class="layui-input-inline">
				      <input type="text"  name="total_num" lay-verify="required|number" autocomplete="off" placeholder="请输入总并发请求数" class="layui-input">
				      <span class="titan-span-desc" id="calculateTotalNumSpan"></span>
				    </div>
			   </div>
			   
			   <div class="layui-form-item">
				    <label class="layui-form-label"><font>*</font>&nbsp;期待吞吐量：</label>
				    <div class="layui-input-inline">
				      <input type="text" name="expect_tps" lay-verify="required|number" autocomplete="off" placeholder="请输入期待吞吐量" class="layui-input">
				    </div>
			   </div>
			   
			   <div class="layui-form-item">
				    <label class="layui-form-label"><font>*</font>&nbsp;选择链路：</label>
				    <div class="layui-input-block">
				      <table class="layui-table titan-data-table-s">
							<thead>
								<tr>
									<th>链路ID</th>
									<th>链路名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody></tbody>
							<tfoot>
								<tr>
									<td colspan="3" >
										<i class="layui-icon" style="position: absolute;margin-left: 5px;margin-top: 10px;">&#xe654;</i>
										<input type="text" name="link_name" placeholder="请输入您要添加的链路名" class="layui-input titan-search-input-s" autocomplete="off">
									</td>
								</tr>
								<tr>
									<td colspan="3" style="height: 0px;min-height: 0px;padding: 0;">
										<ul class="ajaxLinklist" id='ajaxLinklist'>
											<div id="linkResultDiv" class="titan-div-link"></div>
										</ul>
									</td>
								</tr>
							</tfoot>
						</table>
				    </div>
			   </div>
			   
			   <div class="layui-form-item">
				    <label class="layui-form-label"><font>*</font>&nbsp;链路串行关系：</label>
				    <div class="layui-input-block">
				    	<a href="javascript:;" class="layui-btn layui-btn-small titan-btn titan-btn-relation">添加串行关系</a>
				      	<input name="link_relation" style="width:66%;display:inline-block;" type="text" lay-verify="required" autocomplete="off" placeholder="按顺序输入链路ID,用英文','隔开,例如:01,02" class="layui-input">
				    </div>
				    <div class="layui-input-block">
				     <table class="layui-table" id="linkRelationTab" >
				     		<thead>
				     			<tr>
				     				<th><i class="layui-icon">&#xe64c;&nbsp;</i>链路调用关系</th>
				     			</tr>
				     		</thead>
							<tbody>
								<tr>
									<td><div class="titan-div-show"></div></td>
								</tr>
							</tbody>
						</table>
				    </div>
			   </div>
			  <div class="layui-form-item">
			    <div class="layui-input-block" >
			  	  	 <a class="layui-btn titan-btn" lay-submit lay-filter="save">保存</a>
     				 <a class="layui-btn layui-btn-primary titan-btn-cancel">取消</a>
			    </div>
			  </div> 
			  
			</form>
		</div>
		
	</div>
	
</div>
<script> 
$(function() { 
	sceneAddPage();
});
function sceneAddPage(){
	form.render();
	
	var includeLinkList = [],includeIdArr=[];
	
	var sceneId = $mainContent.data('sceneid');
	if(sceneId){
		$("#sceneAddPage label.titan-head-label").text("修改场景");
		$("#sceneAddPage a.titan-btn-relation").text("修改串行关系");
		getDataDetail(sceneId);
	}
	bindSubmitBtn();
	bindKeydownEvent();
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
		    		$("#sceneAddPage input[name='scene_name']").val(params.sceneName);
		    		$("#sceneAddPage input[name='duration_hour']").val(params.durationHour);
		    		$("#sceneAddPage input[name='duration_min']").val(params.durationMin);
		    		$("#sceneAddPage input[name='duration_sec']").val(params.durationSec);
		    		$("#sceneAddPage input[name='agent_num']").val(params.useAgent);
		    		
		    		$("#sceneAddPage input[name='user_num']").val(params.concurrentUser);
		    		$("#sceneAddPage input[name='user_start_num']").val(params.concurrentStart);
		    		$("#sceneAddPage input[name='total_num']").val(params.totalRequest);
		    		$("#sceneAddPage input[name='expect_tps']").val(params.expectTps);
		    		$("#sceneAddPage input[name='link_relation']").val(params.linkRelation);
		    		
		    		$("#sceneAddPage input[name='scene_name']").attr("readonly",true);
		    		
		    		includeLinkList = result.data.linkList;  //赋值给全局变量
		    		includeIdArr = params.containLinkid.split(",");
		    		refreshIncludeLinkList(includeLinkList);
		    		refreshLinkRelation(params.linkRelation);
		    	}else{
		    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
		    	}
		    },
		    error:function(xhr,textStatus){
		        console.log('错误:' + xhr);
		    }
		});
	}
	function bindSubmitBtn(){
		//保存
		form.on('submit(save)', function(data){
			var formParam = data.field;
			
			var hour = formParam.duration_hour,min = formParam.duration_min,sec = formParam.duration_sec;
			if((0 != hour) || (0 != min) || (0 != sec)){
				var time = hour * 60 * 60 + min * 60 + sec*1;
				if((60 > time) || (86400 < time)){
					layer.tips('持续时间范围需为1分钟~1天', 'input[name="duration_sec"]',{tips: [2, '#3da5e2'],time: 2000});
					return;
				}
			}
			if(0 >= formParam.agent_num){
	 			layer.tips('使用agent数需为正整数', 'input[name="agent_num"]',{tips: [2, '#3da5e2'],time: 1000});
	 			return;
	 		}
	 		if(0 >= formParam.user_num){
	 			layer.tips('并发用户数需为正整数', 'input[name="user_num"]',{tips: [2, '#3da5e2'],time: 1000});
	 			return;
	 		}
	 		if(0 > formParam.user_start_num){
	 			layer.tips('起步并发用户数需为非负数', 'input[name="user_start_num"]',{tips: [2, '#3da5e2'],time: 1000});
	 			return;
	 		}
	 		if(0 >= formParam.total_num){
	 			layer.tips('总并发请求数需为正整数', 'input[name="total_num"]',{tips: [2, '#3da5e2'],time: 1000});
	 			return;
	 		}
	 		if(0 >= formParam.expect_tps){
	 			layer.tips('期待吞吐量需为正整数', 'input[name="expect_tps"]',{tips: [2, '#3da5e2'],time: 1000});
	 			return;
	 		}
	 		var times_check_num = 2000;
	 		if(times_check_num < (formParam.user_num / formParam.agent_num)){ //并发用户数不能大于agent数的10000倍
	 			layer.tips('并发用户数不能大于agent数的' + times_check_num +'倍', 'input[name="user_num"]',{tips: [2, '#3da5e2'],time: 1000});
	 			return ;
	 		}
	 		if(0 != (formParam.user_num % formParam.agent_num)){  //并发用户数
	 			layer.tips('并发用户数需为agent数的整数倍', 'input[name="user_num"]',{tips: [2, '#3da5e2'],time: 2000});
	 			return ;
	 		}
	 		if(0 != (formParam.total_num % formParam.user_num)){ //总并发请求数
	 			layer.tips('总并发数需为并发用户数的整数倍', 'input[name="total_num"]',{tips: [2, '#3da5e2'],time: 2000});
	 			return ;
	 		}
	 		if(parseInt(formParam.user_start_num) > parseInt(formParam.user_num * 0.2)){
	 			layer.tips('起步并发用户数不能大于并发用户数的20%', 'input[name="user_start_num"]',{tips: [2, '#3da5e2'],time: 2000});
	 			return;
	 		}
			var result = checkLinkRelation($("#sceneAddPage input[name='link_relation']"));
	 		if(!result){
	 			return;
	 		}
	 		
			var url = '${pageContext.request.contextPath}/scene/add';
	 		var params = {
	 				sceneName:formParam.scene_name,
	 				durationHour:parseInt(formParam.duration_hour),
	 				durationMin:parseInt(formParam.duration_min),
	 				durationSec:parseInt(formParam.duration_sec),
	 				useAgent:parseInt(formParam.agent_num),
	 				concurrentUser:parseInt(formParam.user_num),
	 				concurrentStart:parseInt(formParam.user_start_num),
	 				totalRequest:parseInt(formParam.total_num),
	 				expectTps:parseInt(formParam.expect_tps),
	 				containLinkid:includeIdArr.join(","),
	 				linkRelation:formParam.link_relation
	 		};
	 		if(sceneId){
	 			params.sceneId = parseInt(sceneId),
	 			url = '${pageContext.request.contextPath}/scene/update';
	 		}
	 		$.ajax({
			    url:url,
			    type:'post', 
			    async:false,    
			    data:params,
			    dataType:'json',  
			    success:function(result,textStatus){
			    	if(result.success){
			    		$('#mainContent').empty().load('${pageContext.request.contextPath}/pages/pressure/scene_list');
			    	}else{
			    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
			    	}
			    },
			    error:function(xhr,textStatus){
			        console.log("错误：" + xhr);
			    }
			});
		});
		//取消
		$("#sceneAddPage a.titan-btn-cancel").on("click",function(){
			 $mainContent.empty().load('${pageContext.request.contextPath}/pages/pressure/scene_list');
		});
	}
	function bindKeydownEvent(){
		 $("#sceneAddPage input.titan-search-input-s").keydown(function(e){
			if(e.keyCode==13){
				var value = $.trim($(this).val());
				$.ajax({
					    url:'${pageContext.request.contextPath}/link/list',
					    type:'post',
					    async:false,    
					    dataType:'json',  
					    data:{
					    	filterCondition:value,
					    	pageIndex:0,
					    	pageSize:10
					    },
					    success:function(result,textStatus){
					    	if(result.success){
					    		var strHtml = "";
					    		for(var i=0;i<result.data.records.length;i++){
					    			strHtml +='<li name="'+result.data.records[i].linkId+'">' + result.data.records[i].linkName + '</li>';
					    		}
					    		$("#linkResultDiv").html(strHtml);
					    		binkLinkClickEvent(result.data.records);
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
	function binkLinkClickEvent(linkArr){
		$("#linkResultDiv li").on("click",function(){
			var linkId = $(this).attr("name");
			if(-1 == includeIdArr.indexOf(linkId)){
				for(var i=0;i<linkArr.length;i++){
					if(linkId == linkArr[i].linkId){
						includeLinkList.push(linkArr[i]); 
						includeIdArr.push(linkId);
						break;
					}
				}
				refreshIncludeLinkList(includeLinkList);
			}else{
				console.log("已包含linkId：" + linkId);
			}
		});
	}
	function refreshIncludeLinkList(linkListArr){
		if(null != linkListArr){
			var strHtml = "";
    		for(var i=0;i<linkListArr.length;i++){
    			strHtml += '<tr>'
		    			+	'<th class="titan-data-id">' + linkListArr[i].linkId + '</th>'	
		    			+	'<th>' + linkListArr[i].linkName + '</th>'	
		    			+	'<th><a href="javascript:;"><i class="layui-icon">&#xe640;</i>移除</a></th>'	
		    			+'</tr>';
    		}
    		var tbody_obj = $("#sceneAddPage table.titan-data-table-s").find("tbody");
    		tbody_obj.html(strHtml);
    		//绑定移除按钮
    		tbody_obj.find('a').on("click",function(){
				var id = $(this).parents("tr").find('th.titan-data-id').text();
				for(var i=0;i<includeLinkList.length;i++){
					if(id == includeLinkList[i].linkId){
						titanRemoveArrDataByValue(includeLinkList,includeLinkList[i]);
						titanRemoveArrDataByValue(includeIdArr,id);
						refreshIncludeLinkList(includeLinkList);
						break;
					}
				}
			});
		}
	}
	function refreshLinkRelation(linkRelation){
		if(null != linkRelation){
			var linkRelationHtml = "",arr_temp = linkRelation.split(',');		
	 		for(var i = 0;i < arr_temp.length;i++){
	 			for(var j =0;j<includeLinkList.length;j++){
	 				if(arr_temp[i] == includeLinkList[j].linkId){
	 					linkRelationHtml += '<span>' + includeLinkList[j].linkName + '</span>';
	 					if(i < arr_temp.length-1){
	 						linkRelationHtml += '&nbsp;<i class="layui-icon">&#xe65b;</i>&nbsp;';
	 					}
	 				}
	 			}
	 		}
	 		$("#sceneAddPage div.titan-div-show").html(linkRelationHtml);
		}
	}
	function bindClickEvent(){
		$("#sceneAddPage").not("#linkResultDiv").on("click",function(){
			  $("#linkResultDiv").html("");
		});
		$("#sceneAddPage a.titan-btn-relation").on("click",function(){
			var obj = $("#sceneAddPage input[name='link_relation']");
			checkLinkRelation(obj);
		});
	}
	function checkLinkRelation(obj){
		if(0 >= includeIdArr.length){
 			layer.tips('链路不能为空', '#sceneAddPage table.titan-data-table-s',{tips: [2, '#3da5e2'],time: 1000});
 			return ;
 		}
		var value = $.trim(obj.val());
		if(!value){
			layer.tips('请输入链路串行关系', obj,{tips: [1, '#3da5e2'],time: 1000});
 			return;
		}
		var relationIdArr = value.split(',');
		if(relationIdArr.length != includeLinkList.length){
 			layer.tips('链路关系不正确', obj,{tips: [1, '#3da5e2'],time: 1000});
 			return;
 		}
		for(var i=0;i<relationIdArr.length;i++){
 			if(!isInList(relationIdArr[i])){
 				layer.tips('链路未添加,ID:' + relationIdArr[i], obj,{tips: [1, '#3da5e2'],time: 1000});
 				return;
 			}
 		}
		var nary = relationIdArr.sort(); 
 		for(var i=0;i<relationIdArr.length;i++){ 
	 		if (nary[i] == nary[i+1]){ 
	 			layer.tips('链路ID不能重复,ID:' + nary[i], obj,{tips: [1, '#3da5e2'],time: 1000});
	 			return;
	 		} 
 		} 
		refreshLinkRelation(value);
		$("#sceneAddPage a.titan-btn-relation").text("修改串行关系");
		return true;
	}
	function isInList(linkId){
 		for(var i=0;i<includeLinkList.length;i++){
	 		if(includeLinkList[i].linkId==linkId){
	 			return true;
	 		}
 		}
 		return false;
 	}
}
</script> 