package pl.training.cloud.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaymentTransaction {


    private String id;
    private Long amount;
}
