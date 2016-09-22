Ext.define("app.view.brand.BrandModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.brand",
	stores : {
		list : Ext.create("app.store.Brands")
	}
});