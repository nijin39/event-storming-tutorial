package com.tandem.creditcard.model

import org.junit.Test
import spock.lang.*

class CreditCardTest extends Specification {

    CreditCard creditCard = new CreditCard(UUID.randomUUID())

    @Test
    public void cannot_withdraw_when_limit_not_assigned() {
        when:
        creditCard.withdraw(101)
        then:
        thrown(IllegalStateException)

    }

    @Test
    public void cannot_withdraw_when_not_enough_money() {
        given:
        creditCard.assignLimit(100)
        when:
        creditCard.withdraw(101)
        then:
        thrown(IllegalStateException)
    }

    @Test
    public void cannot_withdraw_when_there_was_withdrawal_within_lastH() {

    }

    @Test
    public void can_withdraw() {
        given:
        creditCard.assignLimit(100)
        when:
        creditCard.withdraw(50)
        then:
        creditCard.availableLimit() == 50
    }

    @Test
    public void cannot_assign_limit_when_it_was_already_assigned() {
        given:
        creditCard.assignLimit(100)
        when:
        creditCard.assignLimit(50)
        then:
        thrown(IllegalStateException)
    }

    @Test
    public void can_assign_limit() {
        when:
        creditCard.assignLimit(50)
        then:
        creditCard.availableLimit() == 50
    }

    @Test
    public void can_repay() {
        given:
        creditCard.assignLimit(100)
        and:
        creditCard.withdraw(10)
        when:
        creditCard.repay(5)
        then:
        creditCard.availableLimit() == 95

    }
}