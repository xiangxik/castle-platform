requirejs.config({
	paths : {
		domReady : "http://cdn.staticfile.org/require-domReady/2.0.1/domReady.min",
		director : "https://cdn.staticfile.org/Director/1.2.8/director.min",
		jquery : "http://lib.sinaapp.com/js/jquery/2.2.4/jquery-2.2.4.min",
		bootstrap : "http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min",
		confirm : "http://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.0.3/jquery-confirm.min",
		slimscroll : "http://cdnjs.cloudflare.com/ajax/libs/jQuery-slimScroll/1.3.8/jquery.slimscroll.min",
		fastclick : "http://cdn.staticfile.org/fastclick/1.0.6/fastclick.min",
		icheck : "http://cdn.staticfile.org/iCheck/1.0.2/icheck.min",
		validator : "http://cdn.staticfile.org/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min",
		drag : base + "/assets/adminlte/vendor/jquery.event.drag/jquery.event.drag-2.2",

		moment : "http://cdn.bootcss.com/moment.js/2.18.1/moment.min",
		daterangepicker : "http://cdn.bootcss.com/bootstrap-daterangepicker/2.1.25/daterangepicker.min",

		bootgrid_base : base + "/assets/adminlte/vendor/jquery.bootgrid/jquery.bootgrid",
		bootgrid : base + "/assets/adminlte/vendor/jquery.bootgrid/jquery.bootgrid.fa.min",

		fileinput_base : "http://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.3.9/js/fileinput.min",
		fileinput : "http://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.3.9/js/locales/zh.min",

		treeview : "http://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min",

		fancytree : "http://cdnjs.cloudflare.com/ajax/libs/jquery.fancytree/2.21.0/jquery.fancytree-all.min",

		ztree : "http://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.28/js/jquery.ztree.core.min",
		ztree_check : "http://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.28/js/jquery.ztree.excheck.min",
		ztree_edit : "http://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.28/js/jquery.ztree.exedit.min",
		ztree_hide : "http://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.28/js/jquery.ztree.exhide.min",

		treegrid_base : "https://cdnjs.cloudflare.com/ajax/libs/jquery-treegrid/0.2.0/js/jquery.treegrid.min",
		treegrid : "https://cdnjs.cloudflare.com/ajax/libs/jquery-treegrid/0.2.0/js/jquery.treegrid.bootstrap3.min",

		select2 : "https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min",
		holder : "https://cdnjs.cloudflare.com/ajax/libs/holder/2.9.4/holder.min",

		kindeditor_base : base + "/assets/adminlte/vendor/kindeditor/kindeditor-all-min",
		kindeditor : base + "/assets/adminlte/vendor/kindeditor/lang/zh-CN",

		app : base + "/assets/adminlte/js/app.min",
		utils : base + "/assets/adminlte/js/utils"
	},
	map : {
		"*" : {
			"css" : "http://cdn.staticfile.org/require-css/0.1.8/css.min.js",
			"text" : "http://cdn.staticfile.org/require-text/2.0.12/text.min.js"
		}
	},
	shim : {
		jquery : {
			exports : "jquery"
		},
		slimscroll : [ "jquery" ],
		holder : [ "jquery" ],
		drag : [ "jquery" ],
		bootstrap : [ "jquery" ],

		moment : {
			exports : "moment"
		},
		daterangepicker : [ "moment", "bootstrap", "css!http://cdn.bootcss.com/bootstrap-daterangepicker/2.1.25/daterangepicker.min.css" ],

		kindeditor_base : [ "jquery" ],
		kindeditor : [ "kindeditor_base", "css!" + base + "/assets/adminlte/vendor/kindeditor/themes/default/default.css" ],
		fileinput_base : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.3.9/css/fileinput.min.css" ],
		fileinput : [ "fileinput_base" ],
		confirm : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.0.3/jquery-confirm.min.css" ],
		icheck : [ "bootstrap", "css!http://cdn.staticfile.org/iCheck/1.0.2/skins/square/blue.css" ],
		validator : [ "bootstrap", "css!http://cdn.staticfile.org/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css" ],
		bootgrid_base : [ "bootstrap", "css!" + base + "/assets/adminlte/vendor/jquery.bootgrid/jquery.bootgrid.min.css" ],
		bootgrid : [ "bootgrid_base" ],
		treeview : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.css" ],
		fancytree : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/jquery.fancytree/2.21.0/skin-win8/ui.fancytree.min.css" ],
		ztree_check : [ "ztree" ],
		ztree_edit : [ "ztree" ],
		ztree_hide : [ "ztree" ],
		treegrid_base : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/jquery-treegrid/0.2.0/css/jquery.treegrid.min.css" ],
		treegrid : [ "treegrid_base" ],
		select2 : [ "bootstrap", "css!http://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" ],
		app : [ "bootstrap", "confirm", "slimscroll", "fastclick", "utils" ]
	}
});

requirejs([ "domReady", "jquery", "director", "app", "holder" ], function(ready, $, director, app) {
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