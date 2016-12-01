Ext.define("app.model.Menu", {
	extend : "Ext.data.TreeModel",
	idProperty : "id",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "code",
		type : "string"
	}, {
		name : "iconCls",
		type : "string"
	}, {
		name : "href",
		type : "string"
	}, {
		name : "parameters",
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