Ext.define("app.model.Menu", {
	extend : "Ext.data.TreeModel",
	idProperty : "id",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "text",
		type : "string"
	}, {
		name : "code",
		type : "string"
	}, {
		name : "iconCls",
		type : "string"
	}, {
		name : "view",
		type : "string"
	}, {
		name : "config",
		type : "string"
	}, {
		name : "sortNo",
		type : "int"
	}, {
		name : "parent",
		mapping : "parent.id",
		type : "int"
	} ]
});