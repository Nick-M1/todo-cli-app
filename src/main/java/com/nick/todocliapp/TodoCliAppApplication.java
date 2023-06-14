package com.nick.todocliapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

/**
 * Note to self: More info about Spring-Shell at:
 * - https://github.com/dmadunic/clidemo/tree/master
 * - https://medium.com/agency04/developing-cli-application-with-spring-shell-part-3-b4c247fdf558
 * - https://docs.spring.io/spring-shell/docs/3.1.0/docs/index.html#using-shell-components
 */

@SpringBootApplication
@CommandScan
public class TodoCliAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoCliAppApplication.class, args);
    }

}
