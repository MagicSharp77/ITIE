package csu.songtie.itie.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Course {
    private int courseId;
    private int categoryId;
    private int tagId;
    private String categoryName;
    private String tagName;
    private String coverImgUrl;
    private String title;
    private double score;
    private String highlights;
    private String whatYouWillLearn;
    private int chapterNum;
    private int lessonNum;
    private int totalMinutes;
    private String description;
    private double originalPrice;
    private double currentPrice;
    private LocalDateTime discountDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
    private String unused;
}
