package com.tandem.creditcard.ui;

import com.tandem.creditcard.application.WithdrawalsProcess;
import com.tandem.creditcard.model.MoneyRepaid;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RestController("/repayments")
class RepaymentController {

    private final Source source;
    private final WithdrawalsProcess withdrawalsProcess;

    RepaymentController(Source source, WithdrawalsProcess withdrawalsProcess) {
        this.source = source;
        this.withdrawalsProcess = withdrawalsProcess;
    }

    @PostMapping("/{cardNr}")
    ResponseEntity repay(@PathVariable String cardNr, @RequestBody RepaymentRequest r) {
        withdrawalsProcess.repay(UUID.fromString(cardNr),  r.amount );
        source.output().send( (Message<MoneyRepaid>) new MoneyRepaid(UUID.fromString(cardNr), r.amount, Instant.now()));
        return ResponseEntity.ok().body(r);
    }

}

class RepaymentRequest {

    final BigDecimal amount;

    RepaymentRequest(BigDecimal amount) {
        this.amount = amount;
    }
}
