Ext.define("app.store.Feedbacks", {
	extend : "Ext.data.Store",
	alias : "store.feedbacks",
	model : "app.model.Feedback",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/feedback/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});