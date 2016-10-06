Ext.define("app.model.Feedback", {
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
		name : "subject",
		type : "string"
	}, {
		name : "message",
		type : "string"
	}, {
		name : "createdDate",
		type : "date",
		dateFormat : "time"
	} ]
});