package net.micode.notes.entity;

import java.util.Date;

public class RelationUserEnterprise {
	private String id;

	private String userid;

	private String enterprisename;

	private String taxcode;

	private String department;

	private String post;

	private Boolean status;

	private String notes;

	private Date inserttime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEnterprisename() {
		return enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename == null ? null : enterprisename
				.trim();
	}

	public String getTaxcode() {
		return taxcode;
	}

	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode == null ? null : taxcode.trim();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department == null ? null : department.trim();
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post == null ? null : post.trim();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	public Date getInserttime() {
		return inserttime;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}

	@Override
	public String toString() {
		return "RelationUserEnterprise [id=" + id + ", userid=" + userid
				+ ", enterprisename=" + enterprisename + ", taxcode=" + taxcode
				+ ", department=" + department + ", post=" + post + ", status="
				+ status + ", notes=" + notes + ", inserttime=" + inserttime
				+ ", dutyParagraph=" + "]";
	}
	
	
}