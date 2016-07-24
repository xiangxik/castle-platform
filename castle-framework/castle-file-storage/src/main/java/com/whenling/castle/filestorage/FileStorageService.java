package com.whenling.castle.filestorage;

import java.io.InputStream;

public interface FileStorageService {

	String upload(InputStream inputStream, String filename, String member);

	InputStream download(String url, String member);

	boolean remove(String url, String member);

	String convertToUrl(String path);

	String convertToPath(String url);
}
