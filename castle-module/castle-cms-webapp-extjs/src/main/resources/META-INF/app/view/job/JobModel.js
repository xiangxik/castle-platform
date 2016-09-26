Ext.define("app.view.job.JobModel", {
	extend : "Ext.app.ViewModel",
	alias : "viewmodel.job",
	stores : {
		list : Ext.create("app.store.Jobs")
	}
});