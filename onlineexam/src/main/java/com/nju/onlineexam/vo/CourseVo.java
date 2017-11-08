package com.nju.onlineexam.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CourseVo {

    int id;

    @NotBlank
    String name;

}
