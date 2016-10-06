Ext.define("app.view.feedback.FeedbackModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.feedback",
	stores : {
		list : Ext.create("app.store.Feedbacks")
	}
});