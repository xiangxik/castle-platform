Ext.define("app.store.OauthPlugins", {
	extend : "Ext.data.Store",
	alias : "store.oauthplugins",
	model : "app.model.Plugin",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/oauth/plugin/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});