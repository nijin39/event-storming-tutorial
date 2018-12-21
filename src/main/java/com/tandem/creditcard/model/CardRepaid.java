package com.tandem.creditcard.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class CardRepaid implements DomainEvent {
    private final UUID uuid;
    private final BigDecimal money;
    private final Instant timestamp;

    public CardRepaid(UUID uuid, BigDecimal money, Instant timestamp) {
        this.uuid = uuid;
        this.money = money;
        this.timestamp = timestamp;
    }

    @Override
    public String getType() {
        return "card-repaid";
    }
}
