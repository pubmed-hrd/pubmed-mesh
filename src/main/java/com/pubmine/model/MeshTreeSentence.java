package com.pubmine.model;

public class MeshTreeSentence {

	private String id;
	private Integer pmid;
	private Integer sentenceOrder;

	public MeshTreeSentence() {
		super();
	}

	public MeshTreeSentence(String id, Integer pmid, Integer sentenceOrder) {
		super();
		this.id = id;
		this.pmid = pmid;
		this.sentenceOrder = sentenceOrder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPmid() {
		return pmid;
	}

	public void setPmid(Integer pmid) {
		this.pmid = pmid;
	}

	public Integer getSentenceOrder() {
		return sentenceOrder;
	}

	public void setSentenceOrder(Integer sentenceOrder) {
		this.sentenceOrder = sentenceOrder;
	}

	@Override
	public String toString() {
		return "MeshTreeSentence [id=" + id + ", pmid=" + pmid + ", sentenceOrder=" + sentenceOrder + "]";
	}

}
