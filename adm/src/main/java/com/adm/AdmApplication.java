package com.adm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AdmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdmApplication.class, args);
	}

	@RestController
    class HelloController {
        @GetMapping("/api/hello")
        public String hello() {
            return "Hello, REST API!";
        }

        @GetMapping("/api/hello-json")
        public Message helloJson() {
            return new Message("Hello, JSON REST API!");
        }
    }

    class Message {
        private String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


}
