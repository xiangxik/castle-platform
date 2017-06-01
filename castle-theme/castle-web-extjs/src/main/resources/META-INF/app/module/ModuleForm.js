Ext.define("app.module.ModuleForm", {
	extend : "Ext.form.Panel",
	mixins : {
		module : "app.module.ModuleController"
	},
	saveUrl : "",
	constructor : function() {
		var me = this;

		me.buttonAlign = "center";
		me.buttons = [ {
			text : "保存",
			formBind : true,
			handler : me.onFormSave
		}, {
			text : "关闭",
			handler : me.onCloseTab
		} ]

		this.callParent(arguments);

		me.add({
			xtype : "hiddenfield",
			name : "id"
		});
	},
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
	setStore : function(store) {
		this.store = store;
	},
	getStore : function() {
		return this.store;
	}
});