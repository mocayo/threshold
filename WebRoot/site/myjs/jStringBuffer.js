

/**
 * 构造函数和原型模式创建对象类型.
 * 长字符串拼接.
 */
function jStringBuffer(str) {
	this.buffer = [];
	if(str != null){
		this.append(str);
	}
}

/**
 * 拼接长字符串.
 * 默认忽略undefined和null.
 * @param str
 * @returns {jStringBuffer}
 */
jStringBuffer.prototype.append = function(str) {
	if(typeof str != "undefined" && str != null){
		this.buffer.push(str.toString());
	}
	return this;
};

/**
 * 换行拼接长字符串.
 * @param str
 * @returns {jStringBuffer}
 */
jStringBuffer.prototype.appendLine = function(str){
	if(typeof str != "undefined" && str != null){
		this.buffer.push(str.toString() + "\r\n");
	}
	return this;
};

/**
 * 清空数组.
 */
jStringBuffer.prototype.clear = function(){
	this.buffer = [];
	return this;
};

/**
 * 变会值类型只读字符串.
 * @returns
 */
jStringBuffer.prototype.toString = function() {
	if(this.buffer == null || this.buffer.length==0){
		return "";
	}
	return this.buffer.join("");
};


/*
 * 对于查询框内特殊符号的判断
 * 判读每行输入的为'%'或者'_'
 * 自动进行屏蔽
 * 
 */
function checkspecial(obj){
	var obj_value =obj.value;
	//obj.value=obj.value.replace(/[ -~]/g,'');
	obj.value=obj_value.replace(/[\%,\_]/g,'');
	if(obj_value!=obj.value){
		var msg = "不可录入%和_字符";
		addErrorMsg(obj,msg);
	}else{
		closeErrorMsg();
	}
}



function addErrorMsg(elem,msg){
	var span = $("#__ErrorMessagePanel");
	if(span.length>0){
		span.show();
	}else{
		var span = document.createElement("SPAN");
		span.id = "__ErrorMessagePanel";
		span.style.color = "red";
		if(elem.parentNode.lastChild)
			elem.parentNode.appendChild(span);
		span.innerHTML = msg;
	}
}

function closeErrorMsg(){
	var span = $("#__ErrorMessagePanel");
	if(span.length>0){
		span.hide(5000);
	}
}


