package com.springmvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="words")
public class Word {

	@Id
	@GeneratedValue(generator="generator")
	@GenericGenerator(name="generator", strategy="increment")
	@Column(name="id")
	private Long id;

	@Column(name="word", unique=true, length=100)
	private String word;

	public Word() {

	}

	public Word(String word) {
		this.word = word;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
