package csu.songtie.itie.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonListVO {
    private int lessonId;
    private int chapterSortOrder;
    private int lessonSortOrder;
    private String title;
    private boolean isPreviewable;
    private int totalSeconds;
}
