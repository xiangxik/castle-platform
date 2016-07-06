Ext.define("app.view.menu.MenuList", {
	extend : "Ext.tree.Panel",
	alias : "widget.menu",
	requires : [ "app.view.menu.MenuController", "app.view.menu.MenuModel" ],
	controller : "menu",
	viewModel : "menu",
	rootVisible : false,
	forceFit : true,
	bind : {
		store : "{tree}"
	},
	columns : [ {
		xtype : "treecolumn",
		text : "名称",
		dataIndex : "text",
		flex : 4
	}, {
		text : "代号",
		dataIndex : "code",
		flex : 2
	}, {
		text : "图标样式",
		dataIndex : "iconCls",
		flex : 2
	}, {
		text : "视图",
		dataIndex : "view",
		flex : 3
	}, {
		text : "配置参数",
		dataIndex : "config",
		flex : 2
	}, {
		text : "排序号",
		dataIndex : "sortNo",
		flex : 1
	}, {
		text : "操作",
		xtype : "actioncolumn",
		flex : 1,
		items : [ {
			iconCls : "fa fa-pencil-square actionColumnIcon",
			tooltip : "编辑",
			handler : "onRowEdit"
		}, {
			iconCls : "fa fa-minus-square actionColumnIcon",
			tooltip : "删除",
			handler : "onRowDelete"
		} ]
	} ],
	tbar : [ {
		xtype : "button",
		text : "添加",
		iconCls : "fa fa-plus-circle",
		handler : "onAdd"
	}, {
		xtype : "button",
		text : "删除",
		iconCls : "fa fa-minus-circle",
		handler : "onDelete"
	} ]
});