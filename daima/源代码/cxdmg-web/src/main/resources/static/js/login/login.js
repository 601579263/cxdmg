//登陆
function toLogin(){
	
	var url=ctx+"/toLogin";
	//alert(url);
	$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{
				//empId:'zhangsan',
				//password:'123456'
			},
			success:function(data){
				alert(data.msg);
				window.location.href=ctx+"/user/getUserList";
			}
		});
}