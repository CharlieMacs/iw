/**
 * 获取网址的get参数。
 * @param name get参数名
 * @returns value
 */
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

/**
 * 列表页右上方的排序方式按钮
 * @param content 传入值如：orderBy('id_DESC=编号倒序,lasttime_DESC=最后登陆时间倒序');
 */
function orderBy(content){
	var ds = '排序方式';		//相当于final，默认显示的文字
	var lh = location.href;
	var defaultShow = ds;
	var mainUrl = '';		//网址主url，后不带?name=value这些get参数
	var queryStringFilter = '';		//过滤掉orderBy参数后，url的附带参数集合，结果如： username=a&name=b
	if(lh.indexOf("orderBy")>-1){
		defaultShow = GetQueryString('orderBy');
		
		var num=lh.indexOf("?") 
		queryString=lh.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
		var mainUrl = lh.split('?')[0];
		var arr=queryString.split("&"); //各个参数放到数组里
	    for(var i=0;i < arr.length;i++){ 
		    num=arr[i].indexOf("="); 
		    if(num>0){
			    name=arr[i].substring(0,num);
			    value=arr[i].substr(num+1);
			    if(value.length>0 && name!='orderBy'){
			    	if(queryStringFilter.length==0){
			    		queryStringFilter = name+"="+value;
			    	}else{
			    		queryStringFilter = "&"+name+"="+value;
			    	}
			    }
		    } 
	    } 
	}
	
	//组合lh网址
	if(queryStringFilter.length>0){
		lh = mainUrl+"?"+queryStringFilter+"&"
	}else{
		lh = mainUrl+"?"
	}
	
	var c = ''
	var ob = content.split(',');
	for(i=0;i<ob.length;i++){
		var o = ob[i].split('=');
		var key = o[0];
		var value = o[1];
		c = c + '<li><a href="'+lh+'orderBy='+key+'">'+value+'</a></li>';
		if(defaultShow==key){
			defaultShow = value;
		}
	}
	c = '<ul role="menu" class="dropdown-menu" style="min-width:120px;">'+c+'</ul>';
	c = '<div class="btn-group"><button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" type="submit">'+defaultShow+'<span class="caret"></span></button> '+c;
	document.write(c);
}