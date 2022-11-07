package com.example.test_task.implementation;

import com.example.test_task.service.BookService;
import com.example.test_task.entiy.Book;
import com.example.test_task.exception_handling.DuplicateBookException;
import com.example.test_task.exception_handling.NoAnyBookException;
import com.example.test_task.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        Long id = book.getId();
        Optional<Book> bookFromDb = bookRepository.findById(id);
        if(bookFromDb.isPresent()){
            throw new DuplicateBookException("Book with id = " + id + " already exists");
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        if(bookList.size() == 0){
            throw new NoAnyBookException("No books found");
        }
        return bookList;
    }

    @Override
    public void delete(Long id) {
        Optional<Book> bookFromDb = bookRepository.findById(id);
        if(!bookFromDb.isPresent()){
            throw new NoAnyBookException("No book found");
        }
        bookRepository.deleteById(id);
    }

    @Cacheable(value = "libraryCache", key = "#id")
    @Override
    public Book findById(Long id) {
        Optional<Book> bookFromDb = bookRepository.findById(id);
        if(!bookFromDb.isPresent()){
            throw new NoAnyBookException("No books found");
        }
        log.info("getting book from database: ");
        return bookFromDb.get();
    }

    @Cacheable(value = "libraryCache", key = "#genre")
    @Override
    public List<Book> listBookByGenre(String genre) {
        List<Book> bookListFromDb = bookRepository.getBooksByGenre(genre);
        if(bookListFromDb.size() == 0){
            throw new NoAnyBookException("No books found");
        }
        log.info("getting book from database: ");
        return bookListFromDb;
    }

    @Cacheable(value = "libraryCache", key = "#publicationDate")
    @Override
    public List<Book> listBookByPublicationDate(LocalDate date) {
        List<Book> bookListFromDb = bookRepository.getBooksByPublicationDate(date);
        if(bookListFromDb.size() == 0){
            throw new NoAnyBookException("No books found");
        }
        log.info("getting book from database: ");
        return bookListFromDb;
    }

    @Cacheable(value = "libraryCache", key = "#author")
    @Override
    public List<Book> listBookByAuthor(String author) {
        List<Book> bookListFromDb = bookRepository.getBooksByAuthor(author);
        if(bookListFromDb.size() == 0){
            throw new NoAnyBookException("No books found");
        }
        log.info("getting book from database: ");
        return bookListFromDb;
    }

    @Cacheable(value = "libraryCache", key = "#title")
    @Override
    public Book findBookByTitle(String title) {
        Optional<Book> bookFromDb = Optional.ofNullable(bookRepository.getBookByTitle(title));
        if(!bookFromDb.isPresent()){
            throw new NoAnyBookException("No books found");
        }
        log.info("getting book from database: ");
        return bookFromDb.get();
    }
}
