Ext.define('app.view.main.MainController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	mixins : {
		center : "app.view.main.CenterController"
	},

	loadManagerReference : function() {
		var vm = this.getViewModel();
		vm.getStore("navItems").reload();
	},

	onLoginSubmit : function(button) {
		var me = this;
		var window = button.up('window');
		var form = window.down("form").getForm();
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					window.hide();
					me.getView().mask("加载系统中...");
					me.loadManagerReference();
					me.getView().unmask();
				},
				failure : function(form, action) {
					console.log("b")
					if (action && action.result) {
						Ext.Msg.alert("提示", action.result.msg);
					}

					document.getElementById("captchaImage").src = Ext.ctx + "/captcha.jpg?_t=" + new Date().getTime();
				}
			});
		}
	},
	onSignOut : function() {
		Ext.Msg.confirm("提示", "您确定要退出系统？", function(choice) {
			if (choice === "yes") {
				window.location.href = Ext.ctx + "/logout";
			}
		}, this);
	},

	onNavItemClick : function(tree, obj) {
		var item = obj.item;
		if (obj.item.getLeaf()) {
			var node = obj.node;
			var data = node.get("data");console.log(data);
			var code = data.code;
			var tab = this.findTabByCode(code);
			if (!tab) {
				var view = data.view;
				var viewConfig = data.config;
				if (view) {
					var config = {};
					if (viewConfig) {
						Ext.apply(config, viewConfig);
					}
					config.closable = true;
					config.id = code;
					config.code = code;
					config.title = node.get("text");
					config.iconCls = node.get("iconCls");

					tab = this.addViewToCenter(code, Ext.create(view, config));
				}
			}
			this.activeTab(tab);
		}
	},

	onConfirm : function(choice) {
		if (choice === 'yes') {

		}
	}
});
