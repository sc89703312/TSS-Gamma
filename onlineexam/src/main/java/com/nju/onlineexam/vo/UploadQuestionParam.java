package com.nju.onlineexam.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by sbin on 2017/12/13.
 */
@Data
public class UploadQuestionParam {

    @NotBlank
    String questionFile;

}
