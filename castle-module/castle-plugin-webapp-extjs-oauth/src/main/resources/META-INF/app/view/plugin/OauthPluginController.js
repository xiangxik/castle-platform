Ext.define("app.view.plugin.OauthPluginController", {
	extend : "Ext.app.ViewController",
	alias : "controller.oauthplugin",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var plugin = grid.getStore().getAt(rowIndex);
		if (plugin.get("isInstalled")) {
			var code = "pluginform" + plugin.id;
			var tab = this.findTabByCode(code);
			if (!tab) {
				var view = Ext.create(plugin.get("settingView"), {
					id : code,
					closable : true,
					title : "编辑认证插件【" + plugin.get("name") + "】",
					iconCls : "fa fa-plug"
				});
				var pluginConfig = Ext.create("app.model.PluginConfig");
				pluginConfig.getProxy().setUrl(Ext.ctx + plugin.get("settingUrl"));
				pluginConfig.load({
					scope : this,
					success : function(record, operation) {
						view.loadRecord(record);
						view.setData(record.getData());
						tab = this.addViewToCenter(code, view);
						this.activeTab(tab);
					},
					failure : function(record, operation) {
						Ext.toast("操作失败", null, "t");
					}
				});
			}
		} else {
			Ext.toast("【" + plugin.get("name") + "】未安装", "提示", "t");
		}
	},

	onRowInstall : function(grid, rowIndex, colIndex) {
		var plugin = grid.getStore().getAt(rowIndex);
		if (plugin.get("isInstalled")) {
			Ext.Msg.alert("提示", "【" + plugin.get("name") + "】已安装");
		} else {
			Ext.Msg.confirm("提示", "您确定要安装【" + plugin.get("name") + "】？", function(choice) {
				if (choice === "yes") {

					var store = this.getViewModel().getStore("list");

					Ext.Ajax.request({
						url : Ext.ctx + plugin.get("installUrl"),
						method : "POST",
						timeout : 10000,
						success : function(response) {
							Ext.toast("操作成功", null, "t");
							store.reload();
						}
					});
				}
			}, this);
		}
	},

	onRowUninstall : function(grid, rowIndex, colIndex) {
		var plugin = grid.getStore().getAt(rowIndex);
		if (plugin.get("isInstalled")) {
			Ext.Msg.confirm("提示", "您确定要卸载【" + plugin.get("name") + "】？", function(choice) {
				if (choice === "yes") {

					var store = this.getViewModel().getStore("list");

					Ext.Ajax.request({
						url : Ext.ctx + plugin.get("uninstallUrl"),
						method : "POST",
						timeout : 10000,
						success : function(response) {
							Ext.toast("操作成功", null, "t");
							store.reload();
						}
					});
				}
			}, this);
		} else {
			Ext.toast("【" + plugin.get("name") + "】未安装", "提示", "t");
		}
	},
	onFormSave : function(button) {
		var pluginForm = button.up("form");
		var form = pluginForm.getForm();
		var store = this.getViewModel().getStore("list");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.toast("操作成功", null, "t");
					store.reload();
					me.closeTab(pluginForm);
				},
				failure : function(form, action) {
					Ext.toast("操作失败", null, "t");
				}
			});
		}
	}
});