package com.imo.backend.models.lessons;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Lesson {

    private int index;

    @NotBlank(message = "Preencha o titulo da aula")
    @Size(min = 10, max = 50, message = "O título da aula precisa ter entre 10 e 50 caracteres")
    private String title;

    @Size(max = 600, message = "A descrição da aula pode ter no máximo 600 caracteres")
    private String description;

    @NotBlank(message = "Preencha o link da aula")
    private String youtubeLink;

    private LocalDateTime uploadedAt;

    public void formatLink() {
            int position = youtubeLink.indexOf("&");
            if(position != -1){
                youtubeLink = youtubeLink.substring(0, position);
            }
        }
    }

