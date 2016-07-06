Ext.define("app.view.menu.MenuModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.menu",
	stores : {
		tree : Ext.create("app.store.Menus")
	}
});