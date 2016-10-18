Ext.define("app.view.question.QuestionModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.question",
	stores : {
		list : Ext.create("app.store.Questions")
	}
});