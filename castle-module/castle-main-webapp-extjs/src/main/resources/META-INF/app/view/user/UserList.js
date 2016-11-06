Ext.define("app.view.user.UserList", {
	extend : "Ext.grid.Panel",
	alias : "widget.userlist",
	requires : [ "app.view.user.UserController", "app.view.user.UserModel", "app.ux.grid.QueryPanel" ],
	title : "用户列表",
	controller : "user",
	viewModel : "user",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "账号",
		dataIndex : "username"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "手机号",
		dataIndex : "mobile"
	}, {
		text : "邮箱",
		dataIndex : "email"
	}, {
		text : "最后登录IP",
		dataIndex : "lastLoginIp"
	}, {
		text : "最后登录时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日 H:i:s',
		dataIndex : "lastLoginDate",
		width : 180
	}, {
		text : "启用",
		dataIndex : "enabled",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
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
			iconCls : "fa fa-check-square actionColumnIcon",
			tooltip : "修改密码",
			handler : "onRowPassword"
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
			fieldLabel : "账号",
			xtype : "textfield",
			name : "username"
		}, {
			fieldLabel : "名称",
			xtype : "textfield",
			name : "name"
		}, {
			fieldLabel : "邮箱",
			xtype : "textfield",
			name : "email"
		}, {
			fieldLabel : "手机",
			xtype : "textfield",
			name : "mobile"
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