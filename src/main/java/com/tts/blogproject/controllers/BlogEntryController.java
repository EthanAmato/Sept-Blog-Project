package com.tts.blogproject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String home(Model model) {
		
		// Call the findAll() method on our blogEntryRepository
		// which will return all of our blog posts from our DB
		// inside of a List
		List<BlogEntry> blogEntries = blogEntryRepository.findAll();
		
		// We then will add these blogEntries to our model so that we 
		// can access them from our HTML/Thymeleaf template:
		model.addAttribute("blogEntries", blogEntries);
		
		return "blogEntry/index.html";
	}
	
	@GetMapping("/new")
	public String newPost(Model model) {
		BlogEntry blogEntry = new BlogEntry();
		model.addAttribute("blogEntry", blogEntry);
		
		return "blogEntry/new.html";
	}
	
	
	@PostMapping("/blogEntries")
	public String createNewBlogEntry(BlogEntry blogEntry, Model model) {
		System.out.println(blogEntry);
		BlogEntry dbBlogEntry = blogEntryRepository.save(blogEntry);
		
		model.addAttribute("newBlogEntry", dbBlogEntry);
		
		
		System.out.println(dbBlogEntry);
		return "blogEntry/result.html";
	}
	
	
	
	
	
	// Responds to DELETE requests that we send from our HTML
	// Remember, the @PathVariable annotation allows us to acquire data
	// from the URL the user goes to. For example, in this case, if a user
	// sends a DELETE request to the link http://localhost:8080/blogEntries/4, the 'id' variable
	// in our deletePostById method will take on the value of 4
	@DeleteMapping("/blogEntries/{id}")
	public String deletePostById(@PathVariable Long id, Model model) {
		
		// First, we should check to see if the entry with id equal to the URL id exists in our database:
		Optional<BlogEntry> blogEntryById = blogEntryRepository.findById(id);
		
		// Check to see if there is actually a blogEntry with id=id using the .isPresent() method
		// from the Optional class:
		if(blogEntryById.isPresent()) {
			// We will get the blogEntry from our Optional variable using the .get() method:
			BlogEntry blogEntry = blogEntryById.get();
			// Add the blogEntry we got from DB as data to render in our view under the name 'deletedBlogEntry'
			model.addAttribute("deletedBlogEntry", blogEntry);
			
			// Lastly, we will delete our blogEntry from the database:
			blogEntryRepository.deleteById(id);
		}
		
		return "blogEntry/delete.html";
	}
	
	
	// Get mapping so the user will be confronted with some UI upon pushing the 'edit this entry' button
	// so that they can fill out a form to modify an existing post:
	@GetMapping("/blogEntries/{id}")
	public String editPostWithId(@PathVariable Long id, Model model) {
		// Just like in the Delete mapping, check to see if blog Entry exists in DB:
		Optional<BlogEntry> blogEntryById = blogEntryRepository.findById(id);
		
		if(blogEntryById.isPresent()) {
			BlogEntry blogEntry = blogEntryById.get();
			
			model.addAttribute("blogEntry", blogEntry);
		}	
		
		return "blogEntry/edit.html";
	}
	
	// This is listening for PATCH requests from our thymeleaf.
	// It will take in the updated BlogEntry object from the form as a request body
	// and will try to update the database entry accordingly:
	@PatchMapping("/blogEntries/{id}")
	public String updatePostById(@PathVariable Long id, BlogEntry blogEntry, Model model) {
		
		Optional<BlogEntry> blogEntryById = blogEntryRepository.findById(id);

		if(blogEntryById.isPresent()) {
			// This is the current state of the blog entry as it exists in the database,
			// whereas blogEntry (that we got from the Patch request) is the 'updated'
			// version we want to turn the db Object into
			BlogEntry dbBlogEntry = blogEntryById.get();
		
			System.out.println(dbBlogEntry);
			
			dbBlogEntry.setAuthor(blogEntry.getAuthor());
			dbBlogEntry.setTitle(blogEntry.getTitle());
			dbBlogEntry.setBlogContent(blogEntry.getBlogContent());
			model.addAttribute("newBlogEntry", dbBlogEntry);

			blogEntryRepository.save(dbBlogEntry);
		
		}
		
		return "blogEntry/result.html";
	}
	
	
	
	
	
}
