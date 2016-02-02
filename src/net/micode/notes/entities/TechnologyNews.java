package net.micode.notes.entities;

/**
 * @author Aldrich_jia
 *{
  "0": {
    "time": "2016-02-02 07:06", 
    "title": "畅游预警《天龙八部》端游手游收入双双下降", 
    "description": "畅游预警《天龙八部》端游手游收入双双下降...", 
    "picUrl": "http://img1.gtimg.com/tech/pics/hv1/48/215/2015/131080248.png", 
    "url": "http://tech.qq.com/a/20160202/009357.htm"
  }, 
  "1": {
    "time": "2016-02-02 07:19", 
    "title": "谷歌母公司Alphabet反超苹果 市值全球第一", 
    "description": "谷歌母公司Alphabet反超苹果 市值全球第一...", 
    "picUrl": "http://mat1.gtimg.com/tech/00Jamesdu/2014/index/remark/2.png", 
    "url": "http://tech.qq.com/a/20160202/009913.htm"
  }, 
 */
public class TechnologyNews {
	private String time;
	private String title;
	private String description;
	private String picUrl;
	private String url;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "TechnologyNews [time=" + time + ", title=" + title
				+ ", description=" + description + ", picUrl=" + picUrl
				+ ", url=" + url + "]";
	}
}
