package com.tts.blogproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.blogproject.models.BlogEntry;
import com.tts.blogproject.repositories.BlogEntryRepository;

// This class will be responsible for taking in requests from the user and routing them accordingly
// e.g. If they send a post request to /new, create a new blog entry using the body sent with that request
// or if they send a get request to /, display the home page

// Thinking back to yesterday, we tell spring boot that a class will be responsible for handling
// requests and serving up 'views' (what the user will eventually see on their screen) through the
// @Controller annotation:

@Controller
public class BlogEntryController {
	
	
	@Autowired
	BlogEntryRepository blogEntryRepository;
	
	
	// Listening in for Get requests to "/" route, Spring Boot will
	// send the user to the index.html file found in either the blogEntry folder inside of
	// static or templates:
	
	// Remember that the main difference between templates and 
	// static is that templates are for 'filling out' with data
	// whereas static html / files will remain 'static' and cannot
	// be modified with data
	
	// To pass data from a controller method to a thymleaf
	// template, we can include a 'Model' object to our
	// method's input arguments. The 'Model' object
	// kind of acts like a direct line of communication
	// between our controller and our view - allowing us
	// to specify key-value pairs in our java code that
	// can be rendered into our Thymeleaf HTML:
	@GetMapping("/test")
	public String testHome(Model model) {
		
		// To add a key value pair of data to our model,
		// so we can render code to our HTML, we can call
		// the addAttribute Method on our mode:
		model.addAttribute("message", "Hello from our controller");
		
		// We are going to be able to fill out templates like
		// index.html using Thymeleaf
		
		return "blogEntry/test.html";
	}
	
	
	@GetMapping("/")
	public String home() {
		
		return "blogEntry/index.html";
	}
	
	@GetMapping("/new")
	public String newPost(Model model) {
		BlogEntry blogEntry = new BlogEntry();
		model.addAttribute("blogEntry", blogEntry);
		
		return "blogEntry/new.html";
	}
	
	
	@PostMapping("/blogEntries")
	public String createNewBlogEntry(BlogEntry blogEntry) {
		System.out.println(blogEntry);
		BlogEntry dbBlogEntry = blogEntryRepository.save(blogEntry);
		
		System.out.println(dbBlogEntry);
		return "blogEntry/new.html";
	}
	
	
	
	
	
	
}
