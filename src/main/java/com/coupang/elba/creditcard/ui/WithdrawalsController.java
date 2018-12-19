package com.coupang.elba.creditcard.ui;

import com.coupang.elba.creditcard.application.WithdrawalsProcess;
import com.coupang.elba.creditcard.model.Withdrawal;
import com.coupang.elba.creditcard.persistance.WithdrawalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class WithdrawalsController {

    private final WithdrawalRepository withdrawalRepository;
    private final WithdrawalsProcess withdrawalsProcess;

    public WithdrawalsController(WithdrawalRepository withdrawalRepository, WithdrawalsProcess withdrawalsProcess) {
        this.withdrawalRepository = withdrawalRepository;
        this.withdrawalsProcess = withdrawalsProcess;
    }

    @PostMapping("/withdrawals")
    ResponseEntity withdraw(@RequestBody WithdrawalRequest withdrawalRequest) {
        withdrawalsProcess.withdrawl(withdrawalRequest.getCardId(), withdrawalRequest.getAmount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/withdrawals/{cardId}")
    ResponseEntity<List<Withdrawal>> withdrawals(@PathVariable UUID cardId) {
        return ResponseEntity.ok().body(withdrawalRepository.findByCardId(cardId));
    }
}

class WithdrawalRequest {
    private UUID cardId;
    private BigDecimal amount;

    WithdrawalRequest() {

    }

    WithdrawalRequest(UUID cardId, BigDecimal amount) {
        this.cardId = cardId;
        this.amount = amount;
    }

    public UUID getCardId() {
        return cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
