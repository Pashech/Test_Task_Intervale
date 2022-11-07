package com.example.test_task.integration;

import com.example.test_task.entiy.Book;
import com.example.test_task.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;

@AutoConfigureMockMvc
@SpringBootTest
public class BookIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private Book book;

    @AfterEach
    public void resetDb(){
        bookRepository.deleteAll();
    }

    @BeforeEach
    public void createBook1(){
        book = new Book();
        book.setId(1L);
        book.setAuthor("Bruce Ekkel");
        book.setTitle("Effective Java");
        book.setPublicationDate(LocalDate.of(2022, 12, 12));
        book.setGenre("fantastic");
    }

    @Test
    public void createTest() throws Exception{

        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.author").value("Bruce Ekkel"));
    }

    @Test
    public void getBookById() throws Exception {
        bookRepository.save(book);
        mockMvc.perform(get("/books/id/{id}", book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()));
    }

    @Test
    public void deleteTest() throws Exception {
        bookRepository.save(book);
        mockMvc.perform(delete("/books/{id}", book.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getBooksByGenreTest() throws Exception {
        bookRepository.save(book);

        mockMvc.perform(get("/books/genre/{genre}", book.getGenre()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].genre").value("fantastic"));
    }

    @Test
    public void getBooksByAuthorTest() throws Exception {
        bookRepository.save(book);

        mockMvc.perform(get("/books/author/{author}", book.getAuthor()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Bruce Ekkel"));
    }

    @Test
    public void getBooksByPublicationDateTest() throws Exception {
        bookRepository.save(book);

        mockMvc.perform(get("/books/publicationDate/{date}", book.getPublicationDate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].publicationDate").value("2022-12-12"));
    }

    @Test
    public void getBooksByTitleTest() throws Exception {
        bookRepository.save(book);

        mockMvc.perform(get("/books/title/{title}", book.getTitle()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }
}
