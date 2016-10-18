Ext.define("app.store.Questions", {
	extend : "Ext.data.Store",
	alias : "store.ads",
	model : "app.model.Question",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/question/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});