package pl.training.cloud.users.service;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.training.cloud.users.model.Message;

@Service
public class EventEmitter {

    private Source source;

    public EventEmitter(Source source) {
        this.source = source;
    }

    public void emit(Message message) {
        System.out.println("### Sending message " + message);
        source.output().send(MessageBuilder
                .withPayload(message)
                .build());
    }

}
