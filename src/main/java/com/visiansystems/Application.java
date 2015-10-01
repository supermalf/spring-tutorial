package com.visiansystems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// --> Adds: @Configuration, @EnableAutoConfiguration, @ComponentScan
public class Application {

    /**
     * Entry point for the application.
     *
     * @param args Command line arguments.
     * @throws Exception Thrown when an unexpected Exception is thrown from the
     *                   application.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
