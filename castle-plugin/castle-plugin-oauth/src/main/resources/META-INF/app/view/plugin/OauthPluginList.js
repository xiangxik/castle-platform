Ext.define("app.view.plugin.OauthPluginList", {
	extend : "Ext.grid.Panel",
	alias : "widget.oauthpluginlist",
	requires : [ "app.view.plugin.OauthPluginController", "app.view.plugin.OauthPluginModel" ],
	title : "认证插件",
	controller : "oauthplugin",
	viewModel : "oauthplugin",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "版本",
		dataIndex : "version"
	}, {
		text : "排序号",
		dataIndex : "sortNo"
	}, {
		text : "是否已安装",
		dataIndex : "isInstalled",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "是否已启用",
		dataIndex : "isEnabled",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "操作",
		xtype : "actioncolumn",
		width : 80,
		items : [ {
			iconCls : "fa fa-sign-in actionColumnIcon",
			tooltip : "安装",
			handler : "onRowInstall"
		}, {
			iconCls : "fa fa-sign-out actionColumnIcon",
			tooltip : "卸载",
			handler : "onRowUninstall"
		}, {
			iconCls : "fa fa-pencil-square actionColumnIcon",
			tooltip : "编辑",
			handler : "onRowEdit"
		} ]
	} ],
	bind : {
		store : "{list}"
	},
	dockedItems : [ {
		xtype : "pagingtoolbar",
		dock : "bottom",
		displayInfo : true,
		bind : {
			store : "{list}"
		}
	} ]
});