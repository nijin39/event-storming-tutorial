package com.coupang.elba.creditcard.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class CardWithdrawn implements DomainEvent {

    private final UUID cardNo;
    private final BigDecimal money;
    private final Instant timestamp;

    public CardWithdrawn(UUID cardNo, BigDecimal money, Instant timestamp) {
        this.cardNo = cardNo;
        this.money = money;
        this.timestamp = timestamp;
    }

    @Override
    public String getType() {
        return "card-withdrawn";
    }
}
