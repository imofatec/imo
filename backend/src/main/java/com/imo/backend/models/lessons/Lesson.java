package com.imo.backend.models.lessons;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Lesson {

    private int index;

    @NotBlank(message = "Preencha o titulo da aula")
    private String title;

    @NotBlank(message = "Preencha a descição da aula")
    private String description;

    @NotBlank(message = "Preencha o link da aula")
    private String youtubeLink;

    @CreatedDate
    private LocalDateTime uploadedAt;

    public void formatLink() {
            int position = youtubeLink.indexOf("&");
            if(position != -1){
                youtubeLink = youtubeLink.substring(0, position);
            }
        }
    }

