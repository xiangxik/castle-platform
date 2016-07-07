Ext.define("app.model.User", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "username",
		type : "string"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "mobile",
		type : "string"
	}, {
		name : "email",
		type : "string"
	}, {
		name : "lastLoginIp",
		type : "string"
	}, {
		name : "lastLoginDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "enabled",
		type : "boolean"
	}, {
		name : "locked",
		type : "boolean"
	}, {
		name : "accountExpired",
		type : "boolean"
	}, {
		name : "credentialsExpired",
		type : "boolean"
	} ]
});