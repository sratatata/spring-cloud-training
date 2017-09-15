package pl.training.cloud.departments.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import pl.training.cloud.departments.model.Message;

@EnableBinding(Sink.class)
@ComponentScan(basePackages = "pl.training.cloud.common")
@Configuration
public class Beans {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("utf-8");
        messageSource.setBasename("exceptions");
        return messageSource;
    }

    @StreamListener(Sink.INPUT)
    public void onMessage(Message message) {
        System.out.println("New message: " + message);
    }


}
