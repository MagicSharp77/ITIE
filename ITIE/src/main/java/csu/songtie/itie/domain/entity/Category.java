package csu.songtie.itie.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private int categoryId;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
    private String unused;
}
