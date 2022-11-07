package com.example.test_task.entiy;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "NEWS_PAPERS")
public class NewsPaper implements Persistable<Long> {

    @Id
    private Long id;
    private String title;
    private LocalDate publicationDate;
    private String genre;

    @Override
    public boolean isNew() {
        return true;
    }
}
