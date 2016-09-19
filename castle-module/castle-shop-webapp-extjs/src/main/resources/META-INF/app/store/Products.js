Ext.define("app.store.Products", {
	extend : "Ext.data.Store",
	alias : "store.products",
	model : "app.model.Product",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/product/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});