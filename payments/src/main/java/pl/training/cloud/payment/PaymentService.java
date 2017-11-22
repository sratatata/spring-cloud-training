package pl.training.cloud.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private EventEmitter eventEmitter;

    public PaymentService(EventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    public void addPaymentTransaction(String paymentId, Long paymentAmount) {
        PaymentTransaction paymentTransaction = new PaymentTransaction(paymentId, paymentAmount);
        notifyAboutPayment(paymentTransaction);
    }


    private void notifyAboutPayment(PaymentTransaction paymentTransaction) {
        String text = String.format("Payment");
        TransactionMessage transactionMessage = new TransactionMessage(paymentTransaction.getId(), text);
        eventEmitter.emit(transactionMessage);
    }

}
