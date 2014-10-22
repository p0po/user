var height = document.documentElement.clientHeight;
var width = document.documentElement.clientWidth;
window.onload=function(){

};

	function setActive(mid,nid){
		var main =document.getElementById('mainnav');
		var sub =document.getElementById('sub-navigation');

		main.innerHTML = '<ul>'
			+'<li><a href="/">首页</a> </li>'
			+'<li><a href="category">著述</a> </li>'
			+'<li><a href="">随笔</a> </li>'
			+'<li><a href="">演讲</a> </li>'
			+'<li><a href="">课程</a> </li>'
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
				
				+'</ul> ';
			mainlis[mid].className = "active";
			break;
		case 2:
			sub.innerHTML = '<ul>'
				+'</ul> ';
			mainlis[mid].className = "active";
			break;
		case 3:
			sub.innerHTML = '<ul>'
				+'<li><a href="">微博</a></li>'
				+'<li><a href="">博客</a></li>'
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
	}
	//======================菜单控制 完========================