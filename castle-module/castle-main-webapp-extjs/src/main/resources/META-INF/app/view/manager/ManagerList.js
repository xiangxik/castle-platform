Ext.define("app.view.manager.ManagerList", {
	extend : "app.module.ModuleList",
	title : "管理员列表",
	listUrl : Ext.ctx + "/manager/page",
	saveUrl : Ext.ctx + "/manager/save",
	deleteUrl : Ext.ctx + "/manager/delete",
	modelFields : [ "username", "name", "mobile", "email", "lastLoginIp", {
		name : "lastLoginDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "enabled",
		type : "boolean"
	}, {
		name : "locked",
		type : "boolean"
	}, {
		name : "accountExpired",
		type : "boolean"
	}, {
		name : "credentialsExpired",
		type : "boolean"
	} ],
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
	} ],
	gridColumns : [ {
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
		text : "账号过期",
		dataIndex : "accountExpired",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "密码过期",
		dataIndex : "credentialsExpired",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	} ],
	formItems : [ {
		fieldLabel : "账号",
		name : "username",
		allowBlank : false
	}, {
		fieldLabel : "姓名",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "邮箱",
		name : "email",
		vtype : "email"
	}, {
		fieldLabel : "手机",
		name : "mobile",
		regex : /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|13\d{9})$/
	} ]
});