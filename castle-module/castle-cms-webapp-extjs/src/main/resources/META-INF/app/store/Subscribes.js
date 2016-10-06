Ext.define("app.store.Subscribes", {
	extend : "Ext.data.Store",
	alias : "store.subscribes",
	model : "app.model.Subscribe",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/subscribe/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});