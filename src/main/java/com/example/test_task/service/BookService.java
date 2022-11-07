package com.example.test_task.service;

import com.example.test_task.entiy.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    Book create(Book book);

    Iterable<Book> findAllBooks();

    void delete(Long id);

    Book findById(Long id);

    List<Book> listBookByGenre(String genre);

    List<Book> listBookByPublicationDate(LocalDate date);

    List<Book> listBookByAuthor(String author);

    Book findBookByTitle(String title);

}
