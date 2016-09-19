Ext.define("app.ux.form.KindUpload", {
	extend : "Ext.form.field.Text",
	alias : "widget.kindupload",
	emptyText : undefined,

	needArrowKeys : false,
	triggers : {
		filebutton : {
			type : 'component',
			hideOnReadOnly : false,
			preventMouseDown : false
		}
	},
	buttonText : 'Browse...',

	buttonOnly : false,

	buttonMargin : 3,

	clearOnSubmit : true,

	extraFieldBodyCls : Ext.baseCSSPrefix + 'form-file-wrap',

	inputCls : Ext.baseCSSPrefix + 'form-text-file',

	readOnly : false,

	editable : true,
	applyTriggers : function(triggers) {
		var me = this, triggerCfg = (triggers || {}).filebutton;

		if (triggerCfg) {
			triggerCfg.component = Ext.apply({
				xtype : 'button',
				ownerCt : me,
				id : me.id + '-button',
				ui : me.ui,
				disabled : me.disabled,
				tabIndex : me.tabIndex,
				text : me.buttonText,
				style : me.buttonOnly ? '' : me.getButtonMarginProp() + me.buttonMargin + 'px',
				accept : me.accept
			}, me.buttonConfig);

			return me.callParent([ triggers ]);
		} else {
			Ext.raise(me.$className + ' requires a valid trigger config containing "filebutton" specification');
		}
	},
	onRender : function() {
		var me = this, inputEl, button, buttonEl, trigger;

		me.callParent(arguments);

		inputEl = me.inputEl;

		trigger = me.getTrigger('filebutton');
		button = me.button = trigger.component;
		me.fileInputEl = button.fileInputEl;
		buttonEl = button.el;

		if (me.buttonOnly) {
			me.inputWrap.setDisplayed(false);
			me.shrinkWrap = 3;
		}

		trigger.el.setWidth(buttonEl.getWidth() + buttonEl.getMargin('lr'));
		if (Ext.isIE) {
			me.button.getEl().repaint();
		}
		
		var inputElId = inputEl.getId();
		var buttonId = buttonEl.getId();
		
		var editor = KindEditor.editor({
			basePath : Ext.ctx + "/assets/kindeditor/",
			uploadJson : Ext.ctx + "/extjs/kindeditor/upload",
			fileManagerJson : Ext.ctx + "/extjs/kindeditor/list",
			allowFileManager : true
		});
		KindEditor("#"+buttonId).click(function() {
			editor.loadPlugin('insertfile', function() {
				editor.plugin.fileDialog({
					fileUrl : KindEditor("#"+inputElId).val(),
					clickFn : function(url, title) {
						KindEditor("#"+inputElId).val(url);
						editor.hideDialog();
					}
				});
			});
		});
	},
	onShow : function() {
		this.callParent();
		this.button.updateLayout();
	},

	onDisable : function() {
		this.callParent();
		this.button.disable();
	},

	onEnable : function() {
		this.callParent();
		this.button.enable();
	},

	getButtonMarginProp : function() {
		return this.getInherited().rtl ? 'margin-right:' : 'margin-left:';
	}
});