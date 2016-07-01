/******************************************************************
 *    Package:     net.micode.notes.entities
 *
 *    Filename:    WxhotArticle.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年6月30日 上午8:54:38
 *
 *    Revision:
 *
 *    2016年6月30日 上午8:54:38
 *        - second revision
 *
 *****************************************************************/
package net.micode.notes.entities;


/**
 * @ClassName WxhotArticle
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年6月30日 上午8:54:38
 * @version 1.0.0
 * "ctime": "2016-03-31", 
      "title": "小本生意做什么挣钱十七大小本生意推荐", 
      "description": "创业最前线", 
      "picUrl": "http://zxpic.gtimg.com/infonew/0/wechat_pics_-4225297.jpg/640", 
      "url": "http://mp.weixin.qq.com/s?__biz=MzA3NjgzNDUwMQ==&idx=2&mid=401864059&sn=cfa082e38ba38c7e673b1ce0a075faee"
 */
public class WxhotArticle {
    private String id;
    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;
    
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the ctime
     */
    public String getCtime() {
        return ctime;
    }
    
    /**
     * @param ctime the ctime to set
     */
    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return the picUrl
     */
    public String getPicUrl() {
        return picUrl;
    }
    
    /**
     * @param picUrl the picUrl to set
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /* (非 Javadoc)
     * Description:
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WxhotArticle [ctime=" + ctime + ", title=" + title + ", description=" + description + ", picUrl="
                + picUrl + ", url=" + url + "]";
    }
    
}
