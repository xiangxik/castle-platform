Ext.define("app.module.ModuleList", {
	extend : "Ext.grid.Panel",
	mixins : {
		module : "app.module.ModuleController"
	},
	requires : [ "app.ux.grid.QueryPanel" ],
	modelFields : [],
	filterItems : [],
	gridColumns : [],
	formItems : [],
	listUrl : "",
	saveUrl : "",
	deleteUrl : "",
	formView : "",
	domainName : "",
	domainNameProperty : "name",
	code : "",
	iconCls : "",
	showDefaultActionColumn : true,
	showDefaultToolbar : true,
	inited: false,
	constructor : function() {
		var me = this;

		me.columns = me.gridColumns;
		me.gridColumns = null;
		if (me.showDefaultActionColumn) {
			me.columns = me.columns.concat([{
				text : "操作",
				xtype : "actioncolumn",
				items : [ {
					iconCls : "fa fa-pencil-square actionColumnIcon",
					tooltip : "编辑",
					handler : me.onRowEdit
				}, {
					iconCls : "fa fa-minus-square actionColumnIcon",
					tooltip : "删除",
					handler : me.onRowDelete
				} ]
			}]);
		}

		if(!me.inited) {
			me.inited = true;
		}
		if (!me.modelFields) {
			me.modelFields = [];
		}
		me.modelFields.push({
			name : "id",
			type : "int"
		});
		var store = Ext.create("Ext.data.Store", {
			fields : me.modelFields,
			remoteFilter : true,
			proxy : {
				type : "ajax",
				url : me.listUrl,
				reader : {
					type : "json",
					rootProperty : "rows"
				}
			},
			autoLoad : true
		});

		this.callParent(arguments);

		me.setStore(store);

		if (me.filterItems && me.filterItems.length > 0) {
			me.addDocked({
				xtype : "querypanel",
				dock : "top",
				store : store,
				filterItems : me.filterItems
			});
		}

		if (me.showDefaultToolbar) {
			me.addDocked({
				xtype : 'toolbar',
				ui : "footer",
				dock : 'top',
				items : [ {
					xtype : "button",
					text : "添加",
					iconCls : "fa fa-plus-circle",
					handler : me.onGridAdd
				}, {
					xtype : "button",
					text : "删除",
					iconCls : "fa fa-minus-circle",
					handler : me.onGridDelete
				} ]
			});
		}

		me.addDocked({
			xtype : "pagingtoolbar",
			dock : "bottom",
			displayInfo : true,
			store : store
		});

	},
	selType : "checkboxmodel",
	multiSelect : true
});