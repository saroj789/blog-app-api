package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public boolean uploadFile(String path, MultipartFile file) throws IOException {
		boolean isUploaded = false;
		
		File f = new File(path);
		if(!f.exists()) {  f.mkdir();  }
		
		String fullPath = path + File.separator + file.getOriginalFilename();
		
		try {
            Files.copy(file.getInputStream(), Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);
            isUploaded = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occured");
        }
		return isUploaded;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream iStream = new FileInputStream(fullPath);
		return iStream;
	}

}
