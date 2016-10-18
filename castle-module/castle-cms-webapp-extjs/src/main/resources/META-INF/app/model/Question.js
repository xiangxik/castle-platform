Ext.define("app.model.Question", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "title",
		type : "string"
	}, {
		name : "content",
		type : "string"
	}, {
		name : "sortNo",
		type : "int"
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