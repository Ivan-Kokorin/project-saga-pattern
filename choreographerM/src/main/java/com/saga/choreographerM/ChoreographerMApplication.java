package com.saga.choreographerM;

import com.saga.choreographerM.config.KafkaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({KafkaConfig.class})
public class ChoreographerMApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChoreographerMApplication.class, args);
	}

}
