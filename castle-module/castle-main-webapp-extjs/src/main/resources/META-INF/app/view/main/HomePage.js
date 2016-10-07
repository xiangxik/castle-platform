Ext.define("app.view.main.HomePage", {
	extend : "Ext.panel.Panel",
	alias : "widget.homepage",
	title : "首页",
	iconCls : "fa fa-home",
	frame : false,
	border : false,
	layout: "fit",
	bind : {
        html: "<table class='input'><tbody>" +
        		"<tr>" +
        			"<th>网站名称</th><td>{app_info.system_name}</td>" +
        			"<th>版本</th><td>{app_info.system_version}</td>" +
        		"</tr>" +
        		"<tr>" +
        			"<th>公司名称</th><td colspan='3'>{app_info.company_name}</td>" +
        		"</tr>" +
        		"<tr><td colspan='4'> <br/> </td></tr>" +
        		"<tr>" +
	    			"<th>JAVA版本</th><td>{app_info.javaVersion}</td>" +
	    			"<th>JAVA路径</th><td>{app_info.javaHome}</td>" +
	    		"</tr>" +
	    		"<tr>" +
	    			"<th>操作系统名称</th><td>{app_info.osName}</td>" +
	    			"<th>操作系统构架</th><td>{app_info.osArch}</td>" +
	    		"</tr>" +
		    		"<tr>" +
	    			"<th>Servlet信息</th><td>{app_info.serverInfo}</td>" +
	    			"<th>Servlet版本</th><td>{app_info.servletVersion}</td>" +
	    		"</tr>" +
        	"</tbody></table>"
        }
})