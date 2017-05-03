package com.whenling.castle.console.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.whenling.castle.console.entity.Admin;
import com.whenling.castle.filestorage.FileStorageService;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.security.CurrentUser;

@Controller
@RequestMapping("/multipart")
public class MultipartController {

	@Autowired
	private FileStorageService fileStorageService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Result upload(@RequestPart("file") MultipartFile[] parts, @CurrentUser Admin admin) {
		return uploadByCategory(parts, "file", admin);
	}

	@RequestMapping(value = "/upload/{category}", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadByCategory(@RequestPart("file") MultipartFile[] parts, @PathVariable("category") String category, @CurrentUser Admin admin) {
		List<String> urls = new ArrayList<>();
		for (MultipartFile part : parts) {
			try {
				urls.add(fileStorageService.upload(part.getInputStream(), category, part.getOriginalFilename(),
						admin == null ? "" : String.valueOf(admin.getId())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return Result.success().addProperties("urls", Joiner.on(",").join(urls));
	}

}
