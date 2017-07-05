if (!$c) {
	var $c = {};
	$c.parseQueryString = function(arr_url) {
		var reg_para = /([^&=]+)=([\w\W]*?)(&|$|#)/g, ret = {};
		if (arr_url) {
			var str_para = arr_url, result;
			while ((result = reg_para.exec(str_para)) != null) {
				if (result[2]) {
					ret[result[1]] = decodeURIComponent(result[2]);
				}
			}
		}
		return ret;
	};
	$c.activeMenu = function(code) {
		Pace.restart();

		var $sidebarMenu = $(".sidebar-menu");
		$sidebarMenu.find("li").removeClass("active");

		var menu = $sidebarMenu.find(".menu_" + code);
		var parentCode = menu.data("parent-code");
		if (!parentCode) {
			parentCode = code.substring(0, code.indexOf("_"));
		}
		if (parentCode && parentCode != "") {
			$sidebarMenu.find(".menu_" + parentCode).addClass("active");
		}
		menu.addClass("active");
	};
	$c.alert = function(content) {
		$.alert({
			title : "提示",
			content : content,
			buttons : {
				ok : {
					text : "确定"
				}
			}
		});
	}

}

if (!parseQueryString) {
	var parseQueryString = $c.parseQueryString;
}
if (!activeMenu) {
	var activeMenu = $c.activeMenu;
}

var deleteRow = function(grid, ids, deleteUrl) {
	$.confirm({
		title : "确认信息",
		content : "确定要删除吗？",
		buttons : {
			confirm : {
				text : "确定",
				action : function() {
					$.post(deleteUrl, {
						ids : ids
					}, function(resp) {
						if (resp.success) {
							$c.alert("操作成功");
							grid.bootgrid("reload")
						} else {
							$c.alert(resp.msg);
						}
					}, "json");
				}
			},
			cancel : {
				text : "取消"
			}
		}
	});
};

var actionWithSelectedRows = function($grid, action) {
	var selectedRows = $grid.bootgrid("getSelectedRows");
	if (selectedRows && selectedRows.length > 0) {
		action($grid, selectedRows);
	} else {
		$c.alert("请至少选择一条记录");
	}
}

var initBootGrid = function($grid, url, $content, formatters, viewUrl, editUrl, deleteUrl, loadedAction) {
	if (viewUrl || editUrl || deleteUrl) {
		if (!formatters) {
			formatters = {};
		}
		formatters["commands"] = function(column, row) {
			return "\
			<div class=\"btn-group btn-group-sm\" data-row-id=\""
					+ row.id
					+ "\">"
					+ (viewUrl ? ("<a class=\"btn btn-primary\" href=\"" + viewUrl + row.id + "\"><i class=\"glyphicon glyphicon-eye-open\"></i></a>")
							: "")
					+ (editUrl ? ("<a class=\"btn btn-primary\" href=\"" + editUrl + row.id + "\"><i class=\"glyphicon glyphicon-edit\"></i></a>")
							: "")
					+ (deleteUrl ? "<a class=\"btn btn-primary command-delete\" href=\"javascript:;\"><i class=\"glyphicon glyphicon-remove\"></i></a>"
							: "") + "</div>";
		}
	}
	var $searchForm = $content.find(".form-search");
	var $bootgrid = $grid.bootgrid({
		url : url,
		post : function() {
			if ($searchForm) {
				var keyValues = $searchForm.serialize();
				return parseQueryString(keyValues);
			}
			return null;
		},
		formatters : formatters
	});

	$bootgrid.on("loaded.rs.jquery.bootgrid", function() {
		var $table = $(this);
		$table.find(".dropdown-toggle").dropdown();

		if (deleteUrl) {
			$table.find(".command-delete").on("click", function(e) {
				var entity_id = $(this).closest(".btn-group").data("row-id");
				deleteRow($table, [ entity_id ], deleteUrl);
			});
		}
		if (loadedAction) {
			loadedAction($table);
		}
	});

	var $searchPanel = $content.find(".search-panel");
	$content.find(".search-button").on("click", function() {
		$bootgrid.bootgrid("reload");
		return false;
	});
	$content.find(".refresh-button").on("click", function() {
		$bootgrid.bootgrid("reload");
	});

	$content.find(".open-search-button").on("click", function() {
		$searchPanel.toggle('fast')
	});
	return $bootgrid;
}

var initValidateForm = function($targetForm, backUrl, action, beforeSubmit) {
	$targetForm.bootstrapValidator().on("success.form.bv", function(e) {
		e.preventDefault();
		var $form = $(e.target);
		if (beforeSubmit) {
			beforeSubmit($form);
		}
		$.post($form.attr('action'), $form.serialize(), function(result) {
			if (result.success) {
				$.alert({
					title : false,
					content : "保存成功",
					buttons : {
						ok : {
							text : "返回列表",
							action : function() {
								if (action) {
									action(result);
								}
								if (backUrl) {
									location.href = backUrl;
								}
							}
						}
					}
				});
			} else {
				$.alert({
					title : false,
					content : result.msg
				});
				$form.bootstrapValidator('disableSubmitButtons', false);
			}
		}, "json");
	});
}

var showGridDialog = function(title, dialogUrl, dataUrl, action, multi, formatters) {
	$.confirm({
		content : "url:" + dialogUrl,
		title : title,
		columnClass : 'col-md-10 col-md-offset-1 col-xs-12 col-xs-offset-0',
		onContentReady : function(data, status, xhr) {
			var $content = this.$content;
			var $searchForm = $content.find(".form-search");
			var $searchPanel = $content.find(".search-panel");

			$selectGrid = $content.find(".select-grid").bootgrid({
				url : dataUrl,
				rowSelect : true,
				multiSelect : multi == null ? false : multi,
				formatters : formatters,
				post : function() {
					var keyValues = $searchForm.serialize();
					var parameterMap = parseQueryString(keyValues);
					return parameterMap;
				}
			});
			$content.find(".search-button").on("click", function() {
				$selectGrid.bootgrid("reload");
				return false;
			});
		},
		buttons : {
			confirm : {
				text : "确定",
				action : function() {
					var selectedRows = $selectGrid.bootgrid("getSelectedRows");
					if (selectedRows && selectedRows.length > 0) {
						if (multi) {
							action(selectedRows);
						} else {
							var selectedRow = selectedRows[0];
							var selectedItem = $selectGrid.bootgrid("getCurrentRows").first(function(item) {
								return item["id"] === selectedRow;
							});
							action(selectedItem);
						}
					} else {
						$c.alert("请至少选择一条记录");
						return false;
					}
				}
			},
			cancel : {
				text : "取消"
			}
		}
	});
}