Ext.define("app.store.Jobs", {
	extend : "Ext.data.Store",
	alias : "store.jobs",
	model : "app.model.Job",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/job/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});