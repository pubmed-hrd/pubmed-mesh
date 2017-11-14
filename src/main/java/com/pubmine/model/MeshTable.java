package com.pubmine.model;

import java.util.List;

public class MeshTable {
	
	private List<Mesh> meshAnatomy;
	private List<Mesh> meshOrganisms;
	private List<Mesh> meshDiseases;
	private List<Mesh> meshChemicalsAndDrugs;
	
	public MeshTable(List<Mesh> meshAnatomy, List<Mesh> meshOrganisms, List<Mesh> meshDiseases,
			List<Mesh> meshChemicalsAndDrugs) {
		super();
		this.meshAnatomy = meshAnatomy;
		this.meshOrganisms = meshOrganisms;
		this.meshDiseases = meshDiseases;
		this.meshChemicalsAndDrugs = meshChemicalsAndDrugs;
	}
	public List<Mesh> getMeshAnatomy() {
		return meshAnatomy;
	}
	public void setMeshAnatomy(List<Mesh> meshAnatomy) {
		this.meshAnatomy = meshAnatomy;
	}
	public List<Mesh> getMeshOrganisms() {
		return meshOrganisms;
	}
	public void setMeshOrganisms(List<Mesh> meshOrganisms) {
		this.meshOrganisms = meshOrganisms;
	}
	public List<Mesh> getMeshDiseases() {
		return meshDiseases;
	}
	public void setMeshDiseases(List<Mesh> meshDiseases) {
		this.meshDiseases = meshDiseases;
	}
	public List<Mesh> getMeshChemicalsAndDrugs() {
		return meshChemicalsAndDrugs;
	}
	public void setMeshChemicalsAndDrugs(List<Mesh> meshChemicalsAndDrugs) {
		this.meshChemicalsAndDrugs = meshChemicalsAndDrugs;
	}
	
}
