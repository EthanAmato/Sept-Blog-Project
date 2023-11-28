package com.tts.blogproject.models;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Thinking back to our project yesterday, we need to tell SpringBoot that this class
// is going to be mapped to a table in our database.
// The way we do this is through the @Entity annotation:

@Entity
public class BlogEntry {

	// Now we need to specify 
	// 1. The primary key of our table (Id)
	// 2. a series of instance variables that will act as columns in each row of our db (with optional validation)
	
	// To specify a Primary Key in SpringBoot, we use the @Id annotation
	// To specify HOW SB will generate new ids for rows in our DB, we use
	// the @GeneratedValue annotation:
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// Define a series of instance variables to act as colums in the BlogEntry table
	// Also provide some validation rules so that each object follows a series of rules:
	
	// Make sure that the author string is between 1 and 50
	@Length(max = 50, min = 1)
	private String author;
	
	@Length(max = 50, min = 1)
	private String title;
	
	// Blog content must be at least 1 character long (no blank blog entries)
	@Length(min = 1)
	private String blogContent;
	
	// Must give JPA a no-args constructor
	public BlogEntry() {}
	
	
	// Define a constructor for making BlogEntries from scratch:
	// Make sure to leave out ID because we're leaving that up to SpringBoot
	public BlogEntry(String author, String title, String blogContent) {
		this.author = author;
		this.title = title;
		this.blogContent = blogContent;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getBlogContent() {
		return blogContent;
	}


	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}


	@Override
	public String toString() {
		return "BlogEntry [id=" + id + ", author=" + author + ", title=" + title + ", blogContent=" + blogContent + "]";
	}	
	
	
}
