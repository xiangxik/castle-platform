Ext.define("app.view.brand.BrandList", {
	extend : "Ext.grid.Panel",
	alias : "widget.producttaglist",
	requires : [ "app.view.brand.BrandController", "app.view.brand.BrandModel", "app.ux.grid.QueryPanel","app.ux.form.KindEditor" ],
	title : "品牌列表",
	controller : "brand",
	viewModel : "brand",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "名称",
		dataIndex : "name"
	}, {
		text : "类型",
		dataIndex : "type",
		renderer:function(value) {
			if(value=="image") {
				return "图像";
			}
			if(value=="text") {
				return "文本";
			}
			return "";
		}
	}, {
		text : "网址",
		dataIndex : "url"
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