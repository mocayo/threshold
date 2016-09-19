Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1,                   //月份
		"d+" : this.getDate(),                        //日
		"h+" : this.getHours(),                       //小时
		"m+" : this.getMinutes(),                     //分
		"s+" : this.getSeconds(),                     //秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度
		"S" : this.getMilliseconds()                  //毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

// 获得日期，day为+时，获取后 day 天后的日期
function getDate(day) {
	var zdate = new Date();
	var sdate = zdate.getTime() - (1 * 24 * 60 * 60 * 1000);
	var edate = new Date(sdate + ((day + 1) * 24 * 60 * 60 * 1000)).format("yyyy-MM-dd");
	return edate;
}

//两个日期的比较，只比较年月日
function datecompare(date1, date2) {
	var datetime1 = date1.split("-");
	var datetime2 = date2.split("-");
	if (datetime1[0] > datetime2[0])
		return true;
	else if (datetime1[1] > datetime2[1])
		return true;
	else if (datetime1[2] > datetime2[2])
		return true;
	else
		return false;
}

function changeHeight(h) {
	var main = $(window.parent.document).find("#button_iframe");
	var thisheight = $(document).height();
	main.height(thisheight + h);
}

function iFrameHeight() {
	var ifm = document.getElementById("button_iframe");
	var subWeb = document.frames ? document.frames["button_iframe"].document
			: ifm.contentDocument;
	if (ifm != null && subWeb != null) {
		ifm.height = subWeb.body.scrollHeight;
	}
}