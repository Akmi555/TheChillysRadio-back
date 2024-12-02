package theChillys.chillys_radio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //turns on async operations in project
public class ChillysRadioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChillysRadioApplication.class, args);
	}

}
