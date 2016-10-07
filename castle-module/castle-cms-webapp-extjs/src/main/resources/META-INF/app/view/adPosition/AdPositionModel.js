Ext.define("app.view.adPosition.AdPositionModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.adPosition",
	stores : {
		list : Ext.create("app.store.AdPositions")
	}
});