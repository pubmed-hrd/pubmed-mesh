package com.pubmine.model;

public class Mesh {
	private String id;
	private String keyword;

	public Mesh(String id, String keyword) {
		super();
		this.id = id;
		this.keyword = keyword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Mesh [id=" + id + ", keyword=" + keyword + "]";
	}
}
