Ext.define("app.view.job.JobForm", {
	extend : "Ext.form.Panel",
	alias : "widget.jobform",
	requires : [ "app.view.job.JobController", "app.view.job.JobModel", "app.ux.form.KindEditor"],
	controller : "job",
	viewModel : "job",
	url : Ext.ctx + "/job/save",
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
		xtype : "checkboxgroup",
		fieldLabel : "设置",
		layout : {
			type : "hbox",
			align : "left"
		},
		defaultType : "checkbox",
		items : [ {
			boxLabel : "是否发布",
			hideLabel : true,
			name : "list"
		}]
	}, {
		xtype : "kindeditor",
		fieldLabel : "内容",
		name : "content",
		height : 140,
		width : 670
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});