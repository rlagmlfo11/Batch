package com.sample.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KUBUN")
public class Kubun {

	@Id
	@Column(name = "kubun")
	private String kubun;

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

}
