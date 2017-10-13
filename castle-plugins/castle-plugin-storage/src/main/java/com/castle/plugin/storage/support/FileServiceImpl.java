package com.castle.plugin.storage.support;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.io.Files;

@Component
public class FileServiceImpl implements FileService, ServletContextAware {

	@Value("${file.upload.path?:upload}")
	private String localFileUploadPath;

	@Value("${file.tmp.path?:upload/tmp}")
	private String tmpFilePath;

	@Value("${file.url?:}")
	private String fileUrl;

	private ServletContext servletContext;

	@Override
	public boolean isValid(MultipartFile multipartFile) {
		return true;
	}

	@Override
	public String upload(MultipartFile multipartFile, boolean async) {
		return null;
	}

	@Override
	public String upload(MultipartFile multipartFile) {
		return uploadLocal(multipartFile);
	}

	@Override
	public String uploadLocal(MultipartFile multipartFile) {
		return uploadLocal(multipartFile, null);
	}

	@Override
	public String uploadLocal(InputStream inputStream, String filename) {
		return uploadLocal(inputStream, filename, null);
	}

	@Override
	public String uploadLocal(MultipartFile multipartFile, Visitor visitor) {
		if (multipartFile == null) {
			return null;
		}

		try {
			return uploadLocal(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), visitor);
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String uploadLocal(InputStream inputStream, String filename, Visitor visitor) {

		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");

		String path = uuid + "." + FilenameUtils.getExtension(filename);
		String destPath = fixUrl(localFileUploadPath, path);

		boolean relative = !StringUtils.startsWith(destPath, "/");
		File destFile = new File(relative ? servletContext.getRealPath(destPath) : destPath);
		try {
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			Files.write(IOUtils.toByteArray(inputStream), destFile);

			if (visitor != null) {
				visitor.visit(destFile);
			}

			return fixUrl(relative && Strings.isNullOrEmpty(fileUrl) ? (servletContext.getContextPath() + "/upload") : fileUrl, path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<FileInfo> browser(String path, String orderProperty) {
		String destPath = fixUrl(localFileUploadPath, path);
		File destFile = new File(StringUtils.startsWith(destPath, "/") ? destPath : servletContext.getRealPath(destPath));
		if (!destFile.isDirectory()) {
			return null;
		}

		String url = fixUrl(fileUrl, path);

		File[] files = destFile.listFiles();
		List<FileInfo> fileInfos = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(file.getName());
				fileInfo.setIsDirectory(file.isDirectory());
				fileInfo.setLastModified(new Date(file.lastModified()));
				fileInfo.setSize(file.length());
				fileInfo.setUrl(fixUrl(url, file.getName()));
				fileInfos.add(fileInfo);
			}
		}

		if ("SIZE".equals(orderProperty)) {
			Collections.sort(fileInfos, sizeComparator);
		} else if ("TYPE".equals(orderProperty)) {
			Collections.sort(fileInfos, typeComparator);
		} else {
			Collections.sort(fileInfos, nameComparator);
		}

		return fileInfos;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	protected String fixUrl(String url1, String url2) {
		if (StringUtils.endsWith(url1, "/")) {
			if (StringUtils.startsWith(url2, "/")) {
				url2 = StringUtils.removeStart(url2, "/");
			}
		} else {
			if (!StringUtils.startsWith(url2, "/")) {
				url2 = "/" + url2;
			}
		}
		return url1 + url2;
	}

	private NameComparator nameComparator = new NameComparator();
	private SizeComparator sizeComparator = new SizeComparator();
	private TypeComparator typeComparator = new TypeComparator();

	public class NameComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileA, FileInfo fileB) {
			if (fileA.getIsDirectory() && !fileB.getIsDirectory()) {
				return -1;
			} else if (!fileA.getIsDirectory() && fileB.getIsDirectory()) {
				return 1;
			} else {
				return fileA.getName().compareTo(fileB.getName());
			}
		}
	}

	public class SizeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileA, FileInfo fileB) {
			if (fileA.getIsDirectory() && !fileB.getIsDirectory()) {
				return -1;
			} else if (!fileA.getIsDirectory() && fileB.getIsDirectory()) {
				return 1;
			} else {
				if (fileA.getSize() > fileB.getSize()) {
					return 1;
				} else if (fileA.getSize() < fileB.getSize()) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	public class TypeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileA, FileInfo fileB) {
			if (fileA.getIsDirectory() && !fileB.getIsDirectory()) {
				return -1;
			} else if (!fileA.getIsDirectory() && fileB.getIsDirectory()) {
				return 1;
			} else {
				return FilenameUtils.getExtension(fileA.getName()).compareTo(FilenameUtils.getExtension(fileB.getName()));
			}
		}
	}

	@Override
	public Resource toResource(String path) {
		if (StringUtils.startsWithAny(path, "http://", "https://")) {

			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replaceAll("-", "");
			String targetPath = uuid + "." + FilenameUtils.getExtension(path);
			String destPath = fixUrl(tmpFilePath, targetPath);

			boolean relative = !StringUtils.startsWith(destPath, "/");
			File destFile = new File(relative ? servletContext.getRealPath(destPath) : destPath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}

			httpDownloadFile(path, destFile.getPath());
			return new ServletContextResource(servletContext, destPath);
		} else {
			path = StringUtils.removeStart(path, servletContext.getContextPath());
			return new ServletContextResource(servletContext, path);
		}

	}

	private void httpDownloadFile(String url, String filePath) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			try {
				HttpEntity httpEntity = response1.getEntity();
				InputStream is = httpEntity.getContent();
				// 根据InputStream 下载文件
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int r = 0;
				while ((r = is.read(buffer)) > 0) {
					output.write(buffer, 0, r);
				}
				FileOutputStream fos = new FileOutputStream(filePath);
				output.writeTo(fos);
				output.flush();
				output.close();
				fos.close();
				EntityUtils.consume(httpEntity);
			} finally {
				response1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
