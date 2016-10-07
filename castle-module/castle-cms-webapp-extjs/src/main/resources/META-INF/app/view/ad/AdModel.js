Ext.define("app.view.ad.AdModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.ad",
	stores : {
		list : Ext.create("app.store.Ads")
	}
});