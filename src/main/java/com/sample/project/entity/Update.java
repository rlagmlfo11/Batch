package com.sample.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UPDATE")
public class Update {

	@Id
	@Column(name = "PERSONALNUMBER")
	private Long personalNumber;

	@Column(name = "NAME")
	private String name;

	@Column(name = "POSITION")
	private String position;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "KUBUN")
	private String kubun;

	@Column(name = "SETUP")
	private String setup;

	public Update() {

	}

	public Update(Long personalNumber, String name, String position, String gender, String kubun,
			String setup) {
		super();
		this.personalNumber = personalNumber;
		this.name = name;
		this.position = position;
		this.gender = gender;
		this.kubun = kubun;
		this.setup = setup;
	}

	/**
	 * @return the personalNumber
	 */
	public Long getPersonalNumber() {
		return personalNumber;
	}

	/**
	 * @param personalNumber the personalNumber to set
	 */
	public void setPersonalNumber(Long personalNumber) {
		this.personalNumber = personalNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the kubun
	 */
	public String getKubun() {
		return kubun;
	}

	/**
	 * @param kubun the kubun to set
	 */
	public void setKubun(String kubun) {
		this.kubun = kubun;
	}

	/**
	 * @return the setup
	 */
	public String getSetup() {
		return setup;
	}

	/**
	 * @param setup the setup to set
	 */
	public void setSetup(String setup) {
		this.setup = setup;
	}

}
