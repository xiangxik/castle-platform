Ext.define("app.view.article.ArticleList", {
	extend : "Ext.grid.Panel",
	alias : "widget.articlelist",
	requires : [ "app.view.article.ArticleController", "app.view.article.ArticleModel", "app.ux.grid.QueryPanel" ],
	title : "文章列表",
	controller : "article",
	viewModel : "article",
	columns : [ {
		xtype : "rownumberer"
	}, {
		text : "标题",
		dataIndex : "title"
	}, {
		text : "文章分类",
		dataIndex : "articleCategoryName"
	}, {
		text : "作者",
		dataIndex : "author"
	}, {
		text : "点击数",
		dataIndex : "hits"
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
		text : "发布",
		dataIndex : "publication",
		xtype : "booleancolumn",
		width : 80,
		align : "center",
		trueText : "是",
		falseText : "否"
	}, {
		text : "置顶",
		dataIndex : "stick",
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
			fieldLabel : "标题",
			xtype : "textfield",
			name : "title"
		}, {
			fieldLabel : "作者",
			xtype : "textfield",
			name : "author"
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