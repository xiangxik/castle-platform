Ext.define("app.ux.form.KindEditor", {
	extend : "Ext.form.field.TextArea",
	alias : "widget.kindeditor",
	liquidLayout : false,
	editorConfig : {},
	listeners : {
		"boxready" : function(t) {
			var me = this;

			me.editor = KindEditor.create("#" + me.getInputId(), Ext.apply(me.editorConfig, {
				height : t.getHeight() - 18,
				width : t.getWidth() - t.labelEl.getWidth() - 2,
				basePath : Ext.ctx + "/assets/kindeditor/",
				uploadJson : Ext.ctx + "/extjs/kindeditor/upload",
				fileManagerJson : Ext.ctx + "/extjs/kindeditor/list",
				allowFileManager : true,
				resizeType : 1,
				wellFormatMode : true,
				newlineTag : "br",
				afterCreate : function() {
					me.kindEditorIsReady = true;
				}
			}));
		},
		"resize" : function(t, w, h) {
			if (this.editor) {
				this.editor.resize(w - t.labelEl.getWidth() - 2, h - 18);
			}
		}
	},
	setValue : function(value) {
		var me = this;
		if (!me.editor) {
			me.setRawValue(me.valueToRaw(value));
		} else {
			me.editor.html(value.toString());
		}
		me.callParent(arguments);
		return me.mixins.field.setValue.call(me, value);
	},
	getRawValue : function() {
		var me = this;
		if (me.kindEditorIsReady) {
			me.editor.sync();
		}
		v = (me.inputEl ? me.inputEl.getValue() : (me.rawValue || ""));
		me.rawValue = v;
		return v;
	},
	destoryKindEditor : function() {
		var me = this;
		if (me.rendered) {
			try {
				me.editor.remove();
				var dom = document.getElementById(me.id);
				if (dom) {
					dom.parentNode.removeChild(dom);
				}
				me.editor = null;
			} catch (e) {
			}
		}
	},
	onDestory : function() {
		var me = this;
		me.destoryKindEditor();
		me.callParent(arguments);
	}

});