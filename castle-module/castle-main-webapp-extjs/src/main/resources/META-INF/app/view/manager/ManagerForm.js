Ext.define("app.view.manager.ManagerForm", {
	extend : "app.module.ModuleForm",
	title : "管理员表单",
	saveUrl : Ext.ctx + "/manager/save",
	items : [ {
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