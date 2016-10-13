Ext.define("app.view.role.RoleList", {
	extend : "Ext.grid.Panel",
	alias : "widget.producttaglist",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel", "app.ux.grid.QueryPanel" ],
	title : "角色列表",
	controller : "role",
	viewModel : "role",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "代号",
		dataIndex : "code"
	}, {
		text : "锁定",
		dataIndex : "locked",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "操作",
		xtype : "actioncolumn",
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
			fieldLabel : "代号",
			xtype : "textfield",
			name : "code"
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