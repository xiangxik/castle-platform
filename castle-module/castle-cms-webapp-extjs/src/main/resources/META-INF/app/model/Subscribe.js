Ext.define("app.model.Subscribe", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "email",
		type : "string"
	}, {
		name : "createdDate",
		type : "date",
		dateFormat : "time"
	} ]
});