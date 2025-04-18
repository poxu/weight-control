package takred.weightcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class WeightControlApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(WeightControlApplication.class, args);
	}

}
