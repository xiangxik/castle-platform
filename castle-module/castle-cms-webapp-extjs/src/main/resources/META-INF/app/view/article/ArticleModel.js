Ext.define("app.view.article.ArticleModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.article",
	stores : {
		tree : Ext.create("app.store.Articles")
	}
});