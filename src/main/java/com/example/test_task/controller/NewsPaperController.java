package com.example.test_task.controller;

import com.example.test_task.entiy.NewsPaper;
import com.example.test_task.service.NewsPaperService;
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
@RequestMapping("/newsPapers")
public class NewsPaperController {

    @Autowired
    private final NewsPaperService newsPaperService;

    @PostMapping
    public ResponseEntity<NewsPaper> create(@RequestBody NewsPaper newsPaper){
        return new ResponseEntity<>(newsPaperService.create(newsPaper), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<NewsPaper>> getAllNewsPapers(){
        return new ResponseEntity<>(newsPaperService.findAllNewsPapers(), HttpStatus.OK);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<NewsPaper> getNewsPaperById(@PathVariable Long id){
        return new ResponseEntity<>(newsPaperService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        newsPaperService.delete(id);
        return new ResponseEntity<>("Newspaper with id = " + id + " deleted successfully", HttpStatus.OK);
    }

    @GetMapping(path = "/genre/{genre}")
    public ResponseEntity<List<NewsPaper>> getNewsPaperByGenre(@PathVariable String genre){
        return new ResponseEntity<>(newsPaperService.listNewsPapersByGenre(genre), HttpStatus.OK);
    }

    @GetMapping(path = "/publicationDate/{date}")
    public ResponseEntity<List<NewsPaper>> getNewsPaperByPublicationDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return new ResponseEntity<>(newsPaperService.listNewsPapersByPublicationDate(date), HttpStatus.OK);
    }

    @GetMapping(path = "/title/{title}")
    public ResponseEntity<NewsPaper> getNewsPapersByTitle(@PathVariable String title){
        return new ResponseEntity<>(newsPaperService.findNewsPaperByTitle(title), HttpStatus.OK);
    }
}
