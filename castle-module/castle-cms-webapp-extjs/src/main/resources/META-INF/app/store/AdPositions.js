Ext.define("app.store.AdPositions", {
	extend : "Ext.data.Store",
	alias : "store.adPositions",
	model : "app.model.AdPosition",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/adPosition/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});