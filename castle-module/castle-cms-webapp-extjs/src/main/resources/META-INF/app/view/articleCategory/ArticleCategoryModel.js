Ext.define("app.view.articleCategory.ArticleCategoryModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.articleCategory",
	stores : {
		tree : Ext.create("app.store.ArticleCategories")
	}
});