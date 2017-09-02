var assetsPath = base + "/assets/adminlte/";
requirejs.config({
	paths : {
		domReady : "http://cdn.staticfile.org/require-domReady/2.0.1/domReady.min",
		director : "http://cdn.staticfile.org/Director/1.2.8/director.min",

		jquery : "http://lib.sinaapp.com/js/jquery/2.2.4/jquery-2.2.4.min",
		bootstrap : "http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min",
		slimscroll : "http://cdnjs.cloudflare.com/ajax/libs/jQuery-slimScroll/1.3.8/jquery.slimscroll.min",
		fastclick : "http://cdn.staticfile.org/fastclick/1.0.6/fastclick.min",
		holder : "http://cdnjs.cloudflare.com/ajax/libs/holder/2.9.4/holder.min",
		jquery_confirm : "http://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min",
		fileinput_base : "http://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.3.9/js/fileinput.min",
		fileinput : "http://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.3.9/js/locales/zh.min",
		adminlte : assetsPath + "js/adminlte",
		demo : assetsPath + "js/demo",

		icheck : "http://cdn.staticfile.org/iCheck/1.0.2/icheck.min",
		validator : "http://cdn.staticfile.org/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min",
		drag : assetsPath + "vendor/jquery.event.drag/jquery.event.drag-2.2",

		moment : "http://cdn.bootcss.com/moment.js/2.18.1/moment.min",
		daterangepicker : "http://cdn.bootcss.com/bootstrap-daterangepicker/2.1.25/daterangepicker.min",

		datetimepicker : "http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min",

		bootgrid_base : assetsPath + "vendor/jquery.bootgrid/jquery.bootgrid",
		bootgrid : assetsPath + "vendor/jquery.bootgrid/jquery.bootgrid.fa.min",

		treeview : "http://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min",

		jquery_ui : assetsPath + "vendor/fancytree/js/jquery-ui.custom",
		fancytree : assetsPath + "vendor/fancytree/js/jquery.fancytree-all.min",
		jquery_contextmenu : "//cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.2.3/jquery.contextMenu.min",
		fancytree_contextmenu : assetsPath + "vendor/fancytree/3rd-party/extensions/contextmenu/js/jquery.fancytree.contextMenu",

		ztree : "http://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.28/js/jquery.ztree.core.min",
		ztree_check : "http://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.28/js/jquery.ztree.excheck.min",
		ztree_edit : "http://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.28/js/jquery.ztree.exedit.min",
		ztree_hide : "http://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.28/js/jquery.ztree.exhide.min",

		treegrid_base : "https://cdnjs.cloudflare.com/ajax/libs/jquery-treegrid/0.2.0/js/jquery.treegrid.min",
		treegrid : "https://cdnjs.cloudflare.com/ajax/libs/jquery-treegrid/0.2.0/js/jquery.treegrid.bootstrap3.min",

		select2 : "https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min",

		fileinput : "http://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.4.3/js/fileinput.min",

		kindeditor_base : assetsPath + "vendor/kindeditor/kindeditor-all",
		kindeditor : assetsPath + "vendor/kindeditor/lang/zh-CN",

		tinymce : "http://cdnjs.cloudflare.com/ajax/libs/tinymce/4.6.6/tinymce.min"
	},
	map : {
		"*" : {
			"css" : "http://cdn.staticfile.org/require-css/0.1.8/css.min.js",
			"text" : "http://cdn.staticfile.org/require-text/2.0.12/text.min.js"
		}
	},
	shim : {
		moment : {
			exports : "moment"
		},
		jquery : {
			exports : "jquery"
		},
		jquery_ui : [ "jquery" ],
		bootstrap : [ "jquery" ],
		slimscroll : [ "bootstrap" ],
		fastclick : [ "bootstrap" ],
		jquery_confirm : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css" ],
		fileinput : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.4.3/css/fileinput.min.css" ],
		adminlte : [ "slimscroll", "fastclick", "jquery_confirm" ],
		demo : [ "adminlte" ],
		datetimepicker : [ "bootstrap",
				"css!http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" ],
		daterangepicker : [ "bootstrap", "moment", "css!http://cdn.bootcss.com/bootstrap-daterangepicker/2.1.25/daterangepicker.min.css" ],

		kindeditor : [ "kindeditor_base", "css!" + assetsPath + "vendor/kindeditor/themes/default/default.css" ],
		icheck : [ "bootstrap", "css!http://cdn.staticfile.org/iCheck/1.0.2/skins/square/blue.css" ],
		validator : [ "bootstrap", "css!http://cdn.staticfile.org/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css" ],
		bootgrid_base : [ "bootstrap", "css!" + assetsPath + "vendor/jquery.bootgrid/jquery.bootgrid.min.css" ],
		bootgrid : [ "bootgrid_base" ],
		treeview : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.css" ],
		fancytree : [ "jquery_ui", "css!" + assetsPath + "vendor/fancytree/css/skin-win8/ui.fancytree.min.css" ],
		jquery_contextmenu : [ "jquery_ui", "css!http://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.2.3/jquery.contextMenu.min.css" ],
		fancytree_contextmenu : [ "fancytree", "jquery_contextmenu" ],
		ztree_check : [ "ztree" ],
		ztree_edit : [ "ztree" ],
		ztree_hide : [ "ztree" ],
		treegrid_base : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/jquery-treegrid/0.2.0/css/jquery.treegrid.min.css" ],
		treegrid : [ "treegrid_base" ],
		select2 : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" ]
	}
});

requirejs([ "domReady", "director", "adminlte" ], function(ready, director, demo) {
	ready(function() {
		var contentWrapper = $(".content-wrapper");
		var $body = $("body");
		var routes = {
			"/page" : {
				"?((\w|.)*)" : function(path) {
					require([ "text!" + base + "/" + path + "?_ajax=req&_t=" + (new Date()).getTime() ], function(html) {
						contentWrapper.html(html);
						$body.removeClass('sidebar-open');
					});
				}
			}
		};
		var router = Router(routes);
		router.init();

		$('[data-toggle="control-sidebar"]').controlSidebar()
		$('[data-toggle="push-menu"]').pushMenu()

		var $pushMenu = $('[data-toggle="push-menu"]').data('lte.pushmenu')
		var $controlSidebar = $('[data-toggle="control-sidebar"]').data('lte.controlsidebar')
		var $layout = $('body').data('lte.layout')

		$layout.fixSidebar()
		if ($('body').hasClass('fixed')) {
			$pushMenu.expandOnHover()
			$layout.activate()
		}
		$controlSidebar.fix()

		$('.sidebar-menu').tree();

		$(document).ajaxError(function(evt, req, settings) {
			if (req && (req.status === 200 || req.status === 0)) {
				return false;
			}
			if (req && req.status === 403) {
				location.href = base + "/login";
			}
		});

		var hash = window.location.hash;
		if (hash && hash != "") {

		} else {
			window.location.href = "#/page/dashboard";
		}
	});
});