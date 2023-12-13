package com.sample.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NEWCUSTOMER")
public class NewCustomer {

	@Id
	@Column(name = "FULLNAME")
	private String fullName;

	@Column(name = "Nation")
	private String nation;

	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@Column(name = "BIRTHDAY")
	private String birthday;

	@Column(name = "EMAIL")
	private String email;

	public NewCustomer() {

	}

	public NewCustomer(String fullName, String nation, String phoneNumber, String birthday,
			String email) {
		super();
		this.fullName = fullName;
		this.nation = nation;
		this.phoneNumber = phoneNumber;
		this.birthday = birthday;
		this.email = email;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the nation
	 */
	public String getNation() {
		return nation;
	}

	/**
	 * @param nation the nation to set
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
