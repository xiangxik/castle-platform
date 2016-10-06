Ext.define("app.view.subscribe.SubscribeModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.subscribe",
	stores : {
		list : Ext.create("app.store.Subscribes")
	}
});