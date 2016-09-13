Ext.define('app.Application', {
	extend : 'Ext.app.Application',

	name : 'app',

	appFolder : Ext.ctx + "/extjs/app",

	stores : [
	],

	launch : function() {
		
		Ext.override(Ext.form.field.Base, {
			initComponent : function() {
				if (this.allowBlank !== undefined && !this.allowBlank) {
					if (this.afterLabelTextTpl) {
						this.afterLabelTextTpl.push("<span style=\"color:red;font-weight:bold\" data-qtip=\"必填选项\">*</span>");
					} else {
						this.afterLabelTextTpl = [ "<span style=\"color:red;font-weight:bold\" data-qtip=\"必填选项\">*</span>" ];
					}
				}
				this.callParent(arguments);
			}
		});
		
		var loginWindow = null;
		Ext.Ajax.on("requestexception", function(conn, response, options, eOpts) {
			if (response.status == 403) {
				if (!loginWindow) {
					loginWindow = Ext.create("app.view.main.Login");
				} else {
					Ext.toast("请登录", null, "t");
				}
				loginWindow.show();

			} else {
				Ext.toast("系统错误", null, "t");
			}
		});

		Ext.create("app.view.main.Main");
	},

	onAppUpdate : function() {
		Ext.Msg.confirm('Application Update', 'This application has an update, reload?', function(choice) {
			if (choice === 'yes') {
				window.location.reload();
			}
		});
	}
});
