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

    public void setYoutubeLink(String youtubeLink) {
        int left = youtubeLink.indexOf("=");
        int right = youtubeLink.indexOf("&");

        if (right != -1) {
            this.youtubeLink = youtubeLink.substring(left + 1, right);
            return;
        }

        if (left != -1) {
            this.youtubeLink = youtubeLink.substring(left + 1);
            return;
        }

        this.youtubeLink = youtubeLink;
    }
}
