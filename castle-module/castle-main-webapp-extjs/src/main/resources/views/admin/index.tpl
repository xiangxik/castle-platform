yieldUnescaped '<!DOCTYPE html>'
html {
	head {
		meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
		
		title(staticConfig['info.system_name'])
		
		link('rel':'stylesheet','type':'text/css','href':'//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css')
		link('rel':'stylesheet','type':'text/css','href':'//cdn.bootcss.com/extjs/6.0.1/classic/theme-'+staticConfig['info.theme']+'/resources/theme-'+staticConfig['info.theme']+'-all.css')
		link('rel':'stylesheet','type':'text/css','href':'//cdn.bootcss.com/extjs/6.0.1/packages/ux/classic/'+staticConfig['info.theme']+'/resources/ux-all.css')
		link('rel':'stylesheet','type':'text/css','href':base+'/extjs/app/css/common.css')
		
		script('type':'text/javascript', 'src':'//cdn.bootcss.com/extjs/6.0.1/ext-all.js', '')
		script('type':'text/javascript', 'src':base+'/assets/ext-6.0.1/packages/ux/classic/ux-debug.js', '')
		script('type':'text/javascript', 'src':'//cdn.bootcss.com/extjs/6.0.1/classic/theme-'+staticConfig['info.theme']+'/theme-'+staticConfig['info.theme']+'.js', '')
		script('type':'text/javascript', 'src':'//cdn.bootcss.com/extjs/6.0.1/classic/locale/locale-zh_CN.js', '')
		
		script('type':'text/javascript', 'src':base+'/assets/kindeditor-4.1.10/kindeditor-all-min.js', '')
		
		script('type':'text/javascript') {
			yieldUnescaped """
				Ext.ctx = "${base}";
				Ext.application({
					name : 'app',
					appFolder : "${base}/extjs/app",
					extend : "app.Application"
				});
			"""
		}
	}
	body {
					   
	}
}