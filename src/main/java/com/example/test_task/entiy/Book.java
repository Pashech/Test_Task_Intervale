package com.example.test_task.entiy;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table(name = "BOOKS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book implements Persistable<Long>, Serializable {

    @Id
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String genre;


    @Override
    public boolean isNew() {
        return true;
    }
}
