package ir.wallet.clientInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClientInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientInfoApplication.class, args);
	}

}
