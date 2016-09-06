Ext.define("app.ux.form.KindEditor", {
	extend : "Ext.form.field.TextArea",
	alias : "widget.kindeditor",
	liquidLayout : false,
	listeners : {
		"boxready" : function(t) {
			var me = this;

			me.editor = KindEditor.create("#" + me.getInputId(), {
				height : t.getHeight() - 18,
				width : t.getWidth() - t.labelEl.getWidth() - 2,
				basePath : Ext.ctx + "/assets/kindeditor-4.1.10/",
				uploadJson : "",
				fileManagerJson : "",
				resizeType : 0,
				wellFormatMode : true,
				newlineTag : "br",
				allowFileManager : true,
				allowPreviewEmotions : true,
				allowImageUpdate : true,
				afterCreate : function() {
					me.kindEditorIsReady = true;
				},
				items : [ 'source', '|', 'undo', 'redo', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', '|', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'bold', 'italic', 'underline', 'lineheight', '|', 'image', 'multiimage', 'table', 'emoticons', 'link', 'unlink', 'fullscreen' ]
			})
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