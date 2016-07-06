Ext.define("app.store.Menus", {
	extend : "Ext.data.TreeStore",
	alias : "store.menus",
	model : "app.model.Menu",
	proxy : {
		type : "ajax",
		url : Ext.ctx + "/menu/tree",
		reader : {
			type : "json"
		}
	},
	autoLoad : true
});