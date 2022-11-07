package com.example.test_task.repository;

import com.example.test_task.entiy.NewsPaper;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NewsPaperRepository extends CrudRepository<NewsPaper, Long> {

    @Query("SELECT * FROM news_papers WHERE genre = :genre")
    List<NewsPaper> getNewsPapersByGenre(String genre);

    @Query("SELECT * FROM news_papers WHERE publication_Date = :date")
    List<NewsPaper> getNewsPapersByPublicationDate(LocalDate date);

    @Query("SELECT * FROM news_papers WHERE title = :title")
    NewsPaper getNewsPaperByTitle(String title);
}
