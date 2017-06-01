Ext.define("app.module.ModuleController", {
	extend : "Ext.Mixin",
	mixins : {
		center : "app.view.main.CenterController"
	},
	onGridAdd : function(button) {
		var grid = button.up("grid");
		var code = grid.code + "new";
		var tab = grid.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.module.ModuleForm", {
				id : code,
				closable : true,
				title : "新建" + grid.domainName,
				iconCls : grid.iconCls,
				saveUrl : grid.saveUrl,
				items : grid.formItems
			});
			view.setStore(grid.getStore());
			tab = grid.addViewToCenter(code, view);
		}
		grid.activeTab(tab);
	},
	onGridDelete : function(button) {
		var grid = button.up("grid");
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

					var store = grid.getStore();

					Ext.Ajax.request({
						url : grid.deleteUrl,
						params : {
							ids : ids
						},
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
	onRowEdit : function(table, rowIndex, colIndex) {
		var grid = table.up("grid");
		var item = grid.getStore().getAt(rowIndex);
		var code = grid.code + item.id;
		var tab = grid.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.module.ModuleForm", {
				id : code,
				closable : true,
				title : "编辑" + grid.domainName + "【" + item.get(grid.domainNameProperty) + "】",
				iconCls : grid.iconCls,
				saveUrl : grid.saveUrl,
				items : grid.formItems
			});
			view.loadRecord(item);
			view.setStore(grid.getStore());
			tab = grid.addViewToCenter(code, view);
		}
		grid.activeTab(tab);
	},
	onRowDelete : function(table, rowIndex, colIndex) {
		var grid = table.up("grid");
		var domain = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + domain.get(grid.domainNameProperty) + "】？", function(choice) {
			if (choice === "yes") {

				var store = grid.getStore("list");

				Ext.Ajax.request({
					url : grid.deleteUrl,
					params : {
						id : domain.id
					},
					method : "POST",
					timeout : 10000,
					success : function(response) {
						Ext.toast("操作成功", null, "t");
						store.reload();
					}
				});
			}
		}, grid);
	},
	onFormSave : function(button) {
		var formPanel = button.up("form");
		var form = formPanel.getForm();
		var store = formPanel.getStore();

		var me = this;
		if (form.isValid()) {
			form.submit({
				url : formPanel.saveUrl,
				success : function(form, action) {
					Ext.toast("操作成功", null, "t");
					if (store) {
						store.reload();
					}
					formPanel.closeTab(formPanel);
				},
				failure : function(form, action) {
					Ext.toast("操作失败", null, "t");
				}
			});
		}
	},
	onCloseTab : function(button) {
		var formPanel = button.up("form");
		var form = formPanel.getForm();
		if (form.isDirty()) {
			Ext.Msg.confirm("提示", "表单未保存，是否关闭？", function(choice) {
				if (choice === "yes") {
					formPanel.closeTab(formPanel);
				}
			});
		} else {
			formPanel.closeTab(formPanel);
		}
	}
});