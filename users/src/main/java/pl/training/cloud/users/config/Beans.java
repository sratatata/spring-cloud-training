package pl.training.cloud.users.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import pl.training.cloud.users.model.PaymentTransaction;

@EnableBinding({Source.class, Sink.class})

@ComponentScan(basePackages = "pl.training.cloud.common.config")
@Configuration
public class Beans {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @StreamListener(Sink.INPUT)
    public void onMessage(PaymentTransaction message) {
        System.out.println("New message: " + message);
    }
}
