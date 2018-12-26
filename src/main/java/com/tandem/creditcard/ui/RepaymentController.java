package com.tandem.creditcard.ui;

import com.tandem.creditcard.application.WithdrawalsProcess;
import com.tandem.creditcard.model.MoneyRepaid;
import com.tandem.creditcard.persistance.CreditCardRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/repayments")
class RepaymentController {

    private final Source source;
    private final WithdrawalsProcess withdrawalsProcess;

    public RepaymentController(Source source, WithdrawalsProcess withdrawalsProcess) {
        this.source = source;
        this.withdrawalsProcess = withdrawalsProcess;
    }

    @PostMapping("/{cardNr}")
    ResponseEntity<RepaymentRequest> repay(@PathVariable UUID cardNr, @RequestBody RepaymentRequest r) {
        //source.output().send(MessageBuilder.withPayload( new MoneyRepaid(cardNr, r.getAmount(), Instant.now()) ).build());
        withdrawalsProcess.repay(cardNr,  r.getAmount() );
        return ResponseEntity.ok().body(r);
    }

}

@Data
class RepaymentRequest implements Serializable {

    private static final long serialversionUID = 129348939L;

    private BigDecimal money;

    public RepaymentRequest(){}
    public RepaymentRequest(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAmount() {
        return money;
    }
}
