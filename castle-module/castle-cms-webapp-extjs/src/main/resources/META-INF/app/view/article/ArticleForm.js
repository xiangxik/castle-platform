Ext.define("app.view.article.ArticleForm", {
	extend : "Ext.form.Panel",
	alias : "widget.articleform",
	requires : [ "app.view.article.ArticleController", "app.view.article.ArticleModel", "Ext.ux.TreePicker", "app.ux.form.KindEditor" ],
	controller : "article",
	viewModel : "article",
	url : Ext.ctx + "/article/save",
	bodyPadding : 5,
	border : false,
	frame : false,
	scrollable : true,
	layout : "anchor",
	defaults : {
		anchor : "90%"
	},
	fieldDefaults : {
		labelAlign : "right",
		labelWidth : 120
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : "标题",
		name : "title",
		allowBlank : false
	}, {
		fieldLabel : "文章分类",
		name : "articleCategory",
		xtype : "treepicker",
		displayField : "name",
		valueField : "id",
		rootVisible : false,
		allowBlank : false,
		store : Ext.create("app.store.ArticleCategories", {
			root : {
				name : "文章分类",
				expanded : true
			}
		})
	}, {
		fieldLabel : "作者",
		name : "author"
	}, {
		xtype : "fieldcontainer",
		id : "tagContainer",
		fieldLabel : "标签",
		layout : {
			type : "hbox",
			align : "left"
		},
		defaultType : "checkbox",
		items : []
	}, {
		xtype : "checkboxgroup",
		fieldLabel : "设置",
		layout : {
			type : "hbox",
			align : "left"
		},
		defaultType : "checkbox",
		items : [ {
			boxLabel : "是否发布",
			hideLabel : true,
			name : "publication"
		}, {
			boxLabel : "是否置顶",
			hideLabel : true,
			name : "stick"
		} ]
	}, {
		xtype : "kindeditor",
		fieldLabel : "内容",
		name : "content",
		height : 140,
		width : 670
	}, {
		fieldLabel : "页面标题",
		name : "seoTitle"
	}, {
		fieldLabel : "页面关键字",
		name : "seoKeywords"
	}, {
		fieldLabel : "页面描述",
		name : "seoDescription"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ],
	listeners : {
		afterRender : function(form, opts) {
			var tagContainer = form.getComponent("tagContainer");
			var tagStore = Ext.create("app.store.ArticleTags", {
				autoLoad : false
			});
			var r = form.getRecord();
			var tags = r == null ? null : r.get("tags");
			tagStore.load({
				callback : function(records, operation, success) {
					Ext.each(records, function(record, index, data) {
						var inputValue = record.get("id");
						var checked = false;
						if (tags) {
							for ( var i in tags) {
								if (tags[i].id == inputValue) {
									checked = true;
									break;
								}
							}
						}

						tagContainer.add({
							boxLabel : record.get("name"),
							hideLabel : true,
							inputValue : inputValue,
							checked : checked,
							name : "tags"
						});
					});
				},
				scope : this
			});

		}
	}
});