package com.coupang.elba.creditcard.application;

import com.coupang.elba.creditcard.model.CreditCard;
import com.coupang.elba.creditcard.model.Withdrawal;
import com.coupang.elba.creditcard.persistance.CreditCardRepository;
import com.coupang.elba.creditcard.persistance.WithdrawalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WithdrawalsProcess {
    private final CreditCardRepository creditCardRepository;
    private final WithdrawalRepository withdrawalRepository;

    public WithdrawalsProcess(CreditCardRepository creditCardRepository, WithdrawalRepository withdrawalRepository) {
        this.creditCardRepository = creditCardRepository;
        this.withdrawalRepository = withdrawalRepository;
    }

    @Transactional
    public void withdrawl(UUID cardId, BigDecimal money) {
        CreditCard creditCard = creditCardRepository.load(cardId);
        creditCard.withdraw(money);
        withdrawalRepository.save(new Withdrawal(UUID.randomUUID(), cardId, money));
    }
}
