//显示角色对应的用户信息
function toUserList(id){
	var url=ctx+"/role/getUserListJson";
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{				
			roleId:id
		},
		success:function(data){
			var sb="";
			if(data.list.length>0){
				
				for (var i = 0; i < data.list.length; i++) {
					sb+=' <tr>';
					sb+='	<td>'+(i+1)+'</td>	';
					sb+='	<td>'+data.list[i].username+'</td>';
					sb+='	<td>'+data.list[i].name+'</td>';
					sb+='</tr>';
				}
			}
			$("#per_tbody").html(sb);
		}
});
}

//跳到设置角色用户界面
function toSettingRoleUser(id){
	 window.location.href=ctx+"/role/toSettingRoleUser?roleId="+id;
}
//保存角色用户
function saveRoleUser(){
	var id="";
	var roleId=$("#roleId").val();
	 $.each($('input:checkbox:checked'),function(){
        id+=$(this).val()+",";
     });
	 if(id==""){
		 alert("请选择一条数据");
		 return;
	 }
	 id=id.substring(0,id.length-1);
	 var url=ctx+"/role/saveRoleUser";
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{				
				userId:id,
				roleId:roleId
			},
			success:function(data){
				if(data.code==-1){
					alert("设置失败");
					return;
				}
				alert("设置成功");
				window.location.href=ctx+"/role/setUpRole";
			}
	});
}