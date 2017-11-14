package com.pubmine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pubmine.service.MeshKeywordService;

@SpringBootApplication
public class PubmineMeshApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(PubmineMeshApplication.class, args);
	}

	@Autowired
	MeshKeywordService meshService;
	
	@Override
	public void run(String... arg0) throws Exception {
		meshService.meshSearchAndSave();
	}
	
}
