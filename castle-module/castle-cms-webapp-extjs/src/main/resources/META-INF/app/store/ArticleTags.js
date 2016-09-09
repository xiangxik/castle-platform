Ext.define("app.store.ArticleTags", {
	extend : "Ext.data.Store",
	alias : "store.articletags",
	model : "app.model.ArticleTag",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/articleTag/page",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});