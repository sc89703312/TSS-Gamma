package com.nju.onlineexam.model;

import lombok.Data;

@Data
public class ResponseBase {
    private String errorMsg;
    private String errorDetail;
    private String result;

    public ResponseBase (String result , String errorMsg ,String errorDetail){
        this.result = result;
        this.errorMsg = errorMsg;
        this.errorDetail = errorDetail;
    }
}
