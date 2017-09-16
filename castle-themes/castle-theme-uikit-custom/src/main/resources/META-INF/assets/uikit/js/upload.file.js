(function($) {
	"use strict";
	var namespace = ".rs.jquery.uploadFile";

	function init() {
		this.element.trigger("initialize" + namespace);

		var $element = this.element;
		var bar = $element.next(".uk-progress")[0];
		
		var that = this;

		var initUrl = $element.find("input[type=hidden]").val();
		$element.find(".uk-placeholder").html("<a href=\"" + initUrl + "\">" + initUrl + "</a>");
		UIkit.upload($element, {
			url : this.options.url,
			multiple : false,
			beforeSend : function() {
			},
			beforeAll : function() {
			},
			load : function() {
			},
			error : function() {
			},
			complete : function(resp) {
				var result = resp.responseJSON;
				if (result.success) {
					$element.find(".uk-placeholder").html("<a href=\"" + result.urls + "\">" + result.urls + "</a>");
					$element.find("input[type=hidden]").val(result.urls);
					
					if(that.options.onComplete) {
						that.options.onComplete($element, result);
					}
				} else {
					UIkit.notification(result.msg);
				}
			},
			loadStart : function(e) {
				bar.removeAttribute('hidden');
				bar.max = e.total;
				bar.value = e.loaded;
			},
			progress : function(e) {
				bar.max = e.total;
				bar.value = e.loaded;
			},
			loadEnd : function(e) {
				bar.max = e.total;
				bar.value = e.loaded;
			},
			completeAll : function() {
				setTimeout(function() {
					bar.setAttribute('hidden', 'hidden');
				}, 1000);
			}
		});
		$element.find(".uk-link-clear").on("click", function() {
			$element.find(".uk-placeholder").html("");
			$element.find("input[type=hidden]").val("");

			bar.max = 0;
			bar.value = 100;
		});

		this.element.trigger("initialized" + namespace);
	}

	var UploadFile = function(element, options) {
		this.element = $(element);
		this.options = $.extend(true, {}, UploadFile.defaults, this.element.data(), options);
	};

	UploadFile.defaults = {};

	$.fn.uploadFile = function(option) {
		var args = Array.prototype.slice.call(arguments, 1), returnValue = null, elements = this.each(function(index) {
			var $this = $(this), instance = $this.data(namespace), options = typeof option === "object" && option;

			if (!instance && option === "destroy") {
				return;
			}
			if (!instance) {
				$this.data(namespace, (instance = new UploadFile(this, options)));
				init.call(instance);
			}
			if (typeof option === "string") {
				if (option.indexOf("get") === 0 && index === 0) {
					returnValue = instance[option].apply(instance, args);
				} else if (option.indexOf("get") !== 0) {
					return instance[option].apply(instance, args);
				}
			}
		});
		return (typeof option === "string" && option.indexOf("get") === 0) ? returnValue : elements;
	};

})(window.jQuery);