package com.saga.choreographerM;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Import({KafkaConfig.class})
public class ChoreographerMApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChoreographerMApplication.class, args);
	}

}
