var height = document.documentElement.clientHeight;
var width = document.documentElement.clientWidth;
window.onload=function(){
	
	//========全选框=============
	// 全选需要添加 id="checkbox-all" 。例如：<input type="checkbox" id="checkbox-all"/>
	//其下节点需要添加class="checkbox-one" 。 例如：<input type="checkbox" id="1" class="checkbox-one">
	//这样点击全选的时候就能够控制下面节点的勾选了。
	//===========================
	jQuery("#checkbox-all").click(function () {
		//点击全选，控制其下所有状态
		if(this.checked){
			jQuery(".checkbox-one").each(function(){
				this.checked = true;
			});
		}else{
			jQuery(".checkbox-one").each(function(){
				this.checked = false;
			});
		}
		
		//获取所有选中的id，拼接
		var checkedbox = "";
		jQuery(".checkbox-one:checked").each(function(){
			checkedbox += this.id + ",";
		});
		
		//将拼接的id填充到全部删除中
		jQuery(".confirm-all-del").each(function(){
			if(this.href.indexOf(',')>0){ //判断是否,结尾，表示添加过id了，需要将上次添加的删除
				this.href = this.href.substring(0,this.href.lastIndexOf("/")+1);
			}
			this.href += checkedbox;
		});
	});
	
	jQuery(".checkbox-one").click(function () {
		
		var checkedbox = "";
		jQuery(".checkbox-one:checked").each(function(){
			checkedbox += this.id + ",";
		});
		
		jQuery(".confirm-all-del").each(function(){
			if(this.href.indexOf(',')>0){
				this.href = this.href.substring(0,this.href.lastIndexOf("/")+1);
			}
			this.href += checkedbox;
		});
	});//========全选框 完==========
	
	//===========删除确认==========================
	//需要添加class="confirm-del"属性和要执行的href。
	//例如：
	//<a class="confirm-del" href="/admin/user/delete/$user.userId">删除</a>
	//这样就能弹出确认删除框，点击确定后执行href链接，点击取消不执行
	//
	//=============================================
	jQuery(".confirm-del").click(function(){
		jQuery('#confirmModal').remove();		
		if(this.href.charAt(this.href.length-1) == ","){
			this.href = this.href.substring(0,this.href.length-1);
		}
		if(this.href.indexOf("sid") == -1){
			this.href += "?sid="+sid;
		}
		
		var confirmhtml = '<div class="modal hide fade" id="confirmModal">'
			  +'<div class="modal-header">'
			  +'<a class="close" data-dismiss="modal">×</a>'
			  +'<h3>操作提醒</h3>'
			  +'</div>'
			  +'<div class="modal-body"><p>'
			  +'确定删除？'
			  +'</p></div>'
			  +'<div class="modal-footer">'
			  +'<a href="javascript:void(0)" class="btn" data-dismiss="modal" aria-hidden="true">取消</a>'
			  +'<a href="'+this.href+'" class="btn btn-primary">确定</a>'
			  +'</div></div>';
		jQuery(document.body).append(confirmhtml);
		jQuery('#confirmModal').modal('show');
		return false;
	});
	
	//==============================================
	//扩展全选和确认删除的功能是多选删除功能：
	//需要添加 class="confirm-del confirm-all-del" 和 href,弹出的确认框中会将多选的id以,分隔添加至href后面
	//<a class="confirm-del confirm-all-del" type="button" href="/admin/user/delete"><i class="icon-user icon-white"></i>&nbsp;删除用户</a>
	//全选需要添加 id="checkbox-all" 。例如：<input type="checkbox" id="checkbox-all"/>
	//其下节点需要添加class="checkbox-one" 。 例如：<input type="checkbox" id="1" class="checkbox-one">
	//这样点击全选的时候就能够控制下面节点并且将值传给confirm-all-del了。
	//==============================================
};

//======================菜单控制========================
function setActive(mid,nid){
	var main =document.getElementById('mainnav');
	var sub =document.getElementById('sub-navigation');

	main.innerHTML = '<ul>'
		+'<li><a href="/admin?sid='+sid+'">管理首页</a> </li>'
		+'<li><a href="/admin/article?sid='+sid+'">文章管理</a> </li>'
		+'<li><a href="/admin/user?sid='+sid+'">系统管理</a> </li>'
		+'</ul>';

	mainlis = main.getElementsByTagName("li");

	switch(mid){
	case 0:
		sub.innerHTML = '<ul>'
			
			+'</ul> ';
		mainlis[mid].className = "active";
		break;
	case 1:
		sub.innerHTML = '<ul>'
			+'<li><a href="/admin/article?sid='+sid+'">文章列表</a></li>'
			+'<li><a href="/admin/article/add?sid='+sid+'">撰写文章</a></li>'
			+'<li><a href="/admin/article/category?sid='+sid+'">目录管理</a></li>'
			+'<li><a href="/admin/article/type?sid='+sid+'">分类管理</a></li>'
			+'</ul> ';
		mainlis[mid].className = "active";
		break;
	case 2:
		sub.innerHTML = '<ul>'
			+'<li><a href="/admin/user?sid='+sid+'">用户管理</a></li>'
			+'<li><a href="/admin/group?sid='+sid+'">分组管理</a></li>'
			+'<li><a href="/admin/role?sid='+sid+'">角色管理</a></li>'
			+'</ul> ';
		mainlis[mid].className = "active";
		break;
	default:
		break;
	}

	sublis = sub.getElementsByTagName("li");
	for(var i=0;i<sublis.length;i++){
		if(i==nid)
			sublis[i].className = "active";	
	}
}//======================菜单控制 完========================


//========提示框 组件================
function alertTips(content,time){
	jQuery("html, body").animate({ scrollTop: 0 }, 120);
	var html = '<div class="alert alert-block fade in"><a class="close" data-dismiss="alert">×</a><center><strong>'+content+'</strong></center></div>';
	jQuery("#tips").empty();
	jQuery("#tips").append(html);
	setTimeout("jQuery('.alert').alert('close')",time);
}

function alertSuccessTips(content,time){
	jQuery("html, body").animate({ scrollTop: 0 }, 120);
	var html = '<div class="alert alert-success fade in"><a class="close" data-dismiss="alert">×</a><center><strong>'+content+'</strong></center></div>';
	jQuery("#tips").empty();
	jQuery("#tips").append(html);
	setTimeout("jQuery('.alert').alert('close')",time);
}

function alertFailTips(content,time){
	jQuery("html, body").animate({ scrollTop: 0 }, 120);
	var html = '<div class="alert alert-error fade in"><a class="close" data-dismiss="alert">×</a><center><strong>'+content+'</strong></center></div>';
	jQuery("#tips").empty();
	jQuery("#tips").append(html);
	setTimeout("jQuery('.alert').alert('close')",time);
}

//=========操作确认 组件==================
function bootConfirm(object,msg){
	var confirmhtml = '<div class="modal hide fade" id="confirmModal">'
		  +'<div class="modal-header">'
		  +'<a class="close" data-dismiss="modal">×</a>'
		  +'<h3>操作提醒</h3>'
		  +'</div>'
		  +'<div class="modal-body"><p>'
		  +msg
		  +'</p></div>'
		  +'<div class="modal-footer">'
		  +'<a href="javascript:void(0)" class="btn" data-dismiss="modal" aria-hidden="true">取消</a>'
		  +'<a href="'+object+'" class="btn btn-primary">确定</a>'
		  +'</div></div>';
	jQuery(document.body).append(confirmhtml);
	jQuery('#confirmModal').modal('show');
	return false;
}

