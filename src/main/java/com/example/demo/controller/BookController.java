package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.repo.BookRepository;

@RestController
public class BookController {
	@Autowired
	private BookRepository repo;

	
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks() {
		try {
			List<Book> bookList=new ArrayList<>();
			repo.findAll().forEach(bookList::add);
			if(bookList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(bookList,HttpStatus.OK);	
	}
		catch(Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
@GetMapping("/getBookById/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Optional<Book> bookData=repo.findById(id);
		if(bookData.isPresent()) {
			return new ResponseEntity<>(bookData.get(),HttpStatus.OK);	
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
@PostMapping("/book")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Book bookObj=repo.save(book);
		return new ResponseEntity<>(bookObj,HttpStatus.OK);
	}
	
@PutMapping("updateBook/{id}")
public ResponseEntity<Book> updateBookById(@PathVariable Long id,@RequestBody Book newBook) {
	Optional<Book> oldBookData=repo.findById(id);
	if(oldBookData.isPresent()) {
		Book updateBookData=oldBookData.get();
		updateBookData.setTitle(newBook.getTitle());
		updateBookData.setAuthor(newBook.getAuthor());
		Book bookObj=repo.save(newBook);
		return new ResponseEntity<>(bookObj,HttpStatus.OK);
		
	}
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id) {
	repo.deleteById(id); 
	return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
