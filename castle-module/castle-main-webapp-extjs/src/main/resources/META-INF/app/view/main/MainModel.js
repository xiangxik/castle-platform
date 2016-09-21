Ext.define('app.view.main.MainModel', {
	extend : 'Ext.app.ViewModel',
	requires : [ "app.store.Menus" ],
	alias : 'viewmodel.main',
	constructor : function() {
		var me = this;
		this.callParent(arguments);

		Ext.Ajax.request({
			url : Ext.ctx + "/app_info",
			async : false,
			success : function(resp) {
				var app_info = Ext.decode(resp.responseText, true);
				Ext.apply(me.data, {
					app_info : app_info
				});
			}
		});
	},
	data : {
		user : {
			id : null,
			name : Ext.currentUser,
			username : "",
			email : "",
			mobile : ""
		}
	},

	stores : {
		navItems : {
			type : "menus"
		}
	}
});
