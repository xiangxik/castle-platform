;
(function($, window, undefined) {
	"use strict";

	var namespace = ".rs.jquery.uigrid";
	
	function loadColumns() {
		var that = this, firstHeadRow = this.element.find("thead > tr").first(), sorted = false;

		/* jshint -W018 */
		firstHeadRow.children().each(function() {
			var $this = $(this), data = $this.data(), column = {
				id : data.columnId,
				identifier : that.identifier == null && data.identifier || false,
				converter : that.options.converters[data.converter || data.type] || that.options.converters["string"],
				text : $this.text(),
				align : data.align || "left",
				headerAlign : data.headerAlign || "left",
				cssClass : data.cssClass || "",
				headerCssClass : data.headerCssClass || "",
				formatter : that.options.formatters[data.formatter] || null,
				order : (!sorted && (data.order === "asc" || data.order === "desc")) ? data.order : null,
				searchable : !(data.searchable === false), // default: true
				sortable : !(data.sortable === false), // default: true
				visible : !(data.visible === false), // default: true
				visibleInSelection : !(data.visibleInSelection === false), // default:
				map : data.map==null ? { } : eval('(' + data.map + ')'),
				// true
				width : ($.isNumeric(data.width)) ? data.width + "px" : (typeof (data.width) === "string") ? data.width : null
			};
			that.columns.push(column);
			if (column.order != null) {
				that.sortDictionary[column.id] = column.order;
			}

			// Prevents multiple identifiers
			if (column.identifier) {
				that.identifier = column.id;
				that.converter = column.converter;
			}

			// ensures that only the first order will be applied in case of
			// multi sorting is disabled
			if (!that.options.multiSort && column.order !== null) {
				sorted = true;
			}
		});
		/* jshint +W018 */
	}
	
	function loadRows() {
		if (!this.options.ajax) {
			var that = this, rows = this.element.find("tbody > tr");

			rows.each(function() {
				var $this = $(this), cells = $this.children("td"), row = {};

				$.each(that.columns, function(i, column) {
					row[column.id] = column.converter.from(column, cells.eq(i).text());
				});

				appendRow.call(that, row);
			});

			setTotals.call(this, this.rows.length);
			sortRows.call(this);
		}
	}
	
	function prepareTable() {
		var tpl = this.options.templates, wrapper = (this.element.parent().hasClass(this.options.css.responsiveTable)) ? this.element.parent() : this.element;

		this.element.addClass(this.options.css.table);

		// checks whether there is an tbody element; otherwise creates one
		if (this.element.children("tbody").length === 0) {
			this.element.append(tpl.body);
		}

		if (this.options.navigation & 1) {
			this.header = $(tpl.header.resolve(getParams.call(this, {
				id : this.element._bgId() + "-header"
			})));
			wrapper.before(this.header);
		}

		if (this.options.navigation & 2) {
			this.footer = $(tpl.footer.resolve(getParams.call(this, {
				id : this.element._bgId() + "-footer"
			})));
			wrapper.after(this.footer);
		}
	}
	function renderTableHeader() {
		var that = this, headerRow = this.element.find("thead > tr"), css = this.options.css, tpl = this.options.templates, html = "", sorting = this.options.sorting;

		if (this.selection) {
			var selectBox = (this.options.multiSelect) ? tpl.select.resolve(getParams.call(that, {
				type : "checkbox",
				value : "all"
			})) : "";
			html += tpl.rawHeaderCell.resolve(getParams.call(that, {
				content : selectBox,
				css : css.selectCell
			}));
		}

		$.each(this.columns, function(index, column) {
			if (column.visible) {
				var sortOrder = that.sortDictionary[column.id], iconCss = ((sorting && sortOrder && sortOrder === "asc") ? css.iconUp : (sorting && sortOrder && sortOrder === "desc") ? css.iconDown
						: ""), icon = tpl.icon.resolve(getParams.call(that, {
					iconCss : iconCss
				})), align = column.headerAlign, cssClass = (column.headerCssClass.length > 0) ? " " + column.headerCssClass : "";
				html += tpl.headerCell.resolve(getParams.call(that, {
					column : column,
					icon : icon,
					sortable : sorting && column.sortable && css.sortable || "",
					css : ((align === "right") ? css.right : (align === "center") ? css.center : css.left) + cssClass,
					style : (column.width == null) ? "" : "width:" + column.width + ";"
				}));
			}
		});

		headerRow.html(html);

		if (sorting) {
			var sortingSelector = getCssSelector(css.sortable);
			headerRow.off("click" + namespace, sortingSelector).on("click" + namespace, sortingSelector, function(e) {
				e.preventDefault();

				setTableHeaderSortDirection.call(that, $(this));
				sortRows.call(that);
				loadData.call(that);
			});
		}

		// todo: create a own function for that piece of code
		if (this.selection && this.options.multiSelect) {
			var selectBoxSelector = getCssSelector(css.selectBox);
			headerRow.off("click" + namespace, selectBoxSelector).on("click" + namespace, selectBoxSelector, function(e) {
				e.stopPropagation();

				if ($(this).prop("checked")) {
					that.select();
				} else {
					that.deselect();
				}
			});
		}
	}
	
	function loadData() {
		var that = this;

		this.element._bgBusyAria(true).trigger("load" + namespace);
		showLoading.call(this);

		function containsPhrase(row) {
			var column, searchPattern = new RegExp(that.searchPhrase, (that.options.caseSensitive) ? "g" : "gi");

			for (var i = 0; i < that.columns.length; i++) {
				column = that.columns[i];
				if (column.searchable && column.visible && column.converter.to(column, row[column.id]).search(searchPattern) > -1) {
					return true;
				}
			}

			return false;
		}

		function update(rows, total) {
			that.currentRows = rows;
			setTotals.call(that, total);

			if (!that.options.keepSelection) {
				that.selectedRows = [];
			}

			renderRows.call(that, rows);
			renderInfos.call(that);
			renderPagination.call(that);

			that.element._bgBusyAria(false).trigger("loaded" + namespace);
		}

		if (this.options.ajax) {
			var request = getRequest.call(this), url = getUrl.call(this);

			if (url == null || typeof url !== "string" || url.length === 0) {
				throw new Error("Url setting must be a none empty string or a function that returns one.");
			}

			// aborts the previous ajax request if not already finished or
			// failed
			if (this.xqr) {
				this.xqr.abort();
			}

			var settings = {
				url : url,
				data : request,
				success : function(response) {
					that.xqr = null;

					if (typeof (response) === "string") {
						response = $.parseJSON(response);
					}

					response = that.options.responseHandler(response);

					that.current = response.current;
					update(response.rows, response.total);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					that.xqr = null;

					if (textStatus !== "abort") {
						renderNoResultsRow.call(that); // overrides loading
						// mask
						that.element._bgBusyAria(false).trigger("loaded" + namespace);
					}
				}
			};
			settings = $.extend(this.options.ajaxSettings, settings);

			this.xqr = $.ajax(settings);
		} else {
			var rows = (this.searchPhrase.length > 0) ? this.rows.where(containsPhrase) : this.rows, total = rows.length;
			if (this.rowCount !== -1) {
				rows = rows.page(this.current, this.rowCount);
			}

			// todo: improve the following comment
			// setTimeout decouples the initialization so that adding event
			// handlers happens before
			window.setTimeout(function() {
				update(rows, total);
			}, 10);
		}
	}
	
	function init() {
		this.element.trigger("initialize" + namespace);

		loadColumns.call(this); // Loads columns from HTML thead tag
		this.selection = this.options.selection && this.identifier != null;
		loadRows.call(this); // Loads rows from HTML tbody tag if ajax is
		// false
		prepareTable.call(this);
		renderTableHeader.call(this);
		loadData.call(this);

		this.element.trigger("initialized" + namespace);
	}

	var Grid = function(element, options) {
		this.element = $(element);
		this.origin = this.element.clone();
		this.options = $.extend(true, {}, Grid.defaults, this.element.data(), options);
		// overrides rowCount explicitly because deep copy ($.extend) leads to
		// strange behaviour
		var rowCount = this.options.rowCount = this.element.data().rowCount || options.rowCount || this.options.rowCount;
		this.columns = [];
		this.current = 1;
		this.currentRows = [];
		this.identifier = null; // The first column ID that is marked as
		// identifier
		this.selection = false;
		this.converter = null; // The converter for the column that is marked
		// as identifier
		this.rowCount = ($.isArray(rowCount)) ? rowCount[0] : rowCount;
		this.rows = [];
		this.searchPhrase = "";
		this.selectedRows = [];
		this.sortDictionary = {};
		this.total = 0;
		this.totalPages = 0;
		this.cachedParams = {
			lbl : this.options.labels,
			css : this.options.css,
			ctx : {}
		};
		this.header = null;
		this.footer = null;
		this.xqr = null;

	};
	
	var old = $.fn.uigrid;

	$.fn.uigrid = function(option) {
		var args = Array.prototype.slice.call(arguments, 1), returnValue = null, elements = this.each(function(index) {
			var $this = $(this), instance = $this.data(namespace), options = typeof option === "object" && option;

			if (!instance && option === "destroy") {
				return;
			}
			if (!instance) {
				$this.data(namespace, (instance = new Grid(this, options)));
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

	$.fn.uigrid.Constructor = Grid;

	$.fn.uigrid.noConflict = function() {
		$.fn.uigrid = old;
		return this;
	};

})(jQuery, window);