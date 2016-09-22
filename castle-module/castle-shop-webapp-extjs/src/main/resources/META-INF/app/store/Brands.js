Ext.define("app.store.Brands", {
	extend : "Ext.data.Store",
	alias : "store.producttags",
	model : "app.model.Brand",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/brand/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});