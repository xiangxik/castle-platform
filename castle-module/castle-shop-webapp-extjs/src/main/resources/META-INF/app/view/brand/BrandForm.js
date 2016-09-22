Ext.define("app.view.brand.BrandForm", {
	extend : "Ext.form.Panel",
	alias : "widget.producttagform",
	requires : [ "app.view.brand.BrandController",
			"app.view.brand.BrandModel", "app.ux.form.KindUpload" ],
	controller : "brand",
	viewModel : "brand",
	url : Ext.ctx + "/brand/save",
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
		fieldLabel : "类型",
		xtype:"combobox",
		name : "type",
		editable:false,
		displayField:"value",
		valueField:"key",
		emptyText:"--请选择类型--",
		queryMode:"local",
		allowBlank : false,
		store:Ext.create("Ext.data.Store", {
			fields:["key", "value"],
			data:[{
				key:"text",value:"文本"
			},{
				key:"image",value:"图像"
			}
			]
		})
	}, {
		fieldLabel : "图标",
		name : "logo",
		xtype : "kindupload"
	}, {
		fieldLabel : "地址",
		name : "url"
	}, {
		xtype : "kindeditor",
		fieldLabel : "介绍",
		name : "introduction"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});