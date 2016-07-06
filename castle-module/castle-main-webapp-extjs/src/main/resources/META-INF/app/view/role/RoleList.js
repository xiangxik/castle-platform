Ext.define("app.view.role.RoleList", {
	extend : "app.module.ModuleList",
	title : "角色列表",
	listUrl : Ext.ctx + "/role/page",
	saveUrl : Ext.ctx + "/role/save",
	deleteUrl : Ext.ctx + "/role/delete",
	modelFields : [ "name", "code", {
		type : 'boolean',
		name : 'locked'
	} ],
	filterItems : [ {
		fieldLabel : "名称",
		xtype : "textfield",
		name : "code"
	}, {
		fieldLabel : "代号",
		xtype : "textfield",
		name : "name"
	} ],
	gridColumns : [ {
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
	} ],
	formItems : [ {
		fieldLabel : "名称",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "代号",
		name : "code",
		allowBlank : false
	} ]
});