Ext.define("app.store.Users", {
	extend : "Ext.data.Store",
	alias : "store.users",
	model : "app.model.User",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/user/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});