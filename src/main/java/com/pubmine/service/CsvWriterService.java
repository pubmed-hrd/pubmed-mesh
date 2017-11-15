package com.pubmine.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pubmine.model.MeshTreeSentence;

@Service
public class CsvWriterService {

	@Value("${pubmine.csv.path}")
	private String csvPath;

	public void write(List<MeshTreeSentence> meshTreeSentences, String fileName, String csvHeader) {
		try {
			PrintWriter pw = new PrintWriter(new File(String.format("%s/%s.csv", csvPath, fileName)));
			
			if (csvHeader != null)
				pw.println(csvHeader);

			for (MeshTreeSentence m : meshTreeSentences) {
				String csvBody = String.format("%s\t%s\t%s", m.getId(), m.getPmid(), m.getSentenceOrder());
				pw.println(csvBody);
			}
			pw.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
