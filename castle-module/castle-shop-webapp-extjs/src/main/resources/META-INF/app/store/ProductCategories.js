Ext.define("app.store.ProductCategories", {
	extend : "Ext.data.TreeStore",
	alias : "store.productCategories",
	model : "app.model.ProductCategory",
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/productCategory/tree",
		reader : {
			type : "json"
		}
	},
	autoLoad : true
});