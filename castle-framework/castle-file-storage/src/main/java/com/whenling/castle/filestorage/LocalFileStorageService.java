package com.whenling.castle.filestorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.io.Files;

@Component
public class LocalFileStorageService implements FileStorageService {

	@Value("${file.server_host?:mdm.whenling.com}")
	private String fileServerHost;

	@Value("${file.upload_dir?:/home/mdmwebsite/upload}")
	private String uploadDir;

	@Override
	public String upload(InputStream inputStream, String filename, String member) {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");

		String destPath = uploadDir + "/" + uuid + filename;
		File destFile = new File(destPath);
		try {
			Files.createParentDirs(destFile);
			Files.write(IOUtils.toByteArray(inputStream), destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return convertToUrl(destPath);
	}

	@Override
	public InputStream download(String url, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String url, String member) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String convertToUrl(String path) {
		path = StringUtils.removeStart(path, uploadDir);
		return "http://" + fileServerHost + "/upload" + path;
	}

	@Override
	public String convertToPath(String url) {
		url = StringUtils.removeStart(url, "http://" + fileServerHost + "/upload");
		return uploadDir + url;
	}

}
