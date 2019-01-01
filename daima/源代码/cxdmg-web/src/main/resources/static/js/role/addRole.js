//增加角色
function addRole(){
	var url=ctx+"/role/addRole";
	var roleName=$("#roleName").val();
	if(roleName==""){
		alert("角色英文不能为空");
		return;
	}
	var roleDesc=$("#roleDesc").val();
	if(roleDesc==""){
		alert("角色中文不能为空");
		return;
	}
	
	$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{				
				roleDesc:roleDesc,
				roleName:roleName
			},
			success:function(data){
				if(data.code=="-1"){
					alert(data.msg);
					return;
				}
				//跳转到角色列表
				 window.location.href=ctx+"/role/getRoleList";
			}
	});
}