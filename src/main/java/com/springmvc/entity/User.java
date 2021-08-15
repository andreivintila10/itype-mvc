package com.springmvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="email")
	private String email;

	@Column(name="gender")
	private String gender;

	@Column(name="phone")
	private String phone;

	@Column(name="address")
	private String address;

	@Column(name="username", unique=true, length=100)
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="high_score", columnDefinition = "double default 0.0")
	private Double highScore;

	@Column(name="total_sessions", columnDefinition = "integer default 0")
	private Integer totalSessions;

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public Double getHighScore() {
		return highScore == null ? 0.0 : highScore;
	}

	public void setHighScore(Double highScore) {
		this.highScore = highScore;
	}

	public Integer getTotalSessions() {
		return totalSessions == null ? 0 : totalSessions;
	}

	public void setTotalSessions(Integer totalSessions) {
		this.totalSessions = totalSessions;
	}
}
