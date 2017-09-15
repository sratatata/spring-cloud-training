package pl.training.cloud.departments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import pl.training.cloud.departments.model.Message;

@EnableBinding(Sink.class)
@SpringBootApplication
public class DepartmentsMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentsMicroservice.class);
    }

    @StreamListener(Sink.INPUT)
    public void onMessage(Message message) {
        System.out.println("New message: " + message);
    }

}
