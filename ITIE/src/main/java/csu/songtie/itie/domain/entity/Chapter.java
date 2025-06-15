package csu.songtie.itie.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Chapter {
    private int chapterId;
    private int courseId;
    private int chapterSortOrder;
    private String title;
    private String description;
    private int lessonNum;
    private int lessonTotalMinute;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
    private String unused;
}
