var parseQueryString = function(arr_url) {
	var reg_para = /([^&=]+)=([\w\W]*?)(&|$|#)/g, ret = {};
	if (arr_url) {
		var str_para = arr_url, result;
		while ((result = reg_para.exec(str_para)) != null) {
			if (result[2]) {
				ret[result[1]] = decodeURIComponent(result[2]);
			}
		}
	}
	return ret;
};

var activeMenu = function(code) {
	Pace.restart();

	var $sidebarMenu = $(".sidebar-menu");
	$sidebarMenu.find("li").removeClass("active");

	var menu = $sidebarMenu.find(".menu_" + code);
	var parentCode = menu.data("parent-code");
	if (parentCode) {
		$sidebarMenu.find(".menu_" + parentCode).addClass("active");
	}
	menu.addClass("active");
};