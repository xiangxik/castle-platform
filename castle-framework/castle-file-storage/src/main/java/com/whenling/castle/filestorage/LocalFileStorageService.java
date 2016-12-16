package com.whenling.castle.filestorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
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
	public String upload(InputStream inputStream, String dir, String filename, String member) {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");

		String destPath = uploadDir + "/" + dir + "/" + uuid + "." + FilenameUtils.getExtension(filename);
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
		String path = convertToPath(url);
		try {
			return new FileInputStream(path);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean remove(String url, String member) {
		String path = convertToPath(url);
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	@Override
	public String convertToUrl(String path) {
		path = StringUtils.removeStart(path, uploadDir);
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return "http://" + fileServerHost + "/upload" + path;
	}

	@Override
	public String convertToPath(String url) {
		url = StringUtils.removeStart(url, "http://" + fileServerHost + "/upload");
		return uploadDir + url;
	}

	@Override
	public File[] list(String dir, String path, String sorter) {

		String currentPath = uploadDir + "/" + dir + "/" + path;
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			return null;
		}

		return currentPathFile.listFiles();
	}

}
