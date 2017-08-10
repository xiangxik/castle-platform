package com.castle.theme.uikit.moxiemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;

@Controller
@RequestMapping("/moxiemanager/api")
public class MoxieManagerController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public void get(String action, Model model) {

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> post(String action, Model model) {
		Map<String, Object> m = new HashMap<>();
		if (Objects.equal(action, "auth")) {
			m.put("token", "8e54e3fb2fa64f95f3c7d36d52d1f6a165f09df9e590d065fd86bed92c2c2009");
			m.put("installed", true);
			m.put("loggedin", true);
			m.put("loginurl", "");
			m.put("standalone", false);
			m.put("overwrite_action", "");
			m.put("dropbox.app_id", "jee1s9eykoh752j");
			m.put("googledrive.client_id", "692151683613.apps.googleusercontent.com");
		} else {
			/**
			 * {"jsonrpc":"2.0","result":{"columns":["name","size","modified","attrs","info"],"config":{"general.hidden_tools"
			 * :"","general.disabled_tools":"","filesystem.extensions":"*","filesystem.force_directory_template":false
			 * ,"upload.maxsize":"10MB","upload.chunk_size":"1mb","upload.extensions":"*","createdoc.templates":"Index
			 * page=\/testfiles\/templates\/document.htm,Normal
			 * page=\/testfiles\/templates\/another_document.htm"
			 * ,"createdoc.fields":"Document title=title","createdir.templates":"Image
			 * folder=\/testfiles\/templates
			 * \/directory"},"file":{"path":"\/testfiles","size":4096,"lastModified":1366967147,"isFile":false,"canRead"
			 * :true,"canWrite":true,"canEdit":false,"canRename":true,"canView":false,"canPreview":false,"canDelete"
			 * :true,"visible":true,"exists":true,"meta":{"url":"http:\/\/www.moxiemanager.com\/testfiles"}},"urlFile"
			 * :null,"data":[["testfolder",0,1366901948,"drwr----vd",{}],["allosaurus_fragilis.jpg",81744,1365695662
			 * ,"-rwrevptvd",{}],["amaincloudballprint.jpg",117552,1365695662,"-rwrevptvd",{}],["buildings.jpg",128037
			 * ,1365695662,"-rwrevptvd",{}],["camera.jpg",70567,1365695662,"-rwrevptvd",{}],["cdrom.jpg",53277,1365695662
			 * ,"-rwrevptvd",{}],["console.jpg",130569,1365695662,"-rwrevptvd",{}],["drive.jpg",81878,1365695662,"-rwrevptvd"
			 * ,{}],["Earthmap.jpg",53482,1365695662,"-rwrevptvd",{}],["firefox.jpg",114898,1365695662,"-rwrevptvd"
			 * ,{}],["grandmaphone.jpg",68075,1365695662,"-rwrevptvd",{}],["neutronstarprint1.jpg",75843,1365695662
			 * ,"-rwrevptvd",{}],["planetenmodell01.jpg",63682,1365695662,"-rwrevptvd",{}],["qwerty.jpg",81150,1365695662
			 * ,"-rwrevptvd",{}],["teddybear.jpg",17829,1364916147,"-rwrevptvd",{}],["test.txt",23,1365606853,"-rwrev--vd"
			 * ,{}]],"url":"http:\/\/www.moxiemanager.com\/testfiles","thumbnailFolder":"mcith","thumbnailPrefix":"mcith_"
			 * ,"offset":16,"last":true},"id":"i1","token":"25bb35021275f88fe9f3dd79a0c0ef852ffa7fc7968bdfc9596a2dbd5d9dc2b8"
			 * }
			 */

			m.put("jsonrpc", "2.0");
			Map<String, Object> result = new HashMap<>();
			result.put("columns", new String[] { "name", "size", "modified", "attrs", "info" });

			Map<String, Object> config = new HashMap<>();
			config.put("createdoc.templates",
					"Index page=\\/testfiles\\/templates\\/document.htm,Normal page=\\/testfiles\\/templates\\/another_document.htm");
			config.put("createdir.templates", "Image folder=\\/testfiles\\/templates\\/directory");
			config.put("general.hidden_tools", "");
			config.put("general.disabled_tools", "");
			config.put("filesystem.extensions", "*");
			config.put("filesystem.force_directory_template", false);
			config.put("upload.maxsize", "10MB");
			config.put("upload.chunk_size", "1mb");
			config.put("upload.extensions", "*");
			
			config.put("createdoc.fields", "Document title=title");
			
			result.put("config", config);

			Map<String, Object> file = new HashMap<>();
			file.put("path", "/testfiles");
			file.put("size", 4096);
			file.put("lastModified", 1366967147);
			file.put("isFile", false);
			file.put("canRead", true);
			file.put("canWrite", true);
			file.put("canEdit", false);
			file.put("canRename", true);
			file.put("canView", false);
			file.put("canPreview", false);
			file.put("canDelete", true);
			file.put("visible", true);
			file.put("exists", true);
			Map<String, Object> meta = new HashMap<>();
			meta.put("url", "http://www.whenling.com");
			file.put("meta", meta);
			result.put("file", file);

			result.put("urlFile", null);

			List<Object[]> data = new ArrayList<>();
			data.add(new Object[] { "testfolder", 0, 1366901948, "drwr----vd", new HashMap<>() });
			data.add(new Object[] { "allosaurus_fragilis.jpg", 81744, 1365695662, "-rwrevptvd", new HashMap<>() });
			result.put("data", data);

			result.put("url", "http://www.whenling.com");
			result.put("thumbnailFolder", "mcith");
			result.put("thumbnailPrefix", "mcith_");
			result.put("offset", 2);
			result.put("last", true);

			m.put("result", result);
			m.put("id", "i3");
			m.put("token", "8e54e3fb2fa64f95f3c7d36d52d1f6a165f09df9e590d065fd86bed92c2c2009");
		}
		return m;
	}

}
