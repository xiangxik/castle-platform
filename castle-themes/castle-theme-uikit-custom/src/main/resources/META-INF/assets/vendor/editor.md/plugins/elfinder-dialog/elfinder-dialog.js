(function() {

	var factory = function(exports) {

		var $ = jQuery;
		var pluginName = "elfinder-dialog";

		exports.fn.elfinderDialog = function() {
			var _this = this;
			var lang = this.lang;
			var editor = this.editor;
			var settings = this.settings;
			var path = settings.pluginPath + pluginName + "/";
			var classPrefix = this.classPrefix;
			var dialogName = classPrefix + pluginName, dialog;
			var dialogLang = {
				title : "选择文件"
			};

			if (editor.find("." + dialogName).length < 1) {
				var dialogContent = "<div class=\"elfinder-body\" style=\"height:460px;margin:-20px;overflow:auto;font-size:14px;border-bottom:1px solid #ddd;padding: 0;\"></div>";

				dialog = this.createDialog({
					name : dialogName,
					title : dialogLang.title,
					width : 900,
					height : 500,
					mask : settings.dialogShowMask,
					drag : settings.dialogDraggable,
					content : dialogContent,
					lockScreen : settings.dialogLockScreen,
					maskStyle : {
						opacity : settings.dialogMaskOpacity,
						backgroundColor : settings.dialogMaskBgColor
					}
				});
			}

			dialog = editor.find("." + dialogName);

			this.dialogShowMask(dialog);
			this.dialogLockScreen();
			dialog.show();

			var helpContent = dialog.find(".elfinder-body");

			if (helpContent.html() === "") {
				helpContent.html("<iframe src=\"" + path
						+ "../../../../../bower_components/elfinder/elfinder_dialog.html\" style=\"width:100%;height:100%\"></iframe>");
			}
		};

	};

	// CommonJS/Node.js
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
		module.exports = factory;
	} else if (typeof define === "function") // AMD/CMD/Sea.js
	{
		if (define.amd) { // for Require.js

			define([ "editormd" ], function(editormd) {
				factory(editormd);
			});

		} else { // for Sea.js
			define(function(require) {
				var editormd = require("./../../editormd");
				factory(editormd);
			});
		}
	} else {
		factory(window.editormd);
	}

})();
