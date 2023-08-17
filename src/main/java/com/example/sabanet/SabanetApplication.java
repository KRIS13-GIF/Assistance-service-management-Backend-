package com.example.sabanet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@SpringBootApplication
public class SabanetApplication {

	public static void main(String[] args) {



		SpringApplication.run(SabanetApplication.class, args);
		try {
			FileSystemResource resource = new FileSystemResource("output.txt"); // Replace with your file's name
			byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
			String content = new String(fileData, "UTF-8");
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
