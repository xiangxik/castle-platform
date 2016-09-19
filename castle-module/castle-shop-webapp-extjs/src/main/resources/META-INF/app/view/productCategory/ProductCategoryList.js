Ext.define("app.view.productCategory.ProductCategoryList", {
	extend : "Ext.tree.Panel",
	alias : "widget.productCategory",
	requires : [ "app.view.productCategory.ProductCategoryController", "app.view.productCategory.ProductCategoryModel" ],
	controller : "productCategory",
	viewModel : "productCategory",
	rootVisible : false,
	forceFit : true,
	bind : {
		store : "{tree}"
	},
	columns : [ {
		xtype : "treecolumn",
		text : "名称",
		dataIndex : "text",
		flex : 4
	}, {
		text : "排序号",
		dataIndex : "sortNo",
		flex : 1
	}, {
		text : "操作",
		xtype : "actioncolumn",
		flex : 1,
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
	} ]
});