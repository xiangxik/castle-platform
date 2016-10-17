Ext.define("app.view.role.RoleMenu", {
	extend : "Ext.form.Panel",
	alias : "widget.rolemenu",
	requires : [ "app.view.role.RoleController", "app.view.role.RoleModel" ],
	controller : "role",
	viewModel : "role",
	url : Ext.ctx + "/role/menu",
	bodyPadding : 5,
	border : false,
	frame : false,
	scrollable : true,
	layout : "fit",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	listeners : {
		beforerender : function(form, opts) {
			var role = form.getRecord();
			form.add({
				xtype : "treepanel",
				border : false,
				frame : false,
				layout : "fit",
				rootVisible : false,
				store : {
					proxy : {
						type : "ajax",
						method : "GET",
						url : Ext.ctx + "/role/menu",
						extraParams : {
							id : role ? role.get("id") : ""
						},
						reader : {
							type : "json"
						}
					}
				},
				listeners : {
					checkchange : function(node, checked) {
						if (checked == true) {
							node.checked = checked;
							pNode = node.parentNode;

							for (; pNode != null; pNode = pNode.parentNode) {
								pNode.set("checked", true);
							}
						}

						if (!node.get("leaf") && !checked) {
							node.cascade(function(node) {
								node.set("checked", false);
							});
						}
					}
				}
			});

		}
	},
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onMenuSave"
	} ]
});