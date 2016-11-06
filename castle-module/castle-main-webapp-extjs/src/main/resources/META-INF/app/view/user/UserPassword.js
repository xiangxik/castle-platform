Ext.define("app.view.user.UserPassword", {
	extend : "Ext.form.Panel",
	alias : "widget.userpassword",
	requires : [ "app.view.user.UserController", "app.view.user.UserModel" ],
	controller : "user",
	viewModel : "user",
	url : Ext.ctx + "/user/password",
	bodyPadding : 5,
	border : false,
	frame : false,
	scrollable : true,
	layout : "anchor",
	defaults : {
		anchor : "90%"
	},
	fieldDefaults : {
		labelAlign : "right",
		labelWidth : 120
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : "密码",
		name : "newPassword",
		inputType : "password",
		allowBlank : false
	}],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onPasswordSave"
	} ]
});