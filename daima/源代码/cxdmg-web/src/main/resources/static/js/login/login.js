//登陆
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
				window.location.href=ctx+"/user/getUserList";
			}
		});
}

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
				empId:empId,
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
				window.location.href=ctx+"/user/getUserList";
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
	var num=10;
	document.getElementById("sendOut_btn").disabled=true;
	$("#sendOut_btn").html(num);
	timer=setInterval(function(){
		num--;
		$("#sendOut_btn").html(num);
		if(num==0){
			document.getElementById("sendOut_btn").disabled=false;
			clearInterval(timer);
			$("#sendOut_btn").html("发送验证码");
		}
	},1000);
	
}

