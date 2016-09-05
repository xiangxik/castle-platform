Ext.define("app.view.article.ArticleModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.article",
	stores : {
		list : Ext.create("app.store.Articles")
	}
});