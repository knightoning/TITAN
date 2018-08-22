/**
 * overview页
 */

/**
 * 当前agent状态
 * 
 * @param availableNum 可用节点数
 * @param totalNum 总节点数
 * @param availableIPArr 可用节点IP
 * @param usedIPArr 已用节点IP
 * @param selector 图表元素ID
 */
function overviewAgentChart(availableNum, totalNum, availableIPArr, usedIPArr,selectorID) {
	var totalNodes = totalNum;
	var availableNodes = availableNum;
	var usedNodes = totalNum - availableNum;
	var availableNodesArr = availableIPArr;
	var usedNodesArr = usedIPArr;

	var rows = 10; // 节点行数,列数根据该值计算
	var bottomHeight = 45; // 底部高度
	var nodeText = [ '已用', '可用', '总数' ];
	var nodeValue = [ 1, 2 ]; // 2个值分别表示已用、可用的类型值
	// 颜色值,依次表示已用节点、可用节点、总节点、占位节点、分割节点线、节点边框
	var nodeTableColor = [ '#DEDE6B', '#7BC96F', '#FFFFFF', '#EBEDF0','#FFFFFF', '#FFFFFF' ];

	// 初始化echarts实例
	var myChart = echarts.init(document.getElementById(selectorID));
	var myChartWidth = myChart.getWidth()
	var myChartHeight = myChart.getHeight();
	var cells = Math.ceil(myChartWidth / ((myChartHeight - bottomHeight) / rows)); // 列数
	// 计算默认行数是否足够,不够则增加
	while ((rows * cells) < totalNodes) {
		rows = rows + 1;
		cells = Math.ceil(myChartWidth / ((myChartHeight - bottomHeight) / rows)); // 列数
	}
	// 获取值为空的指定长度数组
	var getEmptyArr = function(n) {
		var arr = new Array(n);
		for (var i = 0; i < n; i++) {
			arr[i] = '';
		}
		return arr;
	};
	// 组装节点数据,参数依次是:节点值、节点数、数据数组
	var packageNodeData = function(value, nodeNum, dataArr) {
		var data = [], x, y;
		for (var i = 0; i < nodeNum; i++) {
			if (value == nodeValue[0]) {
				x = parseInt((availableNodes + i) / rows);
				/*y = (availableNodes + i) % rows;*/
				y = rows - 1 - (availableNodes + i) % rows;
			} else {
				x = parseInt(i / rows);
				/*y = i % rows;*/
				y = rows - 1 - i % rows;
			}
			data.push({
				label : dataArr[i],
				value : [ x, y, value ]
			});
		}
		return data;
	};

	splitLine = {
		show : true,
		lineStyle : {
			color : nodeTableColor[4],
			width : 2
		}
	}
	itemStyle = {
		normal : {
			borderColor : nodeTableColor[5],
			borderWidth : 2,
		},
		emphasis : {
			shadowBlur : 1,
			shadowColor : 'rgba(0, 0, 0, 0.5)',
		}
	};
	option = {
		tooltip : {
			formatter : function(obj) {
				return '<div>' + obj.data.label + '</div>';
			},
			backgroundColor:'rgba(50,50,50,0.5)'
		},
		grid : {
			top : 15,
			left : 15,
			right : 15,
			bottom : bottomHeight,
			show : true,
			backgroundColor : nodeTableColor[3]
		},
		xAxis : {
			data : getEmptyArr(cells),
			splitLine : splitLine,
			axisLine : {
				show : false
			},
			axisTick : {
				show : false
			}
		},
		yAxis : {
			data : getEmptyArr(rows),
			splitLine : splitLine,
			axisLine : {
				show : false
			},
			axisTick : {
				show : false
			}
		},
		visualMap : [ {
			pieces : [ {
				min : 0,
				label : nodeText[2] + ': ' + totalNodes
			} ],
			orient : 'horizontal',
			right : 20,
			bottom : 8,
			color : [ nodeTableColor[2] ]
		}, {
			pieces : [ {
				value : nodeValue[0],
				label : nodeText[0] + ': ' + usedNodes
			}, {
				value : nodeValue[1],
				label : nodeText[1] + ': ' + availableNodes
			} ],
			orient : 'horizontal',
			itemGap : 20,
			right : 85,
			bottom : 8,
			color : [ nodeTableColor[1], nodeTableColor[0] ]
		} ],
		series : [
				{
					name : nodeText[0],
					type : 'heatmap',
					data : packageNodeData(nodeValue[0], usedNodes,
							usedNodesArr),
					itemStyle : itemStyle
				},
				{
					name : nodeText[1],
					type : 'heatmap',
					data : packageNodeData(nodeValue[1], availableNodes,
							availableNodesArr),
					itemStyle : itemStyle
				} ]
	};
	myChart.setOption(option);
}