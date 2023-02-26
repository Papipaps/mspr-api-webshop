package fr.mspr.webshop;

import fr.mspr.webshop.data.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@RestController
@RequestMapping("api/webshop")
public class DemoApplication {

	private final WebClient webClient;
	public DemoApplication(WebClient webClient) {
		this.webClient = webClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping
	public String hello(){
		return "Hello from webshop !";
	}

}
