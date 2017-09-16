(function($) {
	"use strict";

	var namespace = ".rs.jquery.pageedit";

	function init() {
		this.element.trigger("initialize" + namespace);

		this.$form = this.element.find("#" + this.options.formId);
		this.$backButton = this.element.find("#" + this.options.backButtonId);

		initForm.call(this);

		this.element.trigger("initialized" + namespace);

		return this;
	}

	function initForm() {
		var that = this;

		this.$parsleyForm = that.$form.parsley(that.options.validateOptions);

		if (that.options.onFormSubmit) {
			that.$parsleyForm.on('form:submit', function(source) {
				return that.options.onFormSubmit(source, that.$form);
			});
		} else {
			that.$parsleyForm.on('form:submit', function() {
				$.post(that.$form.attr('action'), that.$form.serialize(), function(result) {
					if (result.success) {
						location.href = that.options.baseUrl;
					} else {
						UIkit.notification(result.msg);
					}
				}, "json");
				return false;
			});
		}

		if (that.options.onFormValidatedSuccess) {
			that.$parsleyForm.on('form:success', that.options.onFormValidatedSuccess);
		}

		if (this.$backButton) {
			this.$backButton.on("click", function() {
				location.href = that.options.baseUrl;
			});
		}
	}
	
	var PageEdit = function(element, options) {
		this.element = $(element);
		this.options = $.extend(true, {}, PageEdit.defaults, this.element.data(), options);
	};

	PageEdit.defaults = {
		formId : "main-form",
		validateOptions : {},
		backButtonId : "back-button"
	};

	PageEdit.prototype.getParsleyForm = function() {
		return this.$parsleyForm;
	};

	$.fn.pageedit = function(option) {
		var args = Array.prototype.slice.call(arguments, 1), returnValue = null, elements = this.each(function(index) {
			var $this = $(this), instance = $this.data(namespace), options = typeof option === "object" && option;

			if (!instance && option === "destroy") {
				return;
			}
			var initInstance = null;
			if (!instance) {
				$this.data(namespace, (instance = new PageEdit(this, options)));
				initInstance = init.call(instance);
			}
			if (typeof option === "string") {
				if (option.indexOf("get") === 0 && index === 0) {
					returnValue = instance[option].apply(instance, args);
				} else if (option.indexOf("get") !== 0) {
					return instance[option].apply(instance, args);
				}
			}
			return initInstance;
		});
		return (typeof option === "string" && option.indexOf("get") === 0) ? returnValue : elements;
	};

	$.fn.pageedit.Constructor = PageEdit;

})(window.jQuery);