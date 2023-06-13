package com.nick.todocliapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
public class TodoCliAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoCliAppApplication.class, args);
    }

}
