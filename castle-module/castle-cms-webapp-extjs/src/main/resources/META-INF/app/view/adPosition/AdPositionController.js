Ext.define("app.view.adPosition.AdPositionController", {
	extend : "Ext.app.ViewController",
	alias : "controller.adPosition",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onItemClick : function(grid, item) {
		
	},

	onAdd : function(button) {
		var code = "adPositionform";
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.adPosition.AdPositionForm", {
				id : code,
				closable : true,
				title : "新建广告位",
				iconCls : "fa fa-user"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},
	
	onRowEdit : function(grid, rowIndex, colIndex) {
		var item = grid.getStore().getAt(rowIndex);
		var code = "adPositionform" + item.id;
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.adPosition.AdPositionForm", {
				id : code,
				closable : true,
				title : "编辑广告位【" + item.get("title") + "】",
				iconCls : "fa fa-user"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},
	
	onRowDelete : function(grid, rowIndex, colIndex) {
		var menu = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + menu.get("title") + "】？", function(choice) {
			if (choice === "yes") {

				var store = grid.getStore();

				Ext.Ajax.request({
					url : Ext.ctx + "/adPosition/delete",
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
		var formPanel = button.up("adPositionform");
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

	onDelete : function(button) {
		var grid = button.up("adPositionlist");
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
						url : Ext.ctx + "/adPosition/delete",
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