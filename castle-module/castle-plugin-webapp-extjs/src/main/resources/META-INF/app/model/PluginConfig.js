Ext.define("app.model.PluginConfig", {
	extend : "Ext.data.Model",
	fields : [ {
		name : "id",
		type : "int"
	}, {
		name : "pluginId",
		type : "string"
	}, {
		name : "isEnabled",
		type : "boolean"
	}, {
		name : "attributes"
	} ],
	proxy : {
		type : "ajax"
	}
});