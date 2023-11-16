package com.sample.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PEOPLE")
public class People {

	@Id
	@Column(name = "PERSONALNUMBER")
	private Long personalNumber;

	@Column(name = "NAME")
	private String name;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "JOB")
	private String job;

	@Column(name = "POSITION")
	private String position;

	@Column(name = "SETUP1")
	private String setup1;

	@Column(name = "SETUP2")
	private String setup2;

	@Column(name = "SETUP3")
	private String setup3;

	@Column(name = "SETUP4")
	private String setup4;

	@Column(name = "RECEIVED_DATE")
	private String date;

	public People() {

	}

	public People(Long personalNumber, String name, String gender, String job, String position,
			String setup1, String setup2, String setup3, String setup4, String date) {
		super();
		this.personalNumber = personalNumber;
		this.name = name;
		this.gender = gender;
		this.job = job;
		this.position = position;
		this.setup1 = setup1;
		this.setup2 = setup2;
		this.setup3 = setup3;
		this.setup4 = setup4;
		this.date = date;
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
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
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
	 * @return the setup1
	 */
	public String getSetup1() {
		return setup1;
	}

	/**
	 * @param setup1 the setup1 to set
	 */
	public void setSetup1(String setup1) {
		this.setup1 = setup1;
	}

	/**
	 * @return the setup2
	 */
	public String getSetup2() {
		return setup2;
	}

	/**
	 * @param setup2 the setup2 to set
	 */
	public void setSetup2(String setup2) {
		this.setup2 = setup2;
	}

	/**
	 * @return the setup3
	 */
	public String getSetup3() {
		return setup3;
	}

	/**
	 * @param setup3 the setup3 to set
	 */
	public void setSetup3(String setup3) {
		this.setup3 = setup3;
	}

	/**
	 * @return the setup4
	 */
	public String getSetup4() {
		return setup4;
	}

	/**
	 * @param setup4 the setup4 to set
	 */
	public void setSetup4(String setup4) {
		this.setup4 = setup4;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

}
