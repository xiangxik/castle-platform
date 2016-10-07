Ext.define("app.view.adPosition.AdPositionForm", {
	extend : "Ext.form.Panel",
	alias : "widget.adPositionform",
	requires : [ "app.view.adPosition.AdPositionController", "app.view.adPosition.AdPositionModel" ],
	controller : "adPosition",
	viewModel : "adPosition",
	url : Ext.ctx + "/adPosition/save",
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
	}, {
		fieldLabel : "宽度",
		name : "width",
		allowBlank : false
	}, {
		fieldLabel : "高度",
		name : "height",
		allowBlank : false
	}, {
		fieldLabel : "描述",
		name : "description",
		xtype : "textarea"
	}, {
		fieldLabel : "模板",
		name : "template",
		xtype : "textarea",
		allowBlank : false
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});