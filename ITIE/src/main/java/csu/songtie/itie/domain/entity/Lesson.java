package csu.songtie.itie.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Lesson {
    private int lessonId;
    private int courseId;
    private int chapterSortOrder;
    private int lessonSortOrder;
    private String title;
    private boolean isPreviewable;
    private int totalSeconds;
    private String coverImgUrl;
    private String videoUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
    private String unused;
}
