Ext.define("app.view.productCategory.ProductCategoryForm", {
	extend : "Ext.form.Panel",
	alias : "widget.productcategoryform",
	requires : [ "app.view.productCategory.ProductCategoryController", "app.view.productCategory.ProductCategoryModel" ],
	controller : "productCategory",
	viewModel : "productCategory",
	url : Ext.ctx + "/productCategory/save",
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
		fieldLabel : "排序号",
		name : "sortNo"
	}, {
		fieldLabel : "页面标题",
		name : "seoTitle"
	}, {
		fieldLabel : "页面关键字",
		name : "seoKeywords"
	}, {
		fieldLabel : "页面描述",
		name : "seoDescription"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});