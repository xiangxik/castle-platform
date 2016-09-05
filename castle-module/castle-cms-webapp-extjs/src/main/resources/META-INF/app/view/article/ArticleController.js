Ext.define("app.view.article.ArticleController", {
	extend : "Ext.app.ViewController",
	alias : "controller.article",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onItemClick : function(tree, item) {
		
	},

	onAdd : function(button) {
		var code = "articleform";
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.article.ArticleForm", {
				id : code,
				closable : true,
				title : "新建文章",
				iconCls : "fa fa-user"
			});
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},
	
	onRowEdit : function(tree, rowIndex, colIndex) {
		var item = tree.getStore().getAt(rowIndex);
		var code = "articleform" + item.id;
		var tab = this.findTabByCode(code);
		if (!tab) {
			var view = Ext.create("app.view.article.ArticleForm", {
				id : code,
				closable : true,
				title : "编辑文章【" + item.get("title") + "】",
				iconCls : "fa fa-user"
			});
			view.loadRecord(item);
			tab = this.addViewToCenter(code, view);
		}
		this.activeTab(tab);
	},
	
	onRowDelete : function(tree, rowIndex, colIndex) {
		var menu = tree.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + menu.get("title") + "】？", function(choice) {
			if (choice === "yes") {

				var store = this.getViewModel().getStore("tree");

				Ext.Ajax.request({
					url : Ext.ctx + "/article/delete",
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
		var formPanel = button.up("articleform");
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
		var articleTree = button.up("article");
		var selection = articleTree.getSelection();
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
							url : Ext.ctx + "/article/delete",
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