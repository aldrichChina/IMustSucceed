package net.micode.notes.entity;

public class NewsImageUrls {
	private int height;
	private String url;
	private int width;
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	@Override
	public String toString() {
		return "NewsImageUrls [height=" + height + ", url=" + url + ", width="
				+ width + "]";
	}
}
