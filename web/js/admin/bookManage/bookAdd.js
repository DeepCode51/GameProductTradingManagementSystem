$(function(){
	var form=$("#bookAddForm").Validform({
		tiptype:2,//validform初始化
	});

	form.addRule([
		{
			ele:"#bookName",
		    datatype:"*2-32",
		    //ajaxurl:"jsp/admin/BookManageServlet?action=find",
		    nullmsg:"请输入饰品名称！",
		    errormsg:"饰品名称至少2个字符,最多32个字符！"
		},
		{
			ele:"#catalog",
			datatype:"*",
			nullmsg:"请选择饰品分类！",
			errormsg:"请选择饰品分类！"
		},
		{
			ele:"#price",
			datatype:"/^[0-9]{1,}([.][0-9]{1,2})?$/",
			mullmsg:"价格不能为空！",
			errormsg:"价格只能为数字"
		},
		{
			ele:"#author",
		    datatype:"*2-30",
		    nullmsg:"请输入品质名称！",
		    errormsg:"品质名称至少2个字符,最多30个字符！"
		},
		{
			ele:"#press",
		    datatype:"*2-30",
		    nullmsg:"请输入类别！",
		    errormsg:"类别至少2个字符,最多30个字符！"
		},
		{
			ele:"#bookImg",
		    datatype:"*",
		    nullmsg:"请上传饰品图片！",
		    errormsg:"请上传饰品图片！"
		}

	]);

});



////ajax实时判断用户存在否
//var oldErr;
//var xmlHttp;
//var flag=true;//标记
//function ck_username(){
//		xmlHttp=getXmlHttp();
//
//		//用户名验证4-8位大小写字母和数字
//
//		if($("#userName").val()==null){
//			return;
//		}
//
//		var url="UserServlet?action=find&userName="+$("#userName").val();
//		xmlHttp.open("GET",url,true);
//		xmlHttp.onreadystatechange=function(){
//			if(xmlHttp.readyState==4 && xmlHttp.status==200){
//				var json=JSON.parse(xmlHttp.responseText);
//				var info=json['info'];
//				if(info==0){
//					oldErr=$(".ckerr").eq(0).html("*用户名已存在！请重新输入！").css("color","red");
//					flag=true;
//				}else{
//					if(oldErr!=null){
//						oldErr.html("√ 正确").css("color","green");
//						flag=false;
//					}
//				}
//			}
//		}
//		xmlHttp.send(null);
//	}
//
//
//
//function getXmlHttp(){
//	var xHttp;
//	if(window.XMLHttpRequest){
//		xHttp=new XMLHttpRequest();
//	}
//	if(window.ActiveXObject){
//		xHttp=new ActiveXObject("Microsoft.XMLHttp");
//	}
//	return xHttp;
//}
//
//
