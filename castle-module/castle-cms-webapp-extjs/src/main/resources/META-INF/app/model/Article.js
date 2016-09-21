Ext.define("app.model.Article", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "title",
		type : "string"
	}, {
		name : "coverImage",
		type : "string"
	}, {
		name : "author",
		type : "string"
	}, {
		name : "content",
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
		name : "publication",
		type : "boolean"
	}, {
		name : "stick",
		type : "boolean"
	}, {
		name : "hits",
		type : "int"
	}, {
		name : "articleCategoryName",
		mapping : "articleCategory.name"
	}, {
		name : "articleCategory",
		mapping : "articleCategory.id",
		reference : "app.model.ArticleCategory"
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