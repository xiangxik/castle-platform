Ext.define("app.store.Ads", {
	extend : "Ext.data.Store",
	alias : "store.ads",
	model : "app.model.Ad",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/ad/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});