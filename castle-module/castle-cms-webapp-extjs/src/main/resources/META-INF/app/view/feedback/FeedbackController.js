Ext.define("app.view.feedback.FeedbackController", {
	extend : "Ext.app.ViewController",
	alias : "controller.feedback",
	mixins : {
		center : "app.view.main.CenterController"
	},

	onItemClick : function(grid, item) {

	},

	onRowDelete : function(grid, rowIndex, colIndex) {
		var menu = grid.getStore().getAt(rowIndex);
		Ext.Msg.confirm("提示", "您确定要删除【" + menu.get("name") + "】？", function(choice) {
			if (choice === "yes") {

				var store = grid.getStore();

				Ext.Ajax.request({
					url : Ext.ctx + "/feedback/delete",
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

	onDelete : function(button) {
		var grid = button.up("feedbacklist");
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
						url : Ext.ctx + "/feedback/delete",
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