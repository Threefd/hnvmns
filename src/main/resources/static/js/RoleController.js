function RoleController(){
    var service=new Object();
    service.url=host+"/auth"+"/role";
    /**
      *添加系统角色  
      *@param role role 待添加的角色信息 
      */
     service.addRole=function(role,onSuccess){
	     var roleStr=JSON.stringify(role);
		 var requestUrl=this.url+"/add";
         $.ajax({
             url:requestUrl,
             type:'Post',
             data:{
                   role:roleStr,
             },
             cache:false,
             success:onSuccess,
             error:common.onError
             });
    };
           
    /**
      *删除角色  
      *@param roleId roleId 待删除角色的Id 
      */
     service.deleteRole=function(roleId,onSuccess){
	     var roleIdStr=roleId;
		 var requestUrl=this.url+"/delete";
         $.ajax({
             url:requestUrl,
             type:'Delete',
             data:{
                   roleId:roleIdStr,
             },
             cache:false,
             success:onSuccess,
             error:common.onError
             });
    };
           
    /**
      *加载角色列表  
      */
     service.getRoleList=function(onSuccess){
		 var requestUrl=this.url+"/list";
         $.ajax({
             url:requestUrl,
             type:'Get',
             data:{
             },
             cache:false,
             success:onSuccess,
             error:common.onError
             });
    };
           
    return service;
}
