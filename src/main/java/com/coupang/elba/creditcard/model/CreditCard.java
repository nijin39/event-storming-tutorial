package com.coupang.elba.creditcard.model;

import io.vavr.Predicates;
import lombok.Getter;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.collection.List.ofAll;

@Getter
@Entity
public class CreditCard {

    private final UUID uuid;
    private List<DomainEvent> pendingEvents = new ArrayList<>();
    private BigDecimal limit;
    private BigDecimal usedLimit = BigDecimal.ZERO;
    private int withdrawals;

    private CreditCard(UUID uuid){
        this.uuid = uuid;
    }

    void assignLimit(BigDecimal money) {        //cmd
        if(limitAlreadyAssigned()) {            //invariant
            throw new IllegalStateException();  //nack
        }
        this.handleWithAppend(new LimitAssigned(uuid, money, Instant.now()));  //ack
    }

    public BigDecimal availableLimit() {
        return limit.subtract(usedLimit);
    }

    private CreditCard limitAssigned(LimitAssigned event) {
        this.limit = event.getMoney();
        return this;
    }

    public void withdraw(BigDecimal money) {          //cmd
        if(limitNotAssigned()) {               //invariant
            throw new IllegalStateException(); //nack
        }
        if(notEnoughMoney(money)) {
            throw new IllegalStateException();
        }
        if(withdrawalWithinLastHour()) {
            throw new IllegalStateException();
        }

        this.handleWithAppend(new CardWithdrawn(uuid, money, Instant.now()));
    }

    private CreditCard cardWithdrawn(CardWithdrawn event) {
        this.usedLimit = usedLimit.add(event.getMoney());
        this.withdrawals++;
        return this;
    }

    void repay(BigDecimal money) {
        this.handleWithAppend(new CardRepaid(uuid, money, Instant.now()));
    }

    private CreditCard cardRepaid(CardRepaid event) {
        usedLimit = usedLimit.subtract(event.getMoney());
        return this;
    }

    private boolean withdrawalWithinLastHour() {
        return false;
    }

    private boolean notEnoughMoney(BigDecimal money) {
        return availableLimit().compareTo(money) < 0;
    }

    private boolean limitNotAssigned()
    {
        return limit == null;
    }



    private boolean limitAlreadyAssigned() {
        return limit != null;
    }

    public void eventsFlushed() {
        pendingEvents.clear();
    }

    public static CreditCard recreateFrom(UUID uuid, List<DomainEvent> events) {
        return ofAll(events).foldLeft(new CreditCard(uuid), CreditCard::handle);
    }


    private CreditCard handleWithAppend(DomainEvent event) {
        this.pendingEvents.add(event);
        return this.handle(event);
    }

    private CreditCard handle(DomainEvent event) {
        return io.vavr.API.Match(event).of(
                Case($(Predicates.instanceOf(LimitAssigned.class)), this::limitAssigned),
                Case($(Predicates.instanceOf(CardWithdrawn.class)), this::cardWithdrawn),
                Case($(Predicates.instanceOf(CardRepaid.class)), this::cardRepaid)
                //Case($(Predicates.instanceOf(CycleClosed.class)), this::cycleClosed)
        );
    }
}
