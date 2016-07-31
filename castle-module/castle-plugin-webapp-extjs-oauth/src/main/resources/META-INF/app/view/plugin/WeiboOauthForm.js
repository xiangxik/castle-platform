Ext.define("app.view.plugin.WeiboOauthForm", {
	extend : "Ext.form.Panel",
	requires : [ "app.view.plugin.OauthPluginController", "app.view.plugin.OauthPluginModel" ],
	controller : "oauthplugin",
	viewModel : "oauthplugin",
	bodyPadding : 5,
	url : Ext.ctx + "/oauth/weibo/update",
	border : false,
	frame : false,
	scrollable : true,
	layout : "auto",
	fieldDefaults : {
		labelAlign : "left",
		labelWidth : 80
	},
	defaultType : "textfield",
	items : [ {
		xtype : "hiddenfield",
		name : "id"
	}, {
		fieldLabel : "排序号",
		xtype : "numberfield",
		allowDecimals : false,
		name : "sortNo"
	}, , {
		fieldLabel : "启用",
		name : "isEnabled",
		xtype : "checkbox"
	} ],
	listeners : {
		beforerender : function(form, opts) {
			var record = form.getRecord();
			var attributes = record.get("attributes");
			form.insert(1, [ {
				fieldLabel : "客户端ID",
				name : "attributes['client_id']",
				allowBlank : false,
				value : attributes.client_id
			}, {
				fieldLabel : "客户端密钥",
				name : "attributes['client_secret']",
				allowBlank : false,
				value : attributes.client_secret
			}, {
				fieldLabel : "图标",
				name : "attributes['icon']",
				value : attributes.icon
			}, {
				fieldLabel : "描述",
				xtype : "textareafield",
				name : "attributes['description']",
				maxLength : 250,
				value : attributes.description
			} ]);
		}
	},
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : "onFormSave"
	} ]
});