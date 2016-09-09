Ext.define("app.view.articleTag.ArticleTagModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.articleTag",
	stores : {
		list : Ext.create("app.store.ArticleTags")
	}
});