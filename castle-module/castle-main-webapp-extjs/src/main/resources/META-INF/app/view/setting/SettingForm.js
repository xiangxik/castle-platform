Ext.define("app.view.setting.SettingForm", {
	extend : "Ext.form.Panel",
	alias : "widget.settingform",
	requires : [ "app.model.Setting", "app.ux.form.KindEditor", "app.ux.form.KindUpload" ],
	url : Ext.ctx + "/setting/save",
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
		fieldLabel : "网站名称",
		name : "siteName",
		allowBlank : false
	}, {
		fieldLabel : "Logo",
		name : "logo",
		xtype : "kindupload"
	}, {
		fieldLabel : "后台登录窗口顶图",
		name : "adminLoginBanner",
		xtype : "kindupload"
	}, {
		xtype : "kindeditor",
		fieldLabel : "介绍",
		name : "introduction",
		newlineTag : "p"
	}, {
		fieldLabel : "联系地址",
		name : "address"
	}, {
		fieldLabel : "联系人",
		name : "contact"
	}, {
		fieldLabel : "联系电话1",
		name : "phone1"
	}, {
		fieldLabel : "联系电话2",
		name : "phone2"
	}, {
		fieldLabel : "邮政编码",
		name : "zipCode"
	}, {
		fieldLabel : "邮箱",
		name : "email",
		vtype : "email"
	}, {
		fieldLabel : "备案编号",
		name : "certtext"
	}, {
		xtype : "checkbox",
		fieldLabel : "网站是否开启",
		name : "isSiteEnabled"
	}, {
		fieldLabel : "网站关闭消息",
		xtype : "textarea",
		name : "siteCloseMessage"
	} ],
	buttonAlign : "left",
	buttons : [ {
		text : "保存",
		formBind : true,
		handler : function(button) {
			var settingform = button.up("settingform");
			var form = settingform.getForm();

			var me = this;
			if (form.isValid()) {
				form.submit({
					success : function(form, action) {
						Ext.toast("操作成功", null, "t");
					},
					failure : function(form, action) {
						Ext.toast("操作失败", null, "t");
					}
				});
			}
		}
	} ],
	initComponent : function() {
		this.callParent(arguments);

		var me = this;
		app.model.Setting.load(1, {
			success : function(record) {
				me.loadRecord(record);
			}
		});
	}
});