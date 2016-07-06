Ext.define("app.view.main.CenterController", {
	extend : "Ext.Mixin",
	alias : "controller.center",

	findTabByCode : function(code) {
		var center = Ext.getCmp("maincenter");
		var tab = center.down("> panel#" + code);
		return tab;
	},

	addViewToCenter : function(code, view) {
		var center = Ext.getCmp("maincenter");
		var tab = center.down("> panel#" + code);
		if (!tab) {
			tab = center.add(view);
		}
		return tab;
	},

	activeTab : function(tab) {
		var center = Ext.getCmp("maincenter");
		center.setActiveTab(tab);
	},

	closeTab : function(tab) {
		var center = Ext.getCmp("maincenter");
		center.remove(tab);
	}

});