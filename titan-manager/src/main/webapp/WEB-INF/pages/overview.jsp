<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
tr td font{
	color: #3da5e2;
	font-weight:bold;
	padding: 0 2px;
}
.ov-tab-head{
	width:56px;
	background-color: #0B6295;
	color: #FFFFFF;
}
.ov-tab-num{
	cursor: pointer;
}
#overview{
	height:200px;
	width:100%;
	min-width:460px;
	border:1px solid #d1d5da;
	border-radius:5px;
	display:inline-block;
}
.titan-overview-operate{
	/* background-color: gray;   */ 
	position: relative;
	top: -32px;
	left: 18px;
	width: 180px;
}
.titan-overview-operate span{
	padding: 0px 10px;
	font-weight: bolder;
	cursor: pointer;
}
.titan-overview-operate i{
	font-size: 20px;
}
.titan-overview-operate i:hover{
	font-size: 21px;
}
.titan-overview-operate i:active{
	font-size: 20px;
}
.titan-overview-operate .titan-overview-start{
	color: #2CB044;
}
.titan-overview-operate .titan-overview-stop{
	color: #FF5722;
}
.titan-overview-operate .titan-overview-restart{
	color: #7BC96F;
}

.titan-overview-disabled{
	color:#A3A3A3;
    cursor: not-allowed;
}

</style>
<div class="layui-container titan-content-div" style="width: 100%;" id="overviewPage">  
	<div class="layui-row layui-col-space30 " >
		<div class="layui-col-md6" >
			<div class="layui-row layui-elem-quote">当前Agent状态：</div>
			<div class="layui-row">
				<div id="overview"></div>
				<div class="titan-overview-operate">
					<label>操作：</label> 
					<span><i class="layui-icon titan-overview-start" title="启动">&#xe652;</i></span>
					<span><i class="layui-icon titan-overview-stop" title="停止">&#xe651;</i></span>
					<span><i class="layui-icon titan-overview-restart" title="重启">&#x1002;</i></span>
				</div>
			</div>
		</div>
		<div class="layui-col-md6" >
			<div class="layui-row layui-elem-quote">我的全链路压测：</div>
	   		<div class="layui-row" style="min-width: 753px;">
		   		<table class="layui-table" style="height: 200px;">
				  <tbody>
				    <tr>
				      <td class="ov-tab-head"><i class="layui-icon">&#xe64c;&nbsp;</i>链路</td>
				      <td class="ov-tab-num">您当前有<font id="ov_count_link">0</font>个链路</td>
				    </tr>
				    <tr>
				      <td class="ov-tab-head"><i class="layui-icon">&#xe715;&nbsp;</i>场景</td>
				      <td class="ov-tab-num">您当前有<font id="ov_count_scene">0</font>个场景</td>
				    </tr>
				     <tr>
				      <td class="ov-tab-head"><i class="layui-icon">&#xe63c;&nbsp;</i>报告</td>
				      <td class="ov-tab-num">您当前有<font id="ov_count_report">0</font>个报告</td>
				    </tr>
				  </tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- <hr>  -->
	<div class="layui-row titan-content-div" style="margin-top: 70px;">
		<div class="layui-row layui-elem-quote">Agent性能监控：</div>
		<div class="layui-col-md4" >
			<fieldset class="layui-elem-field">
				<legend style="font-size: 16px;">CPU使用率(%)</legend>
				<div id="cpuview" class="layui-field-box" style="height: 280px;width: 95%;">
				</div>
			</fieldset>	
		</div>
		<div class="layui-col-md4" >
			<fieldset class="layui-elem-field">
				<legend style="font-size: 16px;">内存使用率(%)</legend>
				<div id="memoryview" class="layui-field-box" style="height: 280px;width: 95%;">
				</div>
			</fieldset>	
		</div>
		<div class="layui-col-md4" >
			<fieldset class="layui-elem-field">
				<legend style="font-size: 16px;">磁盘IOPS(次/秒)</legend>
				<div id="iopsview" class="layui-field-box" style="height: 280px;width: 95%;">
				</div>
			</fieldset>	
		</div>
	</div>
	 
