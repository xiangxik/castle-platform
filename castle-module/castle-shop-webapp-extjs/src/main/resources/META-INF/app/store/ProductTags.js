Ext.define("app.store.ProductTags", {
	extend : "Ext.data.Store",
	alias : "store.producttags",
	model : "app.model.ProductTag",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/productTag/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});