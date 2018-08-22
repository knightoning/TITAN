<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="layui-container" style="width: 100%;" id="reportDetailPage">
	<div class="layui-row titan-content-div">
		<div class="layui-col-md12 layui-elem-quote">
			压测报告 &gt;&gt;<label class="titan-head-label">报告详情</label>
		</div>
	</div>
	<div class="layui-row">
		<div class="layui-col-md12">
			<fieldset class="layui-elem-field">
				<legend>业务指标</legend>
				<div class="layui-field-box">
			    	<div style="position:absolute;height:105px;width:80px;right:90px;top:-40px;text-align:center;line-height: 80px;" class="titan-div-seal">
			    		<img style="height:105px;" ondragstart="return false;" src="" />
			    	</div>
			    	<table class="layui-table titan-data-table" lay-skin="line">
				    	<thead>
				    		<tr><th>并发用户数</th><th>总并发请求数</th><th>HTTP200成功请求数</th><th>业务成功请求数</th><th>HTTP200成功率</th></tr>
				    	</thead>
				    	<tbody class="titan-data-table-1">
				    		 <tr>
				    		 	<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
				    		</tr> 
				    	</tbody>
				    	<thead>
				    		<tr>
				    			<th>业务成功率</th><th>期待吞吐量/s</th><th>实际吞吐量/s</th><th>用户平均请求等待时间/ms</th><th>服务器平均请求等待时间/ms</th>
				    		</tr>
				    	</thead>
				    	<tbody class="titan-data-table-2">
				    		 <tr>
				    		 	<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
				    		</tr> 
				    	</tbody>
				    	<thead>
				    		<tr><th>峰值吞吐量/s</th><th>峰值持续时间</th><th>开始时间</th><th>结束时间</th><th>持续时间</th></tr>
				    	</thead>
				    	<tbody class="titan-data-table-3">
				    		 <tr>
				    		 	<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
					    		<td>&nbsp;</td>
				    		</tr> 
				    	</tbody>
			    	</table>
				</div>
			</fieldset>
		</div>
	</div>
	
	<div class="layui-row">
		<div class="layui-col-md12 layui-elem-quote">
			<label class="titan-head-label">监控集指标</label>
		</div>
		<div class="layui-col-md12">
			<fieldset class="layui-elem-field">
				<legend>CPU使用率(%)</legend>
				<div class="layui-field-box" id="cpuChart" style="height: 320px;width: 98%;"></div>
			</fieldset>
		</div>
		
		<div class="layui-col-md12">
			<fieldset class="layui-elem-field">
				<legend>内存使用率(%)</legend>
				<div class="layui-field-box" id="memoryChart" style="height: 320px;width: 98%;"></div>
			</fieldset>
		</div>
		
		<div class="layui-col-md12">
			<fieldset class="layui-elem-field">
				<legend>磁盘IOPS(次/秒)</legend>
				<div class="layui-field-box" id="iopsChart" style="height: 320px;width: 98%;"></div>
			</fieldset>
		</div>
	</div>
