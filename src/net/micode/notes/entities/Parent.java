package net.micode.notes.entities;

import java.util.Date;

public class Parent extends EntityBase {

	// 建议加上注解， 混淆后列名不受影响
	public String name;

	private String email;

	private boolean isAdmin;

	private Date time;

	private java.sql.Date date;


	// @Finder(valueColumn = "id",targetColumn = "parentId")
	// public Child children;
	// @Finder(valueColumn = "id", targetColumn = "parentId")
	// private List<Child> children;

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Parent{" + "id=" + getId() + ", name='" + name + '\''
				+ ", email='" + email + '\'' + ", isAdmin=" + isAdmin
				+ ", time=" + time + ", date=" + date + '}';
	}
}
