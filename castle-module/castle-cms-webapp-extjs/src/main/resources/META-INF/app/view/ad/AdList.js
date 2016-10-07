Ext.define("app.view.ad.AdList", {
	extend : "Ext.grid.Panel",
	alias : "widget.adlist",
	requires : [ "app.view.ad.AdController", "app.view.ad.AdModel", "app.ux.grid.QueryPanel" ],
	title : "广告列表",
	controller : "ad",
	viewModel : "ad",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "标题",
		dataIndex : "title"
	}, {
		text : "广告位",
		dataIndex : "adPositionName"
	}, {
		text : "类型",
		dataIndex : "type"
	}, {
		text : "开始时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日 H:i:s',
		dataIndex : "beginDate",
		width : 180
	}, {
		text : "结束时间",
		xtype : 'datecolumn',
		format : 'Y年m月d日 H:i:s',
		dataIndex : "endDate",
		width : 180
	}, {
		text : "排序号",
		dataIndex : "sortNo"
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
			fieldLabel : "标题",
			xtype : "textfield",
			name : "title"
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