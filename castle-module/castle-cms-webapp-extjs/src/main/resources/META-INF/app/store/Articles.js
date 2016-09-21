Ext.define("app.store.Articles", {
	extend : "Ext.data.Store",
	alias : "store.articles",
	model : "app.model.Article",
	remoteFilter : true,
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/article/page?path_filter=*,*.*,*.*.id,*.*.name",
		reader : {
			type : "json",
			rootProperty : "rows"
		}
	},
	autoLoad : true
});