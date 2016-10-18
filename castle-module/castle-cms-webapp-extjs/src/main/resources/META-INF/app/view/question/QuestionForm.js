Ext.define("app.view.question.QuestionForm", {
	extend : "Ext.form.Panel",
	alias : "widget.questionform",
	requires : [ "app.view.question.QuestionController", "app.view.question.QuestionModel", "app.ux.form.KindEditor" ],
	controller : "question",
	viewModel : "question",
	url : Ext.ctx + "/question/save",
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
		fieldLabel : "标题",
		name : "title",
		allowBlank : false
	}, {
		xtype : "kindeditor",
		fieldLabel : "内容",
		name : "content"
	}, {
		fieldLabel : "排序号",
		name : "sortNo"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});