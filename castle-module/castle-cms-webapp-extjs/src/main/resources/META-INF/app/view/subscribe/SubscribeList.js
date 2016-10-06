Ext.define("app.view.subscribe.SubscribeList", {
	extend : "Ext.grid.Panel",
	alias : "widget.subscribelist",
	requires : [ "app.view.subscribe.SubscribeController", "app.view.subscribe.SubscribeModel", "app.ux.grid.QueryPanel" ],
	title : "反馈列表",
	controller : "subscribe",
	viewModel : "subscribe",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "邮件",
		dataIndex : "email"
	}, {
		text : "创建时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日 H:i:s',
		dataIndex : "createdDate",
		width : 180
	}, {
		text : "操作",
		xtype : "actioncolumn",
		items : [ {
			iconCls : "fa fa-minus-square actionColumnIcon",
			tooltip : "删除",
			handler : "onRowDelete"
		} ]
	} ],
	tbar : [ {
		xtype : "button",
		text : "删除",
		iconCls : "fa fa-minus-circle",
		handler : "onDelete"
	} ],
	selType : "checkboxmodel",
	multiSelect : true,
	bind : {
		store : "{list}"
	},
	dockedItems : [ {
		xtype : "querypanel",
		dock : "top",
		bind : {
			store : "{list}"
		},
		filterItems : [ {
			fieldLabel : "名称",
			xtype : "textfield",
			name : "name"
		}, {
			fieldLabel : "邮箱",
			xtype : "textfield",
			name : "email"
		} ]
	}, {
		xtype : "pagingtoolbar",
		dock : "bottom",
		displayInfo : true,
		bind : {
			store : "{list}"
		}
	} ]
});