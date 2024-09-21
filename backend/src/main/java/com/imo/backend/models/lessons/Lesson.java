package com.imo.backend.models.lessons;

import lombok.Data;
import java.time.LocalDateTime;

@Data
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
