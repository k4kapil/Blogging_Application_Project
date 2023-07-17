package com.kk.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// Step 1. get file name
				String filename = file.getOriginalFilename();
				// eg. abc.jpeg
				
				// random file name generator
				String randomId= UUID.randomUUID().toString();
				String fileName1 = randomId.concat(filename.substring(filename.lastIndexOf(".")));
				
				// Step 2. full path of file
				String filePath= path+ File.separator + fileName1;
				
				// Step 3. create folder if not created
				File file2= new File(path);
				if(!file2.exists()) 
				{
					file2.mkdir();
				}
				
				// Step 4. copy file
				Files.copy(file.getInputStream(),Paths.get(filePath) );
				
				return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath= path+ File.separator + fileName;
		InputStream inputStream= new FileInputStream(fullPath);
		return inputStream;
	}

}
