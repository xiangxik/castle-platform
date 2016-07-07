Ext.define("app.view.user.UserController", {
	extend : "Ext.app.ViewController",
	alias : "controller.user",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onAdd : function(button) {
		var code = "userform";
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.user.UserForm", {
				id : code,
				closable : true,
				title : "新建用户",
				iconCls : "fa fa-user"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "userform" + item.id;
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.user.UserForm", {
				id : code,
				closable : true,
				title : "编辑用户【" + item.get("name") + "】",
				iconCls : "fa fa-user"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowDelete : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + item.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("list");

				Ext.Ajax.request({
					url : Ext.ctx + "/user/delete",
					params : {
						id : item.id
					},
					method : "POST",
					success : function(response) {
						Ext.toast("操作成功", null, "t");
						store.remove(item);
					}
				});
			}
		}, this);
	},

	onFormSave : function(button) {
		var userform = button.up("userform");
		var form = userform.getForm();
		var store = this.getViewModel().getStore("list");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.toast("操作成功", null, "t");
					store.reload();
					me.closeTab(userform);
				},
				failure : function(form, action) {
					Ext.toast("操作失败", null, "t");
				}
			});
		}
	},

	onDelete : function(button) {
		var grid = button.up("userlist");
		var data = grid.getSelection();
		if (data.length == 0) {
			Ext.Msg.alert("提示", "您最少要选择一条数据");
		} else {
			Ext.Msg.confirm("提示", "您确定要删除所选数据？", function(choice) {
				if (choice === "yes") {
					var ids = [];
					Ext.each(data, function(record, index, array) {
						ids.push(record.id);
					});

					var store = this.getViewModel().getStore("list");

					Ext.Ajax.request({
						url : Ext.ctx + "/user/delete",
						params : {
							ids : ids
						},
						method : "POST",
						success : function(response) {
							Ext.toast("操作成功", null, "t");
							store.remove(data);
						}
					});
				}
			}, this);
		}
	}
});