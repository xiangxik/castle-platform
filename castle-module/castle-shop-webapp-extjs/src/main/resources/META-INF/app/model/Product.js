Ext.define("app.model.Product", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "name",
		type : "string"
	}, {
		name : "fullName",
		type : "string"
	}, {
		name : "image",
		type : "string"
	}, {
		name : "unit",
		type : "string"
	}, {
		name : "weight",
		type : "int"
	}, {
		name : "canSize",
		type : "string"
	}, {
		name : "cartonSize",
		type : "string"
	}, {
		name : "list",
		type : "boolean"
	}, {
		name : "top",
		type : "boolean"
	}, {
		name : "gift",
		type : "boolean"
	}, {
		name : "introduction",
		type : "string"
	}, {
		name : "memo",
		type : "string"
	}, {
		name : "keyword",
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
		name : "productCategoryName",
		mapping : "productCategory.name"
	}, {
		name : "productCategory",
		mapping : "productCategory.id",
		reference : "app.model.ProductCategory"
	}, {
		name : "brandName",
		mapping : "brand.name"
	}, {
		name : "brand",
		mapping : "brand.id",
		reference : "app.model.Brand"
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