Ext.define("app.view.user.UserModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.user",
	stores : {
		list : Ext.create("app.store.Users")
	}
});