package com.nju.onlineexam.vo;

import lombok.Data;

@Data
public class UserVo {

    int id;
    int type;
    String email;
    String name;

    public UserVo(int id, int type , String email ,String name){
        this.id = id;
        this.type = type;
        this.email = email;
        this.name = name;
    }
}
