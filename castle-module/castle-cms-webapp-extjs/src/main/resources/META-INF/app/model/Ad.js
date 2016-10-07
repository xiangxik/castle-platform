Ext.define("app.model.Ad", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "title",
		type : "string"
	}, {
		name : "type",
		type : "string"
	}, {
		name : "content",
		type : "string"
	}, {
		name : "path",
		type : "string"
	}, {
		name : "beginDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "endDate",
		type : "date",
		dateFormat : "time"
	}, {
		name : "url",
		type : "string"
	}, {
		name : "adPositionName",
		mapping : "adPosition.name"
	}, {
		name : "adPosition",
		mapping : "adPosition.id",
		reference : "app.model.AdPosition"
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