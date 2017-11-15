package com.pubmine.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pubmine.model.Mesh;
import com.pubmine.model.MeshTable;
import com.pubmine.model.MeshTreeSentence;
import com.pubmine.repository.MeshRepository;
import com.pubmine.utility.Pagable;

@Service
public class MeshKeywordService {

	private MeshRepository meshRepository;

	private SentenceService sentenceService;

	@Value("${pubmine.mesh.file}")
	private String meshFile;
	
	@Value("${pubmine.mesh.limit}")
	private Integer meshLimit;

	@Value("${pubmine.csv.path}")
	private String csvPath;
	
	@Autowired
	public MeshKeywordService(MeshRepository meshRepository, SentenceService sentenceService) {
		this.meshRepository = meshRepository;
		this.sentenceService = sentenceService;
	}

	private MeshTable read() {
		System.out.println(String.format("-> Reading from file [ %s ]...", meshFile));

		try (BufferedReader br = new BufferedReader(new FileReader(new File(meshFile)))) {
			String line = null;

			List<Mesh> meshAnatomy = new ArrayList<>();
			List<Mesh> meshOrganisms = new ArrayList<>();
			List<Mesh> meshDiseases = new ArrayList<>();
			List<Mesh> meshChemicalsAndDrugs = new ArrayList<>();

			while ((line = br.readLine()) != null) {
				String[] l = line.split(";");
				if (l[1].startsWith("A")) {
					meshAnatomy.add(new Mesh(l[1], l[0]));
				}
				if (l[1].startsWith("B")) {
					meshOrganisms.add(new Mesh(l[1], l[0]));
				}
				if (l[1].startsWith("C")) {
					meshDiseases.add(new Mesh(l[1], l[0]));
				}
				if (l[1].startsWith("D")) {
					meshChemicalsAndDrugs.add(new Mesh(l[1], l[0]));
				}
			}
			return new MeshTable(meshAnatomy, meshOrganisms, meshDiseases, meshChemicalsAndDrugs);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveMesh() {
		MeshTable meshTable = this.read();

		System.out.println(meshTable.getMeshOrganisms().size());
		System.out.println(meshTable.getMeshAnatomy().size());
		System.out.println(meshTable.getMeshDiseases().size());
		System.out.println(meshTable.getMeshChemicalsAndDrugs().size());

		meshRepository.save(meshTable.getMeshAnatomy(), "mesh_anatomy");
		meshRepository.save(meshTable.getMeshOrganisms(), "mesh_organisms");
		meshRepository.save(meshTable.getMeshDiseases(), "mesh_diseases");

		List<Mesh> m1 = new ArrayList<>();
		List<Mesh> m2 = new ArrayList<>();
		List<Mesh> mesh = meshTable.getMeshChemicalsAndDrugs();
		
		m1 = mesh.subList(0, mesh.size() / 2);
		m2 = mesh.subList(mesh.size() / 2, mesh.size());
		
		long start = System.currentTimeMillis();
		meshRepository.save(m1, "mesh_chemicals_and_drugs");
		meshRepository.save(m2, "mesh_chemicals_and_drugs");
		System.out.println(String.format("Finish in %s seconds", (System.currentTimeMillis() - start) * Math.pow(10, -3)));
	}

	public void meshSearchAndSave() {

		MeshTable meshTable = this.read();
		System.out.println("=> Finish read data!!!");
		
		Map<String, List<Mesh>> mapMesh = new HashMap<>();
		mapMesh.put("mesh_anatomy_sentence", meshTable.getMeshAnatomy());
		mapMesh.put("mesh_organisms_sentence", meshTable.getMeshAnatomy());
		mapMesh.put("mesh_diseases_sentence", meshTable.getMeshDiseases());
		mapMesh.put("mesh_chemicals_and_drugs_sentence", meshTable.getMeshAnatomy());
		
		Pagable paging = new Pagable();
		paging.setLimit(meshLimit);
		int page = 0;
		
		for(Map.Entry<String, List<Mesh>> map: mapMesh.entrySet()){
			System.out.println("=> Executing table: " + map.getKey());
			for (Mesh m : map.getValue()) {
				page = 1;
				do{
					paging.setPage(page);
					List<MeshTreeSentence> meshTreeSentences = sentenceService.searchForMeshTree(m, paging);
					
					if(meshTreeSentences.size() > 0){
						
						long start = System.currentTimeMillis();
						try (ObjectOutputStream ois = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(String.format("%s/%s.out", csvPath, map.getKey()), true)))){
							System.out.println("=> Saving to file!!!");
							ois.writeObject(meshTreeSentences);
							
						}catch (Exception e) {
							e.printStackTrace();
						}finally{
							System.out.println(String.format("-> Finish writing file in %s seconds\n", (System.currentTimeMillis() - start) * Math.pow(10, -3)));
						}
					}
					page++;
				}while(page <= paging.getTotalPages());
			}
		}
		
	}
}
