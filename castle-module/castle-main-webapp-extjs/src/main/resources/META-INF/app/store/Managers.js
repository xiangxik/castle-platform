Ext.define("app.store.Managers", {
	extend : "Ext.data.Store",
	alias : "store.managers",
	model : "app.model.Manager",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/admin/manager/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});