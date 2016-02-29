package net.micode.notes.entities;

/**
 * @author Aldrich_jia
 *
 *"id": "1521", 
  "taici": "不是所有的鱼都会生活在同一片海里", 
  "cat": "d", 
  "catcn": "小说", 
  "show": "村上春树", 
  "source": "舞！舞！舞！"
 */
public class HouseSaid {
	private String id;
	private String taici;
	private String cat;
	private String catcn;
	private String show;
	private String source;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaici() {
		return taici;
	}
	public void setTaici(String taici) {
		this.taici = taici;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getCatcn() {
		return catcn;
	}
	public void setCatcn(String catcn) {
		this.catcn = catcn;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "HouseSaid [id=" + id + ", taici=" + taici + ", cat=" + cat
				+ ", catcn=" + catcn + ", show=" + show + ", source=" + source
				+ "]";
	}
	
}
