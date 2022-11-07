package com.example.test_task.service;

import com.example.test_task.entiy.NewsPaper;

import java.time.LocalDate;
import java.util.List;

public interface NewsPaperService {

    NewsPaper create(NewsPaper newsPaper);

    Iterable<NewsPaper> findAllNewsPapers();

    void delete(Long id);

    NewsPaper findById(Long id);

    List<NewsPaper> listNewsPapersByGenre(String genre);

    List<NewsPaper> listNewsPapersByPublicationDate(LocalDate date);

    NewsPaper findNewsPaperByTitle(String title);
}
