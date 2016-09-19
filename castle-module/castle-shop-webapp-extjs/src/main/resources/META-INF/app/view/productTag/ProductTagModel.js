Ext.define("app.view.productTag.ProductTagModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.productTag",
	stores : {
		list : Ext.create("app.store.ProductTags")
	}
});