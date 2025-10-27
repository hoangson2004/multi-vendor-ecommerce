package hust.hoangson.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication(scanBasePackages = {
        "hust.hoangson.order",
        "hust.hoangson.common.kafka",
        "hust.hoangson.clients"
})
@EnableFeignClients(basePackages = "hust.hoangson.clients")
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
