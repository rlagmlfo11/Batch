package com.sample.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class BlackList {

	@Id
	@Column(name = "NAME")
	private String Name;

	@Column(name = "LOCATION")
	private String Location;

	@Column(name = "PERSONAL_NUMBER")
	private String personalNumber;

	@Column(name = "PERSONAL_CODE")
	private String personalCode;

	@Column(name = "BLOCKED_DATE")
	private String blockedDate;

	public BlackList() {

	}

	public BlackList(String name, String location, String personalNumber, String personalCode,
			String blockedDate) {
		super();
		Name = name;
		Location = location;
		this.personalNumber = personalNumber;
		this.personalCode = personalCode;
		this.blockedDate = blockedDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return Location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		Location = location;
	}

	/**
	 * @return the personalNumber
	 */
	public String getPersonalNumber() {
		return personalNumber;
	}

	/**
	 * @param personalNumber the personalNumber to set
	 */
	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}

	/**
	 * @return the personalCode
	 */
	public String getPersonalCode() {
		return personalCode;
	}

	/**
	 * @param personalCode the personalCode to set
	 */
	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}

	/**
	 * @return the blockedDate
	 */
	public String getBlockedDate() {
		return blockedDate;
	}

	/**
	 * @param blockedDate the blockedDate to set
	 */
	public void setBlockedDate(String blockedDate) {
		this.blockedDate = blockedDate;
	}

}
