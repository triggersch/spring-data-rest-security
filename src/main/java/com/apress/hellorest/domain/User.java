package com.apress.hellorest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="USERS")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Long id;
	
	@Column
	@NotEmpty
	private String username;
	
	@Column
	@NotEmpty
	@JsonIgnore
	private String password;
	
	@Column(name="FIRST_NAME")
	@NotEmpty
	private String firstname;
	
	@Column(name="LAST_NAME")
	@NotEmpty
	private String lastname;
	
	@Column(name="ADMIN", columnDefinition="char(3)")
	@Type(type="yes_no")
	@NotEmpty
	private boolean admin;

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Long getId() {
		return id;
	}
}
