Ext.define("app.model.Plugin", {
	extend : "Ext.data.Model",
	idProperty : "id",
	fields : [ {
		name : "id",
		type : "string"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "version",
		type : "string"
	}, {
		name : "author",
		type : "string"
	}, {
		name : "settingView",
		type : "string"
	}, {
		name : "installUrl",
		type : "string"
	}, {
		name : "uninstallUrl",
		type : "string"
	}, {
		name : "settingUrl",
		type : "string"
	}, {
		name : "sortNo",
		type : "int"
	}, {
		name : "isInstalled",
		type : "boolean"
	}, {
		name : "isEnabled",
		type : "boolean"
	} ]
});