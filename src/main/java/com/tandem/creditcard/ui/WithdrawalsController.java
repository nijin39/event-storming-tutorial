package com.tandem.creditcard.ui;

import com.tandem.creditcard.application.WithdrawalsProcess;
import com.tandem.creditcard.model.Withdrawal;
import com.tandem.creditcard.persistance.WithdrawalRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/withdrawals")
public class WithdrawalsController {

    private final WithdrawalRepository withdrawalRepository;
    private final WithdrawalsProcess withdrawalsProcess;

    public WithdrawalsController(WithdrawalRepository withdrawalRepository, WithdrawalsProcess withdrawalsProcess) {
        this.withdrawalRepository = withdrawalRepository;
        this.withdrawalsProcess = withdrawalsProcess;
    }

    @PostMapping("/{cardId}")
    ResponseEntity withdraw(@PathVariable UUID cardId, @RequestBody WithdrawalRequest r){
        withdrawalsProcess.withdrawl(cardId ,r.getMoney());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cardId}")
    ResponseEntity<List<Withdrawal>> withdrawals(@PathVariable UUID cardId) {
        log.info("::::::::::::;:: GET {}",withdrawalRepository.findByCardId(cardId).toString());
        return ResponseEntity.ok().body(withdrawalRepository.findByCardId(cardId));
    }
}

@Data
class WithdrawalRequest implements Serializable {

    private static final long serialversionUID = 129348938L;
    private BigDecimal money;

    public WithdrawalRequest(){};
    public WithdrawalRequest(BigDecimal money) {
        this.money = money;
    }


    public BigDecimal getMoney() {
        return money;
    }
}
