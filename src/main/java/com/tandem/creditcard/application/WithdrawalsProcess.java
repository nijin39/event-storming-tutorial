package com.tandem.creditcard.application;

import com.tandem.creditcard.model.CreditCard;
import com.tandem.creditcard.model.Withdrawal;
import com.tandem.creditcard.persistance.CreditCardRepository;
import com.tandem.creditcard.persistance.WithdrawalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
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

    @Transactional
    public void repay(UUID cardId, BigDecimal money){
        CreditCard creditCard = creditCardRepository.load(cardId);
        creditCard.repay(money);
    }
}
