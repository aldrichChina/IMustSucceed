package net.micode.notes.entities;

public class NewsBody {
	private NewsPageBean pagebean;
	private String ret_code;
	public NewsPageBean getPagebean() {
		return pagebean;
	}
	public void setPagebean(NewsPageBean pagebean) {
		this.pagebean = pagebean;
	}
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	@Override
	public String toString() {
		return "NewsBody [pagebean=" + pagebean + ", ret_code=" + ret_code
				+ "]";
	}
}
