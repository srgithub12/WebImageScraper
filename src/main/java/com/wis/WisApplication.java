package com.wis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class WisApplication {

	public static void main(String[] args) {

		SpringApplication.run(WisApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	private void init() throws Exception {
	}

}
