package pl.training.cloud.payment;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class EventEmitter {

    private Source source;

    public EventEmitter(Source source) {
        this.source = source;
    }

    public void emit(TransactionMessage transactionMessage) {
        System.out.println("### Sending message " + transactionMessage);
        source.output().send(MessageBuilder
                .withPayload(transactionMessage)
                .build());
    }

}
