package com.pubmine.utility;

public class Pagable {
	
	private int page;
	private int limit;

	private int totalCount;
	private int totalPages;
	
	private int offset;

	public Pagable() {
		this(1, 10, 0, 0);
	}
	public Pagable(int page, int limit, int totalCount, int totalPages) {
		this.page = page;
		this.limit = limit;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
	}

	public int getPage() {
		return page;
	}
	public void setPage(int currentPage) {
		this.page = (currentPage <= 1) ? 1 : currentPage;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalPages() {
		return (int) Math.ceil((double) this.getTotalCount() / limit);
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getOffset() {
		return (this.page - 1) * this.limit;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.setTotalPages(this.getTotalPages());
	}
	
	@Override
	public String toString() {
		return "Paging [page=" + page + ", limit=" + limit + ", totalCount=" + totalCount + ", totalPages=" + totalPages
				+ ", offset=" + offset + "]";
	}
	
}
