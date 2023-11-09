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

	public Update() {

	}

	public Update(Long personalNumber, String name, String position) {
		super();
		this.personalNumber = personalNumber;
		this.name = name;
		this.position = position;
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

}
