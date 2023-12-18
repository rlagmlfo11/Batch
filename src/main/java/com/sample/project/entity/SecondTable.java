package com.sample.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SECONDTABLE")
public class SecondTable {

	@Id
	@Column(name = "GIVENNAME")
	private String givenName;

	@Column(name = "OLD")
	private int old;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "PERSONALCONTACT")
	private String personalContact;

	@Column(name = "WAGE")
	private int wage;

	@Column(name = "PROPERTY")
	private String property;

	public SecondTable() {

	}

	public SecondTable(String givenName, int old, String sex, String personalContact, int wage,
			String property) {
		super();
		this.givenName = givenName;
		this.old = old;
		this.sex = sex;
		this.personalContact = personalContact;
		this.wage = wage;
		this.property = property;
	}

	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the old
	 */
	public int getOld() {
		return old;
	}

	/**
	 * @param old the old to set
	 */
	public void setOld(int old) {
		this.old = old;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the personalContact
	 */
	public String getPersonalContact() {
		return personalContact;
	}

	/**
	 * @param personalContact the personalContact to set
	 */
	public void setPersonalContact(String personalContact) {
		this.personalContact = personalContact;
	}

	/**
	 * @return the wage
	 */
	public int getWage() {
		return wage;
	}

	/**
	 * @param wage the wage to set
	 */
	public void setWage(int wage) {
		this.wage = wage;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

}
