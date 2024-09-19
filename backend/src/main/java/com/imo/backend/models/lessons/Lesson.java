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

    private String title;

    private String description;

    private String youtubeLink;

    private LocalDateTime uploadedAt;

    public void formatLink() {
        int position = youtubeLink.indexOf("&");
        if (position != -1) {
            youtubeLink = youtubeLink.substring(0, position);
        }
    }
}
