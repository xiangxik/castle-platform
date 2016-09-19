Ext.define("app.model.ProductCategory", {
	extend : "Ext.data.TreeModel",
	idProperty : "id",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "seoTitle",
		type : "string"
	}, {
		name : "seoKeywords",
		type : "string"
	}, {
		name : "seoDescription",
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