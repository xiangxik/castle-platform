Ext.define("app.store.ArticleCategories", {
	extend : "Ext.data.TreeStore",
	alias : "store.articleCategories",
	model : "app.model.ArticleCategory",
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/articleCategory/tree",
		reader : {
			type : "json"
		}
	},
	autoLoad : true
});