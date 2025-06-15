package csu.songtie.itie.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseListVO {
    private int courseId;

    private String coverImgUrl;
    private String title;
//    private double score;
    private double originalPrice;
    private double currentPrice;

    private LocalDateTime updateTime;
    private int totalMinutes;

    private String highlights;
    private String whatYouWillLearn;
}
