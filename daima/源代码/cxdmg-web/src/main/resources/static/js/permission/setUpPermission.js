//显示角色对应的权限信息
function toPermissionList(id){
	var url=ctx+"/permission/getPermissionListJson";
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
					sb+='	<td>'+data.list[i].perm_tag+'</td>';
					sb+='	<td>'+data.list[i].perm_name+'</td>';
					sb+='</tr>';
				}
			}
			$("#per_tbody").html(sb);
		}
});
}

//跳到设置角色权限界面
function toSettingRolePermissions(id){
	 window.location.href=ctx+"/permission/toSettingRolePermissions?roleId="+id;
}
//保存权限
function savePerm(){
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
	 var url=ctx+"/permission/savePerm";
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:{				
				perId:id,
				roleId:roleId
			},
			success:function(data){
				if(data.code==-1){
					alert("设置失败");
					return;
				}
				alert("设置成功");
				window.location.href=ctx+"/permission/setUpPermission";
			}
	});
}