Ext.define("app.view.menu.MenuController", {
	extend : "Ext.app.ViewController",
	alias : "controller.menu",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onItemClick : function(tree, item) {
		
	},

	onAdd : function(button) {
		var code = "menuform";
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.menu.MenuForm", {
				id : code,
				closable : true,
				title : "新建菜单",
				iconCls : "Applicationsidelist"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},
	
	onRowEdit : function(tree, rowIndex, colIndex) {
		var item = tree.getStore().getAt(rowIndex);
		var code = "menuform" + item.id;
		var tab = this.tabOnCenter(code);
		if (!tab) {
			var view = Ext.create("app.view.menu.MenuForm", {
				id : code,
				closable : true,
				title : "编辑菜单【" + item.get("text") + "】",
				iconCls : "Applicationsidelist"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},
	
	onRowDelete : function(tree, rowIndex, colIndex) {
		var menu = tree.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + menu.get("text") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("tree");

				Ext.Ajax.request({
					url : Ext.ctx + "/menu/delete",
					params : {
						id : menu.id
					},
					method : "POST",
					success : function(response) {
						Ext.Msg.info("提示", "操作成功");
						store.remove(menu);
					}
				});
			}
		}, this);
	},

	onFormSave : function(button) {
		var formPanel = button.up("menuform");
		var form = formPanel.getForm();
		var store = this.getViewModel().getStore("tree");

		var me = this;
		if (form.isValid()) {
			form.submit({
				success : function(form, action) {
					Ext.Msg.info("提示", "操作成功");
					store.reload();
					me.closeTab(formPanel);
				},
				failure : function(form, action) {
					Ext.Msg.error("提示", "操作失败");
				}
			});
		}
	},

	onDelete : function(button) {
		var menu = button.up("menu");
		var selection = menuTree.getSelection();
		if (selection.length == 0) {
			Ext.Msg.alert("提示", "您最少要选择一条数据");
		} else {
			var allLeaf = true;
			var ids = [];
			Ext.each(selection, function(record, index, array) {
				if (!record.isLeaf()) {
					allLeaf = false;
				}
				ids.push(record.id);
			});
			if (!allLeaf) {
				Ext.Msg.alert("提示", "不能删除包含有下级的数据，请先把其下级删除");
			} else {
				Ext.Msg.confirm("提示", "您确定要删除所选菜单？", function(choice) {
					if (choice === "yes") {
						var store = this.getViewModel().getStore("tree");
						Ext.Ajax.request({
							url : Ext.ctx + "/menu/delete",
							params : {
								ids : ids
							},
							method : "POST",
							timeout : 10000,
							success : function(response) {
								Ext.Msg.info("提示", "操作成功");
								store.reload();
							}
						});
					}
				}, this);
			}
		}
	}
});