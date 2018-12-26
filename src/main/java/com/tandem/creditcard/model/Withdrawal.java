package com.tandem.creditcard.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class Withdrawal {
    private @Id
    UUID id;
    private UUID cardId;
    private BigDecimal amount;

    public Withdrawal(UUID id, UUID cardId, BigDecimal amount) {
        this.id = id;
        this.cardId = cardId;
        this.amount = amount;
    }

    Withdrawal() {

    }
}