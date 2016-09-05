Ext.define("app.view.articleCategory.ArticleCategoryForm", {
	extend : "Ext.form.Panel",
	alias : "widget.articlecategoryform",
	requires : [ "app.view.articleCategory.ArticleCategoryController", "app.view.articleCategory.ArticleCategoryModel" ],
	controller : "articleCategory",
	viewModel : "articleCategory",
	url : Ext.ctx + "/articleCategory/save",
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
			fieldLabel : "排序号",
			name : "sortNo"
		}, {
			fieldLabel : "SEO标题",
			name : "seoTitle"
		}, {
			fieldLabel : "SEO关键字",
			name : "seoKeywords"
		}, {
			fieldLabel : "SEO描述",
			name : "seoDescription"
		} ]
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});