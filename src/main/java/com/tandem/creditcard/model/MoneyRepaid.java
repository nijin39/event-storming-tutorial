package com.tandem.creditcard.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class MoneyRepaid implements DomainEvent {
    private final UUID uuid;
    private final BigDecimal money;
    private final Instant timestamp;

    public MoneyRepaid(UUID uuid, BigDecimal money, Instant timestamp) {
        this.uuid = uuid;
        this.money = money;
        this.timestamp = timestamp;
    }

    @Override
    public String getType() {
        return "card-repaid";
    }
}
