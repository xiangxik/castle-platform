(function($) {
	"use strict";

	$
			.extend({
				dialog : function(options) {

					var defaults = {
						height : 400
					};
					options = $.extend(true, defaults, options);

					var $body = $("body");

					var id = "modal_" + new Date().getTime();
					var $dialog = $("<div id=\""
							+ id
							+ "\" uk-modal class=\"uk-modal-container\">\
								<div class=\"uk-modal-dialog\">\
									<button class=\"uk-modal-close-default\" type=\"button\" uk-close></button>\
									<div class=\"uk-modal-header\">\
										<h2 class=\"uk-modal-title\"></h2>\
									</div>\
									<div class=\"uk-modal-body\" uk-overflow-auto style=\"padding: 0\"></div>\
									<div class=\"uk-modal-footer uk-text-right\">\
										<button class=\"uk-button uk-button-default uk-modal-close\" type=\"button\">取消</button>\
										<button class=\"uk-button uk-button-primary\" type=\"button\">提交</button>\
									</div>\
								</div>\
							</div>");
					$body.append($dialog);
					var modal = UIkit.modal("#" + id);

					$dialog.find(".uk-modal-title").text(options.title);
					$dialog.find(".uk-modal-body").css("height", options.height).append("<iframe id=\"if_" + id + "\" src=\"" + options.url + "\" style=\"width: 100%;height: 100%;\"></iframe>");

					$dialog.find(".uk-modal-footer > .uk-button-primary").on("click", function() {
						var frameContentWindow = document.getElementById("if_" + id).contentWindow;
						if (options.onSuccess) {
							options.onSuccess(frameContentWindow, modal);
						}
					});
					return modal;
				}
			});

})(window.jQuery);