</div>
<script>
$(function() { 
	reportDetailPage();
});
function reportDetailPage(){
	var reportItem = $mainContent.data('reportItem');
	businessData(reportItem);
	refreshMonitorData(reportItem);
	
	function businessData(item){
		var imgPath = {0:'${pageContext.request.contextPath}/images/well.png',1:'${pageContext.request.contextPath}/images/bad.png'};
		$("#reportDetailPage div.titan-div-seal img").attr('src',imgPath[item.conclusion]); 
		var tbody1 = '<tr>'
					+   '<td>'+item.concurrentUser+'</td>'
					+   '<td>'+item.totalRequest+'</td>'
					+   '<td>'+item.successRequest+'</td>'
					+   '<td>'+item.businessSuccessRequest+'</td>'
					+   '<td>'+item.httpSuccessRate+'%</td>'
					+ '</tr>';
		var tbody2 = '<tr>'
					+   '<td>'+item.businessSuccessRate+'%</td>'
					+   '<td>'+item.expectTps+'</td>'
					+   '<td>'+item.actualTps+'</td>'
					+   '<td>'+item.userWaittime+'</td>'
					+   '<td>'+item.serverWaittime+'</td>'
					+ '</tr>';
		var tbody3 = '<tr>'
					+   '<td>'+item.peakThroughput+'</td>'
					+   '<td>'+item.peakDurationTime+'</td>'
					+   '<td>' + titanFormdate(item.startTime) + '</td>'
					+   '<td>' + titanFormdate(item.endTime) + '</td>'
					+   '<td>' + item.durationTime + '</td>'
					+ '</tr>';
		var tabObj = $("#reportDetailPage table.titan-data-table");		
		tabObj.find("tbody.titan-data-table-1").html(tbody1);
	 	tabObj.find("tbody.titan-data-table-2").html(tbody2);
		tabObj.find("tbody.titan-data-table-3").html(tbody3); 
	}
	
	function refreshMonitorData(reportItem){
		getMonitor();
		function getMonitor(){
			$.ajax({
			    url:'${pageContext.request.contextPath}/monitor/chartdetail',
			    type:'post', 
			    dataType:'json',  
			    data:{
			    	sceneId:parseInt(reportItem.sceneId),
			    	startTime:reportItem.startTime,
			    	endTime:reportItem.endTime
				},   
			    success:function(result,textStatus){
			    	if(result.success){
			    		var map = eval(result.data);
			    		var length  = Object.keys(map).length ;
			    		var count = 0;
			    		var ip1,ip2,ip3;
			    		var cpu_data1 = [],mem_data1 = [],iops_data1 = [];
			    		var cpu_data2 = [],mem_data2 = [],iops_data2 = [];
			    		var cpu_data3 = [],mem_data3 = [],iops_data3 = [];
			    		for(var key in map){
			    		     if(count == length){
			    		    	 break;
			    		     }
			    		     var dataObj = map[key];
			    		     for(var i=0;i<dataObj.length;i++){
			    		    	var item = dataObj[i];
			    		    	
			    		    	var cpu = new Array();
				    			var mem = new Array();
				    			var iops = new Array();
				    			cpu[0] = titanFormdateMin(item.createTime);
				    			cpu[1] = item.cpuUsage;
				    			mem[0] = titanFormdateMin(item.createTime);
				    			mem[1] = item.memoryUsage;
				    			iops[0] = titanFormdateMin(item.createTime);
				    			iops[1] = item.iops;
			    		    	 
			    		    	if(0 == count){
				    		    	 ip1 = key;
				    		    	 cpu_data1.unshift(cpu);
						    		 mem_data1.unshift(mem);
						    		 iops_data1.unshift(iops);
				    		     }
				    		     if(1 == count){
				    		    	 ip2 = key;
				    		    	 cpu_data2.unshift(cpu);
						    		 mem_data2.unshift(mem);
						    		 iops_data2.unshift(iops);
				    		     }
	  							 if(2 == count){
	  								 ip3 = key;
	  								 cpu_data3.unshift(cpu);
						    		 mem_data3.unshift(mem);
						    		 iops_data3.unshift(iops);
				    		     }
			    		     }
  							 count++;
			    		}
			    		agentChart(cpu_data1,cpu_data2,cpu_data3,ip1,ip2,ip3,window.document.getElementById("cpuChart"),0);
				    	agentChart(mem_data1,mem_data2,mem_data3,ip1,ip2,ip3,window.document.getElementById("memoryChart"),1);
				    	agentChart(iops_data1,iops_data2,iops_data3,ip1,ip2,ip3,window.document.getElementById("iopsChart"),2);
			    	}else{
			    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
			    	}
			    },
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	console.log('错误:' + XMLHttpRequest.status + "," + errorThrown);
			    }
			});
		}
		function agentChart(data1,data2,data3,ip1,ip2,ip3,selectorId,type){
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
			var valueList3 = data3.map(function (item) {
		  	 	 return item[1];
			});
			var yAxisMaxSize = null;
			option = {
			    tooltip: {
			        trigger: 'axis'
			    },
			    legend: {
			        data:[ip1,ip2,ip3],
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
			        lineStyle:{
			        	normal:{
			        		color:colorArr[0]
			        	}
			        },
			        itemStyle:{
			        	normal:{
			        		color:colorArr[0]
			        	}
			    	}
			    },
			    {
			    	name:ip2,
			        type: 'line',
			        showSymbol: false,
			        smooth:true,
			        data: valueList2,
			        lineStyle:{
			        	normal:{
			        		color:colorArr[1]
			        	}
			        },
			        itemStyle:{
			        	normal:{
			        		color:colorArr[1]
			        	}
			    	}
			    },
			    {
			    	name:ip3,
			        type: 'line',
			        showSymbol: false,
			        smooth:true,
			        data: valueList3,
			        lineStyle:{
			        	normal:{
			        		color:colorArr[2]
			        	}
			        },
			        itemStyle:{
			        	normal:{
			        		color:colorArr[2]
			        	}
			    	}
			    }]
			};
			myChartcpu.setOption(option);
		}
	}
}
</script>