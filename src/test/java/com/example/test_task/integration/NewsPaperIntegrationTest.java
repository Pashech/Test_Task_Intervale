package com.example.test_task.integration;

import com.example.test_task.entiy.NewsPaper;
import com.example.test_task.repository.NewsPaperRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
public class NewsPaperIntegrationTest {

    @Autowired
    private NewsPaperRepository newsPaperRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private NewsPaper newsPaper;

    @AfterEach
    public void resetDb(){
        newsPaperRepository.deleteAll();
    }

    @BeforeEach
    public void createNewsPaper(){
        newsPaper = new NewsPaper();
        newsPaper.setId(1L);
        newsPaper.setTitle("NY Times");
        newsPaper.setPublicationDate(LocalDate.of(2022, 12, 12));
        newsPaper.setGenre("news");
    }

    @Test
    public void createTest() throws Exception{

        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(newsPaper);

        mockMvc.perform(post("/newsPapers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("NY Times"));
    }

    @Test
    public void getNewsPaperById() throws Exception {
        newsPaperRepository.save(newsPaper);
        mockMvc.perform(get("/newsPapers/id/{id}", newsPaper.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newsPaper.getId()));
    }

    @Test
    public void deleteTest() throws Exception {
        newsPaperRepository.save(newsPaper);
        mockMvc.perform(delete("/newsPapers/{id}", newsPaper.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getNewsPaperByGenreTest() throws Exception {
        newsPaperRepository.save(newsPaper);

        mockMvc.perform(get("/newsPapers/genre/{genre}", newsPaper.getGenre()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].genre").value("news"));
    }


    @Test
    public void getNewsPaperByPublicationDateTest() throws Exception {
        newsPaperRepository.save(newsPaper);

        mockMvc.perform(get("/newsPapers/publicationDate/{date}", newsPaper.getPublicationDate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].publicationDate").value("2022-12-12"));
    }

    @Test
    public void getNewsPaperByTitleTest() throws Exception {
        newsPaperRepository.save(newsPaper);

        mockMvc.perform(get("/newsPapers/title/{title}", newsPaper.getTitle()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("NY Times"));
    }
}
