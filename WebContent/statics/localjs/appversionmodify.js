var pathName=window.document.location.pathname;
//截取，得到项目名称
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
//删除文件,从数据库修改文件为空
function delfile(id){
	$.ajax({
		type:"GET",//请求类型
		url:projectName+"/appver/deleteImage",//请求的url
		data:{id:id},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			if(data==true){
				alert("删除成功！");
				$("#uploadfile").show();
				$("#apkFile").html('');
			}else if(data==false){
				alert("删除失败！");
			}
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("请求错误！");
		}
	});  
}

$(function(){
	//返回按钮
	$("#back").on("click",function(){
		window.location.href = "list";
	});
	
	//上传APK文件---------------------
	var downloadLink = $("#downloadLink").val();
	var id = $("#id").val();
	var apkFileName = $("#apkFileName").val();
	if(downloadLink == null || downloadLink == "" ){
		$("#uploadfile").show();
	}else{
		$("#apkFile").append("<p>"+apkFileName+
							"&nbsp;&nbsp;<a href=\""+downloadLink+"?m="+Math.random()+"\" >下载</a> &nbsp;&nbsp;" +
							"<a href=\"javascript:;\" onclick=\"delfile('"+id+"');\">删除</a></p>");
	}

});
      
      
      