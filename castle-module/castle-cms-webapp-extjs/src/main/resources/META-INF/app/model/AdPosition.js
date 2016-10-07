Ext.define("app.model.AdPosition", {
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
		name : "width",
		type : "int"
	}, {
		name : "height",
		type : "int"
	}, {
		name : "description",
		type : "string"
	}, {
		name : "template",
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