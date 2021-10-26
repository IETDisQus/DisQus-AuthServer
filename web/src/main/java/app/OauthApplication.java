package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"config","controller"})
public class OauthApplication {
	
	public static void main(String args[]) {
		SpringApplication.run(OauthApplication.class, args);
	}

}
