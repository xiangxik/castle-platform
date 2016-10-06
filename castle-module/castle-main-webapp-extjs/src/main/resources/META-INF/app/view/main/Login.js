Ext.define("app.view.main.Login", {
	extend : "Ext.window.Window",
	xtype : "widget.login",
	title : "登录",
	width : 422,
	height : 260,
	closable : false,
	resizable : false,
	draggable : true,
	modal : true,
	iconCls : "fa fa-lock",
	layout : "border",
	bodyPadding : 0,
	buttonAlign : "center",
	plain : false,
	closeAction : "hide",
	items : [ {
		xtype : "image",
		src : Ext.setting.adminLoginBanner,
		region : "north",
		height : 60
	}, {
		region : "center",
		xtype : "form",
		url : Ext.ctx + "/login",
		border : false,
		layout : 'anchor',
		defaults : {
			anchor : '90%',
			msgTarget : "side"
		},
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 60
		},
		bodyPadding : 5,

		waitMsgTarget : true,
		baseParams : {
			task : "login"
		},
		items : [ {
			xtype : 'textfield',
			name : 'username',
			fieldLabel : '用户名',
			allowBlank : false
		}, {
			xtype : 'textfield',
			name : 'password',
			inputType : 'password',
			fieldLabel : '密码',
			allowBlank : false
		}, {
			xtype : "fieldcontainer",
			fieldLabel : '验证码',
			layout : {
				type : "hbox",
				align : "left"
			},
			allowBlank : false,
			items : [ {
				xtype : "textfield",
				name : "captcha",
				width : 80,
				allowBlank : false
			}, {
				xtype : "image",
				id : "captchaImage",
				height : 24,
				width : 60,
				margin : "0 2 0 2"
			}, {
				xtype : "component",
				html : "<a href='javascript:;' style='line-height:24px;' onclick='onRefreshCaptcha()'>看不清，点击刷新</a>",
			} ]
		} ]
	} ],
	listeners : {
		beforeshow : function(form, options) {
			this.down("textfield[name=password]").reset();
			this.down("textfield[name=captcha]").reset();
		},
		afterRender : function(form, options) {
			onRefreshCaptcha();
		},
		afterlayout : function(form, options) {
			Ext.create("Ext.util.KeyNav", this.el, {
				enter : function() {
					this.lookupController("main").onLoginSubmit(this.down("button#loginButton"))
				},
				scope : this
			});

		}

	},
	buttons : [ {
		id : "loginButton",
		text : "登录",
		handler : "onLoginSubmit"
	}, {
		text : "找回密码"
	} ]
});

function onRefreshCaptcha() {
	document.getElementById("captchaImage").src = Ext.ctx + "/captcha.jpg?_t=" + new Date().getTime();
}