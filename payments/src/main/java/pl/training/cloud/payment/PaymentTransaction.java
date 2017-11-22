package pl.training.cloud.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaymentTransaction {


    private String id;
    private Long amount;
}
