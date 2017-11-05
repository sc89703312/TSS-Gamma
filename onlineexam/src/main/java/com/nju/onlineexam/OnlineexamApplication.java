package com.nju.onlineexam;

import com.nju.onlineexam.dao.TestTableRepo;
import com.nju.onlineexam.entity.TestTableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;


@SpringBootApplication
public class OnlineexamApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineexamApplication.class, args);
	}

}
