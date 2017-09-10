(function($) {
	"use strict";
	var namespace = ".rs.jquery.uploadImage";

	function init() {
		this.element.trigger("initialize" + namespace);

		var $element = this.element;
		var bar = $element.next(".uk-progress")[0];
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
					$element.find("img").attr("src", result.urls);
					$element.find("input[type=hidden]").val(result.urls);
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
			$element.find("img").attr("src", "#");
			$element.find("input[type=hidden]").val("");

			bar.max = 0;
			bar.value = 100;
		});

		this.element.trigger("initialized" + namespace);
	}

	var UploadImage = function(element, options) {
		this.element = $(element);
		this.options = $.extend(true, {}, UploadImage.defaults, this.element.data(), options);
	};

	UploadImage.defaults = {};

	$.fn.uploadImage = function(option) {
		var args = Array.prototype.slice.call(arguments, 1), returnValue = null, elements = this.each(function(index) {
			var $this = $(this), instance = $this.data(namespace), options = typeof option === "object" && option;

			if (!instance && option === "destroy") {
				return;
			}
			if (!instance) {
				$this.data(namespace, (instance = new UploadImage(this, options)));
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