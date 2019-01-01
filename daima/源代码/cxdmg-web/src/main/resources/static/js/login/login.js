$(function(){
	
	 //调用QC.Login方法，指定btnId参数将按钮绑定在容器节点中
	   QC.Login({
	       //btnId：插入按钮的节点id，必选
	       btnId:"qqLoginBtn",	
	       //用户需要确认的scope授权项，可选，默认all
	       scope:"all",
	       //按钮尺寸，可用值[A_XL| A_L| A_M| A_S|  B_M| B_S| C_S]，可选，默认B_S
	       size: "A_XL"
	   }, function(reqData, opts){//登录成功
	       //根据返回数据，更换按钮显示状态方法
	       var dom = document.getElementById(opts['btnId']),
	       _logoutTemplate=[
	            //头像
	            '<span><img src="{figureurl}" class="{size_key}"/></span>',
	            //昵称
	            '<span>{nickname}</span>',
	            //退出
	            '<span><a href="javascript:QC.Login.signOut();">退出</a></span>'	
	                     ].join("");
	       dom && (dom.innerHTML = QC.String.format(_logoutTemplate, {
	           nickname : QC.String.escHTML(reqData.nickname),
	           figureurl : reqData.figureurl
	              }));
	   },function(opts){//注销成功
		  alert('QQ登录 注销成功');
		window.location.reload();
	  }
	);
	   
   var paras = {};
	QC.api("get_user_info", paras)
		.success(function(s){//成功回调
			//隐藏qq登陆按钮
			$("#qqLoginBtn").hide();
				QC.Login.getMe(function(openId, accessToken){
					console.log("当前登录用户的", "openId为："+openId, "accessToken为："+accessToken);
					//绑定qq
					var url=ctx+"/qqLoginCallback";
					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						data:{				
							openId:openId,
							name:s.data.nickname,
							figureurl:s.data.figureurl_1
						},
						success:function(data){
							if(data.code=="-1"){
								alert(data.msg);
								return;
							}
							//跳到其他页
							window.location.href=ctx+"/index?name="+s.data.nickname+"&figureurl="+s.data.figureurl_1;
						}
					});
				})
		})
		.error(function(f){//失败回调
			console.log("获取用户信息失败！");
		})
		.complete(function(c){//完成请求回调
			console.log("获取用户信息完成！");
		});   
	
});


/*//登陆
function toLogin(){
	var pwd=$("#pwd").val();
	var empId=$("#empId").val();
	if(empId==""){
		alert("账号不能为空");
		return;
	}
	if(pwd==""){
		alert("密码不能为空");
		return;
	}
	var url=ctx+"/toLogin";
	$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{
				empId:empId,
				pwd:pwd
			},
			success:function(data){
				if(data.code=="-1"){
					alert(data.msg);
					return;
				}
				//跳到首页
				window.location.href=ctx+"/index?name="+empId;
			}
		});
}*/

//注册
function register(){
	var pwd=$("#pwd1").val();
	var empId=$("#empId1").val();
	var email=$("#email").val();
	var name=$("#name").val();
	var phone=$("#phone").val();
	if(empId==""){
		alert("账号不能为空");
		return;
	}
	if(pwd==""){
		alert("密码不能为空");
		return;
	}
	if(email==""){
		alert("邮箱不能为空");
		return;
	}
	if(name==""){
		alert("姓名不能为空");
		return;
	}
	if(phone==""){
		alert("手机号不能为空");
		return;
	}
	var flag=isPoneAvailable(phone);
	if(!flag){
		alert("手机号格式不正确");
		return;
	}
	var yzm=$("#yzm").val();
	var verificationCode=$("#verificationCode").val();
	if(verificationCode==""){
		alert("验证码不能为空");
		return;
	}
	if(verificationCode!=yzm){
		alert("验证码不正确");
		return;
	}
	
	//对电子邮件的验证   
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if (!myreg.test(email)) {
		alert("邮箱格式不正确");
		return ;
	}
	var url=ctx+"/register";
	$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{
				username:empId,
				password:pwd,
				phone:phone,
				name:name,
				email:email
			},
			success:function(data){
				if(data.code=="-1"){
					alert(data.msg);
					return;
				}
				//跳到首页
				window.location.href=ctx+"/index?name="+name;
			}
		});
}
//验证手机号
function isPoneAvailable(poneInput) {  
    var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
    if (!myreg.test(poneInput)) {  
        return false;  
    } else {  
        return true;  
    }  
}

//忘记密码
function forgetPwd(){
	//跳到忘记密码界面
	window.location.href=ctx+"/getForgetPwd";
}

//忘记密码发送验证码
function sendOut(){
	//开始发送验证码
	var url=ctx+"/sendOut";
	var phone=$("#phone").val();
	if(phone==""){
		alert("手机号不能为空");
		return;
	}
	var flag=isPoneAvailable(phone);
	if(!flag){
		alert("手机号格式不正确");
		return;
	}
	$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{				
				phone:phone,
				templateId:'255433'
			},
			success:function(data){
				if(data.code=="-1"){
					alert(data.msg);
					return;
				}
				$("#yzm").val(data.yzm);
			}
	});
	
	var num=30;
	document.getElementById("sendOut_btn").disabled=true;
	$("#sendOut_btn").html(num+"秒后,重新发送");
	timer=setInterval(function(){
		num--;
		$("#sendOut_btn").html(num+"秒后,重新发送");
		if(num==0){
			document.getElementById("sendOut_btn").disabled=false;
			clearInterval(timer);
			$("#sendOut_btn").html("发送验证码");
		}
	},1000);
}

//修改新密码
function updateNewPwd(){
	var yzm=$("#yzm").val();
	var phone=$("#phone").val();
	
	if(phone==""){
		alert("手机号不能为空");
		return;
	}
	var flag=isPoneAvailable(phone);
	if(!flag){
		alert("手机号格式不正确");
		return;
	}
	var verificationCode=$("#verificationCode").val();
	if(verificationCode==""){
		alert("验证码不能为空");
		return;
	}
	if(verificationCode!=yzm){
		alert("验证码不正确");
		return;
	}
	
	var pwd=$("#pwd").val();
	if(pwd==""){
		alert("密码不能为空");
		return;
	}
	var url=ctx+"/updateNewPwd";
	$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{				
				pwd:pwd,
				phone:phone
			},
			success:function(data){
				if(data.code=="-1"){
					alert(data.msg);
					return;
				}
				//跳到首页
				window.location.href=ctx+"/index";
			}
		});
}


//注册发送验证码
function registerSendOut(){
	//开始发送验证码
	var url=ctx+"/sendOut";
	var phone=$("#phone").val();
	if(phone==""){
		alert("手机号不能为空");
		return;
	}
	var flag=isPoneAvailable(phone);
	if(!flag){
		alert("手机号格式不正确");
		return;
	}
	$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{				
				phone:phone,
				templateId:'255535'
			},
			success:function(data){
				if(data.code=="-1"){
					alert(data.msg);
					return;
				}
				$("#yzm").val(data.yzm);
			}
	});
	
	var num=30;
	document.getElementById("sendOut_btn").disabled=true;
	$("#sendOut_btn").html(num+"秒后,重新发送");
	timer=setInterval(function(){
		num--;
		$("#sendOut_btn").html(num+"秒后,重新发送");
		if(num==0){
			document.getElementById("sendOut_btn").disabled=false;
			clearInterval(timer);
			$("#sendOut_btn").html("发送验证码");
		}
	},1000);
}

