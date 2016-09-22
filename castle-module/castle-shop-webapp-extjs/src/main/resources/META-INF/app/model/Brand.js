Ext.define("app.model.Brand", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "logo",
		type : "string"
	}, {
		name : "type",
		type : "string"
	}, {
		name : "url",
		type : "string"
	}, {
		name : "introduction",
		type : "string"
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