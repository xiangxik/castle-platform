Ext.define("app.view.ad.AdForm", {
	extend : "Ext.form.Panel",
	alias : "widget.adform",
	requires : [ "app.view.ad.AdController", "app.view.ad.AdModel", "app.ux.form.KindUpload", "app.ux.field.DateTime" ],
	controller : "ad",
	viewModel : "ad",
	url : Ext.ctx + "/ad/save",
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
		fieldLabel : "广告位",
		xtype : "combobox",
		name : "adPosition",
		editable : false,
		displayField : "name",
		valueField : "id",
		emptyText : "--请选择广告位--",
		queryMode : "local",
		allowBlank : false,
		store : Ext.create("app.store.AdPositions")
	}, {
		fieldLabel : "标题",
		name : "title",
		allowBlank : false
	}, {
		fieldLabel : "类型",
		xtype : "combobox",
		name : "type",
		editable : false,
		displayField : "name",
		valueField : "id",
		emptyText : "--请选择类型--",
		allowBlank : false,
		store : Ext.create("Ext.data.Store", {
			fields : [ "id", "name" ],
			data : [ {
				id : "text",
				name : "文本"
			}, {
				id : "code",
				name : "代码"
			}, {
				id : "image",
				name : "图片"
			}, {
				id : "flash",
				name : "动画"
			}, {
				id : "video",
				name : "视频"
			} ]
		})
	}, {
		xtype : "textarea",
		fieldLabel : "内容",
		name : "content"
	}, {
		fieldLabel : "路径",
		name : "path",
		xtype : "kindupload"
	}, {
		xtype : "datetimefield",
		fieldLabel : "开始时间",
		name : "beginDate",
		format : "Y年m月d日 H:i:s",
	}, {
		xtype : "datetimefield",
		fieldLabel : "结束时间",
		name : "endDate",
		format : "Y年m月d日 H:i:s",
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