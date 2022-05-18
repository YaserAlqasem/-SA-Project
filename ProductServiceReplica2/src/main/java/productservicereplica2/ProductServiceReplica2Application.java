package productservicereplica2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableEurekaClient
@EnableKafka
public class ProductServiceReplica2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceReplica2Application.class, args);
	}

}
