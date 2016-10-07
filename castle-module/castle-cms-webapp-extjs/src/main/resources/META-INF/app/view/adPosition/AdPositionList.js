Ext.define("app.view.adPosition.AdPositionList", {
	extend : "Ext.grid.Panel",
	alias : "widget.adPositionlist",
	requires : [ "app.view.adPosition.AdPositionController", "app.view.adPosition.AdPositionModel", "app.ux.grid.QueryPanel" ],
	title : "广告位列表",
	controller : "adPosition",
	viewModel : "adPosition",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "代号",
		dataIndex : "code"
	}, {
		text : "宽度",
		dataIndex : "width"
	}, {
		text : "高度",
		dataIndex : "height"
	}, {
		text : "描述",
		dataIndex : "description"
	}, {
		text : "最后修改人",
		dataIndex : "lastModifiedByName"
	}, {
		text : "最后修改时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日 H:i:s',
		dataIndex : "lastModifiedDate",
		width : 180
	}, {
		text : "发布",
		dataIndex : "list",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "操作",
		xtype : "actioncolumn",
		items : [ {
			iconCls : "fa fa-pencil-square actionColumnIcon",
			tooltip : "编辑",
			handler : "onRowEdit"
		}, {
			iconCls : "fa fa-minus-square actionColumnIcon",
			tooltip : "删除",
			handler : "onRowDelete"
		} ]
	} ],
	tbar : [ {
		xtype : "button",
		text : "添加",
		iconCls : "fa fa-plus-circle",
		handler : "onAdd"
	}, {
		xtype : "button",
		text : "删除",
		iconCls : "fa fa-minus-circle",
		handler : "onDelete"
	} ],
	selType : "checkboxmodel",
	multiSelect : true,
	bind : {
		store : "{list}"
	},
	dockedItems : [ {
		xtype : "querypanel",
		dock : "top",
		bind : {
			store : "{list}"
		},
		filterItems : [ {
			fieldLabel : "名称",
			xtype : "textfield",
			name : "name"
		} ]
	}, {
		xtype : "pagingtoolbar",
		dock : "bottom",
		displayInfo : true,
		bind : {
			store : "{list}"
		}
	} ]
});