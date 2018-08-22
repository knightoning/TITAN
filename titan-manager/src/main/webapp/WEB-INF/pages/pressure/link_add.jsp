<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="layui-container" style="width: 100%;" id="linkAddPage">
	<div class="layui-row titan-content-div">
		<div class="layui-col-md7 layui-elem-quote">
			链路管理 &gt;&gt;<label class="titan-head-label">新增链路</label>
		</div>
	</div>
	<div class="layui-row">
		<div class="layui-col-md7">
			<form class="layui-form" method="post">
				  <div class="layui-form-item">
				    <label class="layui-form-label"><font style="color: red;">*</font>&nbsp;链路名：</label>
				    <div class="layui-input-block">
				      <input type="text" name="link_name" lay-verify="required" placeholder="请输入链路名（10字符内）" autocomplete="off" class="layui-input" maxlength="10">
				    </div>
				  </div>
				  <div class="layui-form-item">
				    <label class="layui-form-label">协议类型：</label>
				    <div class="layui-input-block">
				    	<select name="protocol_type">
					    	<option value="0">HTTP</option> 
					    	<option value="1">HTTPS</option>
				    	</select>
				    </div>
				  </div>
				  <div class="layui-form-item">
				    <label class="layui-form-label"><font style="color: red;">*</font>&nbsp;压测URL：</label>
				      <div class="layui-input-block">
				        <input type="text" name="stresstest_url" lay-verify="required|stresstest_url" placeholder="请输入URL" autocomplete="off" class="layui-input" >
				      </div>
				  </div>
				  <div class="layui-form-item">
				    <label class="layui-form-label">请求类型：</label>
				    <div class="layui-input-block">
				    	<select name="request_type">
				    		<option value="0">GET</option> 
				    		<option value="1">POST</option>
				    	</select>
				    </div>
				  </div>
				  <div class="layui-form-item">
				    <label class="layui-form-label">内容类型：</label>
				    <div class="layui-input-block">
				    	<select name="content_type">
				    		<option value="0">application/json</option> 
				    		<option value="1">application/xml</option>
				    		<option value="2">text/xml</option>
				    		<option value="3">text/html</option>
				    		<option value="4">application/x-www-form-urlencoded</option>
				    	</select>
				    </div>
				  </div>
				  <div class="layui-form-item">
				    <label class="layui-form-label">字符编码：</label>
				    <div class="layui-input-block">
				    	<select name="charset_type">
				    		<option value="0">UTF-8</option> 
				    		<option value="1">ISO-8859-1</option> 
				    		<option value="2">US-ASCII</option>
				    		<option value="3">UTF-16</option>
				    		<option value="4">UTF-16LE</option> 
				    		<option value="5">UTF-16BE</option>
				    	</select>
				    </div>
				  </div>
				  <div class="layui-form-item">
				    <label class="layui-form-label">压测参数：</label>
				    <div class="layui-input-block titan-file-upload">
				    	<button type="button" class="layui-btn layui-btn-primary" id="upload_testdata_file">
						  <i class="layui-icon">&#xe67c;</i>选择文件（xls）
						</button>
						<i class="layui-icon titan-file-upload-desc">&#xe60b;</i><span class="titan-span-filename"><a href="javascript:;" title="点击可下载"></a></span>
						<input type="text" name="test_filename" autocomplete="off" class="layui-input layui-hide"/>
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
	linkAddPage();
});
function linkAddPage(){
	/* 表单渲染 */
	form.render();
	/* 参数校验规则 */
	form.verify({ 
		  stresstest_url: function(value){
		   	  var protocol = $("#linkAddPage select[name='protocol_type']").val();
	          if(/^(https:\/\/).*$/.test(value)){  
	          	if("0" == protocol){
					return '请输入正确的http协议网址'; 
	          	}
	          }else if(/^(http:\/\/).*$/.test(value)){
	          	if(protocol=="1"){
					return '请输入正确的https协议网址'; 
	          	}
	          }else{
	          	return '请输入正确的网址'; 
	          }
	      }
	});
	var linkId = $mainContent.data('linkid');
	if(linkId){
		$("#linkAddPage label.titan-head-label").text("修改链路");
		getDataDetail(linkId);
	}
	uploadFile();
	bindSubmitBtn();
	showFileExplain();
	bindDownloadFile();
	
	function getDataDetail(id){
		$.ajax({
		    url:'${pageContext.request.contextPath}/link/get',
		    type:'post', 
		    async:false,    
		    dataType:'json',  
		    data:{
		    	linkId:parseInt(id)
		    },
		    success:function(result,textStatus){
		    	if(result.success){
		    		var params = result.data;
		    		$("#linkAddPage input[name='link_name']").val(params.linkName);
		    		$("#linkAddPage select[name='protocol_type']").val(params.protocolType);
		    		$("#linkAddPage input[name='stresstest_url']").val(params.stresstestUrl);
		    		$("#linkAddPage select[name='request_type']").val(params.requestType);
		    		$("#linkAddPage select[name='content_type']").val(params.contentType);
		    		$("#linkAddPage select[name='charset_type']").val(params.charsetType);
		    		$("#linkAddPage input[name='test_filename']").val(params.testfilePath);
		    		
		    		$("#linkAddPage input[name='link_name']").attr("readonly",true);
		    		if(null != params.testfilePath){
		    			$("#linkAddPage div.titan-file-upload").find("span").find("a").text(params.testfilePath);
		    		}
		    		
		    		form.render(); //重新渲染
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
			   var url = '${pageContext.request.contextPath}/link/add';
			   var params = {
				        linkName:formParam.link_name,
				        protocolType:parseInt(formParam.protocol_type),
				        stresstestUrl:formParam.stresstest_url,
				        requestType:parseInt(formParam.request_type),
				        contentType:parseInt(formParam.content_type),
				        charsetType:parseInt(formParam.charset_type),
				        testfilePath:formParam.test_filename
				    };
		 	   if(linkId){
		 			url = '${pageContext.request.contextPath}/link/update';
		 			params.linkId = parseInt(linkId);
		 	   } 
			   $.ajax({
				    url:url,
				    type:'post', 
				    async:false, 
				    dataType:'json',  
				    data:params,   
				    success:function(result,textStatus){
				    	if(result.success){
				    		$('#linkPageBtn').trigger("click");
				    	}else{
				    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
				    	} 
				    },
				    error:function(xhr,textStatus){
				        console.log("错误:"+ xhr);
				    }
				});
			  return false;//阻止表单跳转
		});
		//取消
		$("#linkAddPage a.titan-btn-cancel").on("click",function(){
			 $mainContent.empty().load('${pageContext.request.contextPath}/pages/pressure/link_list');
		});
	}
	/* 上传压测文件 */
	function uploadFile(){
		var uploadInst = upload.render({
		    elem: '#upload_testdata_file', //绑定元素
		    url: '${pageContext.request.contextPath}/file/fileUpload', //上传接口
		    accept: 'file', 
		    exts:"xls",		
		    data: {"ticket":getCookie("ticket")},
		    size:102400,
		    done: function(result){
		    	if(result.success){
		    		$("#linkAddPage div.titan-file-upload").find("span").find("a").text(result.data.filename);
		    		$("#linkAddPage input[name='test_filename']").val(result.data.filename);
		    	}else{
		    		layer.alert(result.errorCode + ":"+ result.errorMsg, {icon: 5});
		    	}
		    },
		    error: function(xhr,textStatus){
		    	 console.log('错误:' + xhr);
		    }
		  });
	}
	function showFileExplain(){
		$("#linkAddPage i.titan-file-upload-desc").on("click",function(){
			var content = "<div style='padding: 15px;'>"
				+"	<div><i class='layui-icon' style='font-size: 24px;'>&#xe705;</i> 假设URL为：</div>	"
				+"		<div style='padding-left: 25px;'><pre class='layui-code'>http://localhost:8080/yunjibuyer/subject/getFullCoudivon.json</pre></div>"	
				+"		<div>需要支持动态参数时（GET和POST请求同理），动参文件格式为：</div>"	
				+"		<div style='padding-left: 25px;'>"
				+"			<pre class='layui-code'>"
				+"<span>?fullcoudivonId=115&shodivId=123&adivdivCont=0&isNewVersion=1</span><br/>"
				+"<span>?fullcoudivonId=116&shodivId=456&adivdivCont=0&isNewVersion=2</span><br/>"
				+"<span>?fullcoudivonId=117&shodivId=789&adivdivCont=0&isNewVersion=3</span>"
				+"			</pre>"
				+"		</div>	"
				+"	<div>如果需要支持Cookie或者其它头信息，动参文件格式为：</div>	"
				+"	<div style='padding-left: 25px;'><pre class='layui-code'>{'header':{'Cookie':'ticket_123'}}</pre></div>"
				+"	<div>或通过符号%%%分割：</div>	"
				+"	<div style='padding-left: 25px;'><pre class='layui-code'>?fullcoudivonId=115&shodivId=123&adivdivCont=0&isNewVersion=1%%%{'header':{'Cookie':'ticket_123','testHeader':'test_321'}}</pre></div>"	
				+"	</div>";			
				layer.open({
				    type: 1,
				    closeBtn: false,
				    area: '1000px;',
				    shade: 0.5,
				    id: 'LAY_layuipro2',
				    resize: false, 
				    btn: ['关闭'],
				    btnAlign: 'c',
				    moveType: 0,
				    content:content,
				    success: function(layero){
				         var btn = layero.find('.layui-layer-btn');
				    }
				});
		});
	}
	function bindDownloadFile(){
		$("#linkAddPage span.titan-span-filename a").on("click",function(){
			var filename = $("#linkAddPage input[name='test_filename']").val();
			var url = "${pageContext.request.contextPath}/file/fileDownload";
	        var form = $("<form></form>").attr("action", url).attr("method", "post");
	        form.append($("<input></input>").attr("type", "hidden").attr("name", "filename").attr("value", filename));
	        form.appendTo('body').submit().remove(); 
		});
	}
}
</script>