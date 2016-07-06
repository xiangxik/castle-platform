Ext.define("app.view.main.Main", {
	extend : "Ext.container.Viewport",
	xtype : "app-main",
	layout : "border",
	requires : [ "app.view.main.MainController", "app.view.main.MainModel", "app.view.main.Center" ],
	controller : "main",
	viewModel : "main",
	items : [ {
		region : "north",
		xtype : "toolbar",
		defaults : {
			xtype : "button"
		},
		items : [ {
			xtype : "label",
			bind : {
				html : "<i class='fa fa-2x fa-leaf' style='color: green'></i> {app_info.system_name} v{app_info.system_version}"
			},
			style : "font-size: 14;font-weight: 800"
		}, "->", {
			text : "用户",
			iconCls : "fa fa-user",
			menu : [ {
				text : "我的资料",
				iconCls : "fa fa-info-circle"
			}, "-", {
				text : "退出",
				iconCls : "fa fa-sign-out",
				handler : "onSignOut"
			} ]
		} ]
	}, {
		region : "south",
		xtype : "toolbar",
		ui : "footer",
		border : true,
		items : [ "->", {
			bind : {
				html : "&copy;{app_info.company_name}"
			},
			xtype : "label"
		}, "->" ]
	}, {
		region : 'west',
		title : "导航",
		width : 200,
		split : true,
		collapsible : true,
		layout : {
			type : "vbox",
			align : "stretch"
		},
		border : false,
		scrollable : "y",
		bodyCls : "treelist-with-nav",
		items : [ {
			xtype : "treelist",
			// xtype:"treepanel",
			rootVisible : false,
			expanderOnly : true,
			ui : "nav",
			highlightPath : true,
			listeners : {
				itemclick : "onNavItemClick"
			},
			bind : {
				store : "{navItems}"
			}
		} ]
	}, {
		region : "center",
		xtype : "maincenter"
	} ]
});