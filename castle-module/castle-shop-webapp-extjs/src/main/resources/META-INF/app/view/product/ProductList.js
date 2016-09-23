Ext.define("app.view.product.ProductList", {
	extend : "Ext.grid.Panel",
	alias : "widget.productlist",
	requires : [ "app.view.product.ProductController", "app.view.product.ProductModel", "app.ux.grid.QueryPanel" ],
	title : "产品列表",
	controller : "product",
	viewModel : "product",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "编号",
		dataIndex : "sn"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "分类",
		dataIndex : "productCategoryName"
	}, {
		text : "品牌",
		dataIndex : "brandName"
	}, {
		text : "单位",
		dataIndex : "unit"
	}, {
		text : "净重",
		dataIndex : "weight"
	}, {
		text : "包装",
		dataIndex : "pcs"
	}, {
		text : "空罐规格",
		dataIndex : "canSize"
	}, {
		text : "纸箱规格",
		dataIndex : "cartonSize"
	}, {
		text : "列出",
		dataIndex : "list",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "置顶",
		dataIndex : "top",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "赠品",
		dataIndex : "gift",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "OEM",
		dataIndex : "oem",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "备注",
		dataIndex : "memo"
	}, {
		text : "创建人",
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
			fieldLabel : "编号",
			xtype : "textfield",
			name : "sn"
		}, {
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