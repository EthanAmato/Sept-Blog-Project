package com.tts.blogproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// This class will be responsible for taking in requests from the user and routing them accordingly
// e.g. If they send a post request to /new, create a new blog entry using the body sent with that request
// or if they send a get request to /, display the home page

// Thinking back to yesterday, we tell spring boot that a class will be responsible for handling
// requests and serving up 'views' (what the user will eventually see on their screen) through the
// @Controller annotation:

@Controller
public class BlogEntryController {
	
	// Listening in for Get requests to "/" route, Spring Boot will
	// send the user to the index.html file found in either the blogEntry folder inside of
	// static or templates:
	@GetMapping("/")
	public String testHome() {
		return "blogEntry/index.html";
	}
	
	
}
