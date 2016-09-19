Ext.define("app.view.productCategory.ProductCategoryController", {
	extend : "Ext.app.ViewController",
	alias : "controller.productCategory",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onItemClick : function(tree, item) {
		
	},

	onAdd : function(button) {
		var code = "productcategoryform";
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.productCategory.ProductCategoryForm", {
				id : code,
				closable : true,
				title : "新建文章分类",
				iconCls : "fa fa-user"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},
	
	onRowEdit : function(tree, rowIndex, colIndex) {
		var item = tree.getStore().getAt(rowIndex);
		var code = "productcategoryform" + item.id;
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.productCategory.ProductCategoryForm", {
				id : code,
				closable : true,
				title : "编辑文章分类【" + item.get("text") + "】",
				iconCls : "fa fa-user"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},
	
	onRowDelete : function(tree, rowIndex, colIndex) {
		var menu = tree.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + menu.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("tree");

				Ext.Ajax.request({
					url : Ext.ctx + "/productCategory/delete",
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
		var formPanel = button.up("productcategoryform");
		var form = formPanel.getForm();
		var store = this.getViewModel().getStore("tree");

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
		var productCategoryTree = button.up("productCategory");
		var selection = productCategoryTree.getSelection();
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
				Ext.Msg.confirm("提示", "您确定要删除所选文章分类？", function(choice) {
					if (choice === "yes") {
						var store = this.getViewModel().getStore("tree");
						Ext.Ajax.request({
							url : Ext.ctx + "/productCategory/delete",
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