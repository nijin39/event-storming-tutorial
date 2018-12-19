package com.coupang.elba.creditcard.persistance;

import com.coupang.elba.creditcard.model.CreditCard;
import com.coupang.elba.creditcard.model.DomainEvent;
import org.springframework.stereotype.Repository;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.GenericMessage;

import java.util.*;

@Repository
public class CreditCardRepository {
    private final Source source;
    private final Map<UUID, List<DomainEvent>> eventStreams = new HashMap<>();

    public CreditCardRepository(Source source) {
        this.source = source;
    }

    public void save(CreditCard creditCard) {
        List<DomainEvent> currentStream
                = eventStreams.getOrDefault(creditCard.getUuid(), new ArrayList<>());
        currentStream.addAll(creditCard.getPendingEvents());
        creditCard.getPendingEvents().forEach(event ->
                source.output().send(new GenericMessage<>(event, headers(event))));
        eventStreams.put(creditCard.getUuid(), currentStream);
        creditCard.eventsFlushed();
    }

    private Map<String, Object> headers(DomainEvent event) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", event.getType());
        return headers;
    }

    public CreditCard load(UUID uuid) {
        return CreditCard.recreateFrom(uuid, eventStreams.getOrDefault(uuid, new ArrayList<>()));
    }
}
