package com.example.test_task.implementation;

import com.example.test_task.entiy.NewsPaper;
import com.example.test_task.exception_handling.DuplicateNewsPaperException;
import com.example.test_task.exception_handling.NoAnyNewsPaperException;
import com.example.test_task.repository.NewsPaperRepository;
import com.example.test_task.service.NewsPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NewsPaperServiceImpl implements NewsPaperService {

    @Autowired
    private NewsPaperRepository newsPaperRepository;

    @Override
    public NewsPaper create(NewsPaper newsPaper) {
        Long id = newsPaper.getId();
        Optional<NewsPaper> bookFromDb = newsPaperRepository.findById(id);
        if(bookFromDb.isPresent()){
            throw new DuplicateNewsPaperException("NewsPaper with id = " + id + " already exists");
        }
        return newsPaperRepository.save(newsPaper);
    }

    @Override
    public List<NewsPaper> findAllNewsPapers() {
        List<NewsPaper> newsPaperList = (List<NewsPaper>) newsPaperRepository.findAll();
        if(newsPaperList.size() == 0){
            throw new NoAnyNewsPaperException("No newspapers found");
        }
        return newsPaperList;
    }

    @Override
    public void delete(Long id) {
        Optional<NewsPaper> newsPaperFromDb = newsPaperRepository.findById(id);
        if(!newsPaperFromDb.isPresent()){
            throw new NoAnyNewsPaperException("No newspapers found");
        }
        newsPaperRepository.deleteById(id);
    }

    @Cacheable(value = "libraryCache", key = "#id")
    @Override
    public NewsPaper findById(Long id) {
        Optional<NewsPaper> newsPaperFromDb = newsPaperRepository.findById(id);
        if(!newsPaperFromDb.isPresent()){
            throw new NoAnyNewsPaperException("No newspapers found");
        }
        log.info("getting newspaper from database: ");
        return newsPaperFromDb.get();
    }

    @Cacheable(value = "libraryCache", key = "#genre")
    @Override
    public List<NewsPaper> listNewsPapersByGenre(String genre) {
        List<NewsPaper> newsPaperFromDb = newsPaperRepository.getNewsPapersByGenre(genre);
        if(newsPaperFromDb.size() == 0){
            throw new NoAnyNewsPaperException("No newspapers found");
        }
        log.info("getting newspaper from database: ");
        return newsPaperFromDb;
    }

    @Cacheable(value = "libraryCache", key = "#publicationDate")
    @Override
    public List<NewsPaper> listNewsPapersByPublicationDate(LocalDate date) {
        List<NewsPaper> newsPaperFromDb = newsPaperRepository.getNewsPapersByPublicationDate(date);
        if(newsPaperFromDb.size() == 0){
            throw new NoAnyNewsPaperException("No newspaper found");
        }
        log.info("getting newspaper from database: ");
        return newsPaperFromDb;
    }

    @Cacheable(value = "libraryCache", key = "#title")
    @Override
    public NewsPaper findNewsPaperByTitle(String title) {
        Optional<NewsPaper> newsPaperFromDb = Optional.ofNullable(newsPaperRepository.getNewsPaperByTitle(title));
        if(!newsPaperFromDb.isPresent()){
            throw new NoAnyNewsPaperException("No newspapers found");
        }
        log.info("getting newspaper from database: ");
        return newsPaperFromDb.get();
    }
}
