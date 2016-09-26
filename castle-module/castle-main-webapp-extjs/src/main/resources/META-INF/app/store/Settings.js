Ext.define("app.store.Settings", {
	extend : "Ext.data.Store",
	alias : "store.settings",
	model : "app.model.Setting",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/setting/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});