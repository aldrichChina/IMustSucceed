package net.micode.notes.entities;

import java.util.Date;

public class User {
	private String id;

	private String name;

	private Short sex;

	private String phone;

	private String password;

	private Short status;

	private String notes;

	private Short age;

	private Date inserttime;

	private Date lastlogintime;

	private String lastloginip;

	private RelationUserEnterprise relationuserenterprise;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public Date getInserttime() {
		return inserttime;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip == null ? null : lastloginip.trim();
	}

	public RelationUserEnterprise getRelationuserenterprise() {
		return relationuserenterprise;
	}

	public void setRelationuserenterprise(
			RelationUserEnterprise relationuserenterprise) {
		this.relationuserenterprise = relationuserenterprise;
	}
}