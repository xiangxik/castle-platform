Ext.define('app.ux.grid.QueryPanel', {
	extend : "Ext.form.Panel",
	alias : "widget.querypanel",
	border : false,
	frame : false,
	layout : "fit",
	bodyPadding : "5 5 0 5",
	fieldDefaults : {
		labelAlign : "right",
		labelWidth : 80
	},
	items : [ {
		xtype : "fieldset",
		title : "查询面板",
		layout : "column",
		collapsible : true,
		defaults : {
			margin : "0 0 5 0"
		}
	} ],
	getStore : function() {
		return this.store;
	},
	setStore : function(store) {
		this.store = store;
	},
	listeners : {
		beforerender : function(form, opts) {
			var fieldset = form.child("fieldset");
			fieldset.add(this.filterItems);
			fieldset.add({
				xtype : "fieldcontainer",
				layout : "hbox",
				margin : "0 0 0 8",
				defaults : {
					margin : "0 0 0 8"
				},
				items : [ {
					text : "搜索",
					iconCls : "fa fa-search",
					margin : "6 0 6 64",
					xtype : "button",
					handler : function() {
						var formPanel = this.up("form");
						var form = formPanel.getForm();
						var store = formPanel.getStore();

						if (form.isValid()) {

							form.getFields().each(function(item, index, len) {
								if (item.getValue() && item.getValue() != "" && (Ext.getClassName(item) != "Ext.ux.TreePicker" || item.getValue() != "root")) {
									this.addFilter(new Ext.util.Filter({
										id : item.getName(),
										property : item.getName(),
										value : item.getValue()
									}), true);
								} else {
									this.removeFilter(item.getName(), true);
								}
							}, store);

							store.load();
						}
					}
				}, {
					text : "重置",
					iconCls : "fa fa-undo",
					margin : "6 0 6 8",
					xtype : "button",
					handler : function() {
						var formPanel = this.up("form");
						var form = formPanel.getForm();
						var store = formPanel.getStore();

						form.reset();
						form.getFields().each(function(item, index, len) {
							this.removeFilter(item.getName(), true);
						}, store);
					}
				} ]
			});
		}
	}
});