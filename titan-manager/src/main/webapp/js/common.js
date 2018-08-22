/**
 * 通用js
 */
/**
 * 格式化日期
 * @param timestamp 时间戳
 * 
 * @return 格式化后的日期 格式： yyyy-MM-dd HH：mm:ss
 */
//全部变量
var titanFirstPageIndex = 0; //首页索引值
var titanPageSize = 10; //每页大小
var titanTotalCount = 0;	//总记录数初始值
var titanDataIdArr = []; //表格选中的ID集合
var getOperateStatusTimer;	 //刷新压测状态定时器
var titanTimer1; //刷新压测状态定时器
var titanTimer2; //刷新agent性能检测

//初始化方法，用于页面切换时，初始化相关参数
function titanInitParam(){
	titanTotalCount = 0;	//总记录数初始值
	titanDataIdArr = []; //表格选中的ID集合
	window.clearInterval(titanTimer1); //关闭定时任务
	window.clearInterval(titanTimer2); //关闭定时任务
}

//日期时间戳格式化
function titanFormdate(timestamp){
     var tempDate = new Date(timestamp);
     var yyyy = tempDate.getFullYear();
     var MM = correctForm(tempDate.getMonth() + 1);
     var dd = correctForm(tempDate.getDate());
     var HH = correctForm(tempDate.getHours());
     var mm = correctForm(tempDate.getMinutes());
     var ss = correctForm(tempDate.getSeconds());
     function correctForm(i){
     	if(i<=9){
     		i = "0" + i;
     	}
     	return i
     }
     return yyyy + '-' + MM + '-' + dd + ' ' + HH + ':' + mm + ':' + ss; 
};
function titanFormdateMin(timestamp){
	var tempDate = new Date(timestamp);
    var HH = correctForm(tempDate.getHours());
    var mm = correctForm(tempDate.getMinutes());
    var ss = correctForm(tempDate.getSeconds());
    function correctForm(i){
    	if(i<=9){
    		i = "0" + i;
    	}
    	return i
    }
    return HH + ':' + mm + ':' + ss; 
}
//时间字符串格式为Date
function titanFormatTimeStr(timeStr){
	if(null != timeStr){
		return new Date(Date.parse('1970-01-01 ' + timeStr));
	}else{
		return null;
	}
}

//移除数组中的一个
function titanRemoveArrDataByValue(arr, value) {
	for(var i=0; i<arr.length; i++) {
		if(arr[i] == value) {
	      arr.splice(i, 1);
	      break;
	    }
	}
}
//IP格式校验 true:合法 false:非法
function titanValidteIP(ip){     
    var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/     
    return reg.test(ip);     
}   