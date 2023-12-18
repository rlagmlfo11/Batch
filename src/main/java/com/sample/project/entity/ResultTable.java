package com.sample.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESULTTABLE")
public class ResultTable {

	@Id
	@Column(name = "NICKNAME")
	private String nickName;

	@Column(name = "PROXYMAIL")
	private String proxyMail;

	@Column(name = "性別")
	private String seibetu;

	@Column(name = "OKANE")
	private String okane;

	public ResultTable() {

	}

	public ResultTable(String nickName, String proxyMail, String seibetu, String okane) {
		super();
		this.nickName = nickName;
		this.proxyMail = proxyMail;
		this.seibetu = seibetu;
		this.okane = okane;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the proxyMail
	 */
	public String getProxyMail() {
		return proxyMail;
	}

	/**
	 * @param proxyMail the proxyMail to set
	 */
	public void setProxyMail(String proxyMail) {
		this.proxyMail = proxyMail;
	}

	/**
	 * @return the seibetu
	 */
	public String getSeibetu() {
		return seibetu;
	}

	/**
	 * @param seibetu the seibetu to set
	 */
	public void setSeibetu(String seibetu) {
		this.seibetu = seibetu;
	}

	/**
	 * @return the okane
	 */
	public String getOkane() {
		return okane;
	}

	/**
	 * @param okane the okane to set
	 */
	public void setOkane(String okane) {
		this.okane = okane;
	}

}
