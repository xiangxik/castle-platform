Ext.define("app.view.role.RoleForm", {
	extend : "Ext.form.Panel",
	alias : "widget.roleform",
	requires : [ "app.view.role.RoleController",
			"app.view.role.RoleModel", "app.ux.form.KindUpload" ],
	controller : "role",
	viewModel : "role",
	url : Ext.ctx + "/role/save",
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
		fieldLabel : "名称",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "代号",
		name : "code",
		allowBlank : false
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});