package com.castle.plugin.storage.support.multipart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.castle.plugin.storage.support.FileService;
import com.castle.repo.domain.Result;
import com.google.common.base.Joiner;

@Controller
@RequestMapping("/multipart")
public class MultipartController {

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Result upload(@RequestPart("file") MultipartFile[] parts) {
		List<String> urls = new ArrayList<>();
		List<Long> sizes = new ArrayList<>();
		for (MultipartFile part : parts) {
			sizes.add(part.getSize());
			urls.add(fileService.upload(part));
		}
		return Result.success().addProperties("urls", Joiner.on(",").join(urls)).addProperties("sizes", sizes);
	}

	@RequestMapping(value = "/uploadMore", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadMore(@RequestPart("files[]") MultipartFile[] parts) {
		return upload(parts);
	}

}
