package csu.songtie.itie.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("course")
public class Course {
    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;
    @TableField(value = "course_id")
    private int courseId;
    @TableField(value = "category_id")
    private int categoryId;
    @TableField(value = "tag_id")
    private int tagId;
    @TableField(value = "category_name")
    private String categoryName;
    @TableField(value = "tag_name")
    private String tagName;
    @TableField(value = "cover_img_url")
    private String coverImgUrl;
    private String title;
    private double score;
    private String highlights;
    @TableField(value = "what_you_will_learn")
    private String whatYouWillLearn;
    @TableField(value = "chapter_num")
    private int chapterNum;
    @TableField(value = "lesson_num")
    private int lessonNum;
    @TableField(value = "total_minutes")
    private int totalMinutes;
    private String description;
    @TableField(value = "original_price")
    private double originalPrice;
    @TableField(value = "current_price")
    private double currentPrice;
    @TableField(value = "discount_date")
    private LocalDateTime discountDate;
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
    private String unused;
}
