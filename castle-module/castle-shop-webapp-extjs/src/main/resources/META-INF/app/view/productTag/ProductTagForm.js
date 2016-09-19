Ext.define("app.view.productTag.ProductTagForm", {
	extend : "Ext.form.Panel",
	alias : "widget.producttagform",
	requires : [ "app.view.productTag.ProductTagController",
			"app.view.productTag.ProductTagModel", "app.ux.form.KindUpload" ],
	controller : "productTag",
	viewModel : "productTag",
	url : Ext.ctx + "/productTag/save",
	bodyPadding : 5,
	border : false,
	frame : false,
	scrollable : true,
	layout : "fit",
	defaults : {
		anchor : "90%"
	},
	fieldDefaults : {
		labelAlign : "right",
		labelWidth : 120
	},
	items : [ {
		bodyPadding : 5,
		border : false,
		frame : false,
		scrollable : true,
		layout : "anchor",
		defaults : {
			anchor : "90%"
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
			fieldLabel : "图标",
			name : "icon",
			xtype : "kindupload"
		}, {
			fieldLabel : "备注",
			name : "memo"
		} ]
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});