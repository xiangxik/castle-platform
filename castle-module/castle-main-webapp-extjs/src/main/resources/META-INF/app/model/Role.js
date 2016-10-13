Ext.define("app.model.Role", {
	extend : "Ext.data.Model",
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
		name : "locked",
		type : "boolean"
	}, {
		name : "lastModifiedByName",
		mapping : "lastModifiedBy.name"
	}, {
		name : "lastModifiedBy",
		mapping : "lastModifiedBy.id",
		reference : "app.model.User"
	}, {
		name : "lastModifiedDate",
		type : "date",
		dateFormat : "time"
	} ]
});