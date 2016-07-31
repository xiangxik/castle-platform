Ext.define("app.view.plugin.OauthPluginModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.oauthplugin",
	stores : {
		list : Ext.create("app.store.OauthPlugins")
	}
});