package com.imo.backend.models.lessons;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class Lesson {

    @Id
    private String id;

    @NotBlank(message = "Preencha o titulo da aula")
    private String title;

    @NotBlank(message = "Preencha a descição da aula")
    private String description;

    @NotBlank(message = "Preencha o link da aula")
    private String youtubeLink;

    @CreatedDate
    private LocalDateTime uploadedAt;

}
