Ext.define("app.view.product.ProductForm", {
	extend : "Ext.form.Panel",
	alias : "widget.productform",
	requires : [ "app.view.product.ProductController", "app.view.product.ProductModel", "Ext.ux.TreePicker", "app.ux.form.KindEditor", "app.ux.form.KindUpload" ],
	controller : "product",
	viewModel : "product",
	url : Ext.ctx + "/product/save",
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
		fieldLabel : "编号",
		name : "sn",
		allowBlank : false
	}, {
		fieldLabel : "名称",
		name : "name",
		allowBlank : false
	}, {
		fieldLabel : "分类",
		name : "productCategory",
		xtype : "treepicker",
		displayField : "name",
		valueField : "id",
		rootVisible : false,
		allowBlank : false,
		store : Ext.create("app.store.ProductCategories", {
			root : {
				name : "产品分类",
				expanded : true
			}
		})
	}, {
		fieldLabel : "品牌",
		xtype:"combobox",
		name : "brand",
		editable:false,
		displayField:"name",
		valueField:"id",
		emptyText:"--请选择品牌--",
		queryMode:"local",
		allowBlank : false,
		store:Ext.create("app.store.Brands")
	}, {
		fieldLabel : "展示图片",
		name : "image",
		xtype : "kindupload"
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
		fieldLabel : "单位",
		name : "unit"
	}, {
		fieldLabel : "重量",
		name : "weight"
	}, {
		fieldLabel : "空罐规格",
		name : "canSize"
	}, {
		fieldLabel : "纸箱规格",
		name : "cartonSize"
	}, {
		xtype : "checkboxgroup",
		fieldLabel : "设置",
		layout : {
			type : "hbox",
			align : "left"
		},
		defaultType : "checkbox",
		items : [ {
			boxLabel : "是否列出",
			hideLabel : true,
			name : "list"
		}, {
			boxLabel : "是否置顶",
			hideLabel : true,
			name : "top"
		}, {
			boxLabel : "是否赠品",
			hideLabel : true,
			name : "gift"
		} ]
	}, {
		xtype : "kindeditor",
		fieldLabel : "内容",
		name : "introduction"
	}, {
		fieldLabel : "备注",
		name : "memo"
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
			var tagStore = Ext.create("app.store.ProductTags", {
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