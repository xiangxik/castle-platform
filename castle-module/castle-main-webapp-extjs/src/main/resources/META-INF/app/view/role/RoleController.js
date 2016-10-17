Ext.define("app.view.role.RoleController", {
	extend : "Ext.app.ViewController",
	alias : "controller.role",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onItemClick : function(grid, item) {

	},

	onAdd : function(button) {
		var code = "roleform";
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.role.RoleForm", {
				id : code,
				closable : true,
				title : "新建角色",
				iconCls : "fa fa-user"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowEdit : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "roleform" + item.id;
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.role.RoleForm", {
				id : code,
				closable : true,
				title : "编辑角色【" + item.get("name") + "】",
				iconCls : "fa fa-user"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowMenu : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "rolemenu" + item.id;
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.role.RoleMenu", {
				id : code,
				closable : true,
				title : "编辑角色【" + item.get("name") + "】的菜单",
				iconCls : "fa fa-user"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},

	onRowDelete : function(grid, rowIndex, colIndex) {
		var menu = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + menu.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = grid.getStore();

				Ext.Ajax.request({
					url : Ext.ctx + "/role/delete",
					params : {
						id : menu.id
					},
					method : "POST",
					success : function(response) {
						Ext.toast("操作成功", null, "t");
						store.remove(menu);
					}
				});
			}
		}, this);
	},

	onFormSave : function(button) {
		var formPanel = button.up("roleform");
		var form = formPanel.getForm();
		var store = this.getViewModel().getStore("list");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.toast("操作成功", null, "t");
					store.reload();
					me.closeTab(formPanel);
				},
				failure : function(form, action) {
					Ext.toast("操作失败", null, "t");
				}
			});
		}
	},

	onMenuSave : function(button) {
		var formPanel = button.up("rolemenu");
		var form = formPanel.getForm();

		var menuPanel = formPanel.down("treepanel");
		var menus = [];
		var menuChecked = menuPanel.getChecked();
		Ext.each(menuChecked, function(record, index, array) {
			if (record.get("id") != "root") {
				menus.push(record.get("id"));
			}
		});

		var me = this;
		if (form.isValid()) {

			var role = formPanel.getRecord();

			form.submit({
				params : {
					id : role.get("id"),
					menus : menus
				},
				success : function(form, action) {
					Ext.toast("操作成功", null, "t");
					me.closeTab(formPanel);
				},
				failure : function(form, action) {
					Ext.toast("操作失败", null, "t");
				}
			});
		}
	},

	onDelete : function(button) {
		var grid = button.up("rolelist");
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
						url : Ext.ctx + "/role/delete",
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