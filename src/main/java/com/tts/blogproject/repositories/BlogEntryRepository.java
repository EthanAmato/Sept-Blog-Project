package com.tts.blogproject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tts.blogproject.models.BlogEntry;

// This is going to serve as an object that we let SpringBoot make (through inversion of control)
// and interact with the BlogEntry table of our repository:

// Need to provide CrudRepository with 2 generics:
// 1. The type of objects to interact with from the DB table (BlogEntry)
// 2. The type of the Primary Key of said object (Long because the Id instance variable in BlogEntry is Long)
public interface BlogEntryRepository extends CrudRepository<BlogEntry, Long> {

	// Have findById return an optional which will serve as a container
	// for a BlogEntry if the id passed to this method exists in the DB,
	// if not Optional will contain null
	@Override
	Optional<BlogEntry> findById(Long id);

	// Override the findAll() method which, by default, just returns an Iterable<T>
	// type, but
	// we want it to specifically send back a List (of blog entries)
	@Override
	List<BlogEntry> findAll();

}
