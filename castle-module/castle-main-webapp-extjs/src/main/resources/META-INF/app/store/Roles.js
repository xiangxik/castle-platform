Ext.define("app.store.Roles", {
	extend : "Ext.data.Store",
	alias : "store.roles",
	model : "app.model.Role",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/role/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});