</div>
<script src="${pageContext.request.contextPath}/plugins/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/overview.js"></script>
<script>
$(function(){
	overviewPage();
});
function overviewPage(){
	refreshData();
	bindTabClickEvent();
	bindOperateBtn();
	refreshMonitorData();
	
	function refreshData(){
		$.ajax({
		    url:'${pageContext.request.contextPath}/overview/getOverviewData',
		    type:'get', 
		    dataType:'json',  
		    success:function(result,textStatus){
		    	if(result.success){
		    		//agent图
		    		overviewAgentChart(result.data.availableNodesNum,result.data.totalNodesNum,result.data.availableNodesIPList,result.data.usedNodesIPList,"overview");
		    		//统计表
		    		$("#ov_count_link").text(result.data.totalLinkNum);
		    		$("#ov_count_scene").text(result.data.totalSceneNum)
		    		$("#ov_count_report").text(result.data.totalReportNum);
		    	}else{
		    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
		    	}
		    },
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
		    }
		});
	}
	function bindTabClickEvent(){
		$("#ov_count_link").parent().on("click",function(e){
			$('#linkPageBtn').trigger("click");
		});
		$("#ov_count_scene").parent().on("click",function(e){
			$('#scenePageBtn').trigger("click");
		});
		$("#ov_count_report").parent().on("click",function(e){
			$('#pressureReportPageBtn').trigger("click");
		});
	}
	function bindOperateBtn(){
		$("#overviewPage .titan-overview-operate").find("i").on("click",function(){
			if($(this).hasClass("titan-overview-disabled")){
				return;
			}
			var operateType = 0,operateDesc = "";
			if($(this).hasClass("titan-overview-start")){
				operateType = 1;
				operateDesc = "启动";
			}else if($(this).hasClass("titan-overview-stop")){
				operateType = 2;
				operateDesc = "停止";
			}else if($(this).hasClass("titan-overview-restart")){
				operateType = 3;
				operateDesc = "重启";
			}
			var _this = $(this);
			 layer.open({
				  content: '确认执行&nbsp;<span style="color:#FF5722;font-weight: bold;">' + operateDesc + '</span>&nbsp;操作吗？',
				  icon: 3,
				  offset: '200px',
				  shadeClose :true,
				  btn: ['是', '否'],
				  yes: function(index, layero){
					  var index = layer.load(2, {
						  time: 10*1000,
						  shade:0.5
						  }); 
					  $.ajax({
						    url:'${pageContext.request.contextPath}/operate/agent',
						    type:'post', 
						    dataType:'json',  
						    data:{
						    	operateType:operateType
							},   
						    success:function(result,textStatus){
						    	layer.close(index);
						    	if(result.success){
						    		layer.alert("操作成功", {icon: 1});
						    	}else{
						    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
						    	} 
						    },
						    error:function(xhr,textStatus){
						        console.log('错误：' + xhr);
						    }
					  });
					  layer.closeAll('dialog');
				  }
			});
		});
	}
	function refreshMonitorData(){
		getMonitor();//第一次
		window.clearInterval(titanTimer2); //关闭旧定时任务
		titanTimer2 = setInterval(getMonitor,5000); 
		function getMonitor(){
			$.ajax({
			    url:'${pageContext.request.contextPath}/overview/monitor',
			    type:'post', 
			    dataType:'json',  
			    success:function(result,textStatus){
			    	if(result.success){
			    		var cpu_data1 = [],mem_data1 = [],iops_data1 = [],cpu_data2 = [],mem_data2 = [],iops_data2 = [];
			    		var ip_flag = null,ip1,ip2;
			    		var dataLength = result.data.length;
			    		for(var i=0;i<dataLength;i++){
			    			var item = result.data[i];
			    			if(null == ip_flag){
		    					ip_flag = item.ip;
		    					ip1 = item.ip;
		    				}else{
		    					ip2 = item.ip;
		    				}
			    			var cpu = new Array();
			    			var mem = new Array();
			    			var iops = new Array();
			    			cpu[0] = titanFormdateMin(item.createTime);
			    			cpu[1] = item.cpuUsage;
			    			mem[0] = titanFormdateMin(item.createTime);
			    			mem[1] = item.memoryUsage;
			    			iops[0] = titanFormdateMin(item.createTime);
			    			iops[1] = item.iops;
			    			
			    			if(ip_flag == item.ip){
			    				cpu_data1.unshift(cpu);
				    			mem_data1.unshift(mem);
				    			iops_data1.unshift(iops);
			    			}else{
			    				cpu_data2.unshift(cpu);
				    			mem_data2.unshift(mem);
				    			iops_data2.unshift(iops);
			    			}
			    			
			    		}
			    		agentChart(cpu_data1,cpu_data2,ip1,ip2,window.document.getElementById("cpuview"),0);
			    		agentChart(mem_data1,mem_data2,ip1,ip2,window.document.getElementById("memoryview"),1);
			    		agentChart(iops_data1,iops_data2,ip1,ip2,window.document.getElementById("iopsview"),2);
			    	}else{
			    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
			    	}
			    },
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
			    }
			});
		}
	}
	function agentChart(data1,data2,ip1,ip2,selectorId,type){
		if((selectorId == undefined) || (data1 == undefined) || (data2 == undefined) ){
			return;
		}
		var colorArr = ['#3BC0FF','#F2AC38','#55B802'];
		
		var myChartcpu = echarts.init(selectorId);
		var dateList = data1.map(function (item) {
	   		 return item[0];
		});
		//数据少时兼容
		if(data1.length < data2.length){
			dateList = data2.map(function (item) {
		   		 return item[0];
			});
		}
		var valueList1 = data1.map(function (item) {
	  	 	 return item[1];
		});
		var valueList2 = data2.map(function (item) {
	  	 	 return item[1];
		});
		var yAxisMaxSize = null;
		option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:[ip1,ip2],
		        bottom:0
		    },
		    xAxis: [{
		        data: dateList
		    }],
		    yAxis: [{
		        splitLine: {show: true},
			    axisLine:{
		        	show:false
		        },
		        axisTick:{
		        	show:false
		        },
		        max:yAxisMaxSize
		    }],
		    grid:{
		    	top : 20,
				left : 30,
				right : 0,
				bottom: 50
		    },
		    series: [{
		    	name:ip1,
		        type: 'line',
		        showSymbol: false,
		        smooth:true,
		        data: valueList1,
		        areaStyle: {normal: {}},
		        lineStyle:{
		        	normal:{
		        		color:colorArr[type]
		        	}
		        },
		        itemStyle:{
		        	normal:{
		        		color:colorArr[type]
		        	}
		    	}
		    }]
		};
		myChartcpu.setOption(option);
	}
	
}
</script>