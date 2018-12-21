package com.tandem.creditcard.ui;

import com.tandem.creditcard.application.WithdrawalsProcess;
import com.tandem.creditcard.model.Withdrawal;
import com.tandem.creditcard.persistance.WithdrawalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
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

    @PostMapping("/{cardNo}")
    ResponseEntity withdraw(@PathVariable String cardNo, @RequestBody WithdrawalRequest r){
        withdrawalsProcess.withdrawl(UUID.fromString(cardNo) ,r.getMoney());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/withdrawals/{cardId}")
    ResponseEntity<List<Withdrawal>> withdrawals(@PathVariable UUID cardId) {
        return ResponseEntity.ok().body(withdrawalRepository.findByCardId(cardId));
    }
}

class WithdrawalRequest implements Serializable {
    private BigDecimal money;

    public WithdrawalRequest(){};
    public WithdrawalRequest(BigDecimal money) {
        this.money = money;
    }


    public BigDecimal getMoney() {
        return money;
    }
}
