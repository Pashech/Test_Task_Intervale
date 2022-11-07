package com.example.test_task.controller;

import com.example.test_task.service.BookService;
import com.example.test_task.entiy.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    @Autowired
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book){
        return new ResponseEntity<>(bookService.create(book), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> getAllBooks(){
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        bookService.delete(id);
        return new ResponseEntity<>("Book with id = " + id + " deleted successfully", HttpStatus.OK);
    }

    @GetMapping(path = "/genre/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String genre){
        return new ResponseEntity<>(bookService.listBookByGenre(genre), HttpStatus.OK);
    }

    @GetMapping(path = "/publicationDate/{date}")
    public ResponseEntity<List<Book>> getBooksByPublicationDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return new ResponseEntity<>(bookService.listBookByPublicationDate(date), HttpStatus.OK);
    }

    @GetMapping(path = "/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author){
        return new ResponseEntity<>(bookService.listBookByAuthor(author), HttpStatus.OK);
    }

    @GetMapping(path = "/title/{title}")
    public ResponseEntity<Book> getBooksByTitle(@PathVariable String title){
        return new ResponseEntity<>(bookService.findBookByTitle(title), HttpStatus.OK);
    }
}
