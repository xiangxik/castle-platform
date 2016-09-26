Ext.define("app.model.Setting", {
	extend : "Ext.data.Model",
	idProperty : "id",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "siteName",
		type : "string"
	}, {
		name : "logo",
		type : "string"
	}, {
		name : "introduction",
		type : "string"
	}, {
		name : "hotSearch",
		type : "string"
	}, {
		name : "address",
		type : "string"
	}, {
		name : "phone1",
		type : "string"
	}, {
		name : "phone2",
		type : "string"
	}, {
		name : "zipCode",
		type : "string"
	}, {
		name : "email",
		type : "string"
	}, {
		name : "certtext",
		type : "string"
	}, {
		name : "isSiteEnabled",
		type : "boolean"
	}, {
		name : "siteCloseMessage",
		type : "string"
	}],
	constructor : function() {
		this.callParent(arguments);
		app.model.Setting.setProxy({
			type : "ajax",
			url : Ext.ctx + "/setting/get"
		})
	}
});