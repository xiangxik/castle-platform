Ext.define("app.view.setting.SettingController", {
	extend : "Ext.app.ViewController",
	alias : "controller.setting",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onFormSave : function(button) {
		var settingform = button.up("settingform");
		var form = settingform.getForm();
		var store = this.getViewModel().getStore("list");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.toast("操作成功", null, "t");
				},
				failure : function(form, action) {
					Ext.toast("操作失败", null, "t");
				}
			});
		}
	}

});