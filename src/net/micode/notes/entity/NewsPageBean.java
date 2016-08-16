package net.micode.notes.entity;

import java.util.List;

public class NewsPageBean {
	private String allNum;
	private String allPages;
	private List<NewsDetailContent> contentlist;
	private String currentPage;
	private String maxResult;
	public String getAllNum() {
		return allNum;
	}
	public void setAllNum(String allNum) {
		this.allNum = allNum;
	}
	public String getAllPages() {
		return allPages;
	}
	public void setAllPages(String allPages) {
		this.allPages = allPages;
	}
	public List<NewsDetailContent> getContentlist() {
		return contentlist;
	}
	public void setContentlist(List<NewsDetailContent> contentlist) {
		this.contentlist = contentlist;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(String maxResult) {
		this.maxResult = maxResult;
	}
	@Override
	public String toString() {
		return "NewsPageBean [allNum=" + allNum + ", allPages=" + allPages
				+ ", contentlist=" + contentlist + ", currentPage="
				+ currentPage + ", maxResult=" + maxResult + "]";
	}
}
