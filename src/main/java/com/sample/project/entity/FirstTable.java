package com.sample.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIRSTTABLE")
public class FirstTable {

	@Id
	@Column(name = "NAME")
	private String name;

	@Column(name = "AGE")
	private int age;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "MAIL")
	private String mail;

	@Column(name = "WAGE")
	private int wage;

	public FirstTable() {

	}

	public FirstTable(String name, int age, String gender, String mail, int wage) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.mail = mail;
		this.wage = wage;
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
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
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
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
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

}
