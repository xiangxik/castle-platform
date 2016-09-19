Ext.define("app.view.productCategory.ProductCategoryModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.productCategory",
	stores : {
		tree : Ext.create("app.store.ProductCategories")
	}
});