package cn.edu.nbpt.facenet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FacenetApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacenetApplication.class, args);
	}

}
