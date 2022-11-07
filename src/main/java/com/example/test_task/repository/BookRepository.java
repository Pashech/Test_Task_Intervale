package com.example.test_task.repository;

import com.example.test_task.entiy.Book;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository  extends CrudRepository<Book, Long> {

    @Query("SELECT * FROM books WHERE genre = :genre")
    List<Book> getBooksByGenre(String genre);

    @Query("SELECT * FROM books WHERE publication_Date = :date")
    List<Book> getBooksByPublicationDate(LocalDate date);

    @Query("SELECT * FROM books WHERE author = :author")
    List<Book> getBooksByAuthor(String author);

    @Query("SELECT * FROM books WHERE title = :title")
    Book getBookByTitle(String title);


}
