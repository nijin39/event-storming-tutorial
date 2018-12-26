package com.tandem.creditcard.ui;

import com.tandem.creditcard.CreditcardApplication;
import com.tandem.creditcard.model.CreditCard;
import com.tandem.creditcard.model.DomainEvent;
import com.tandem.creditcard.model.MoneyRepaid;
import com.tandem.creditcard.model.Withdrawal;
import com.tandem.creditcard.persistance.CreditCardRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import static java.math.BigDecimal.TEN;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CreditcardApplication.class,
        webEnvironment = RANDOM_PORT)
public class RepaymentsTest {

    private static final UUID ANY_CARD_NR = UUID.randomUUID();

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    public MessageCollector messageCollector;

    @Autowired
    public Source source;

    public BlockingQueue<Message<?>> outputEvents;

    @Before
    public void setup() {
        outputEvents = messageCollector.forChannel(source.output());
        CreditCard card = new CreditCard(ANY_CARD_NR);
        card.assignLimit( new BigDecimal(1000) );
        creditCardRepository.save(card);
    }

    @Test
    public void should_show_correct_number_of_withdrawals_after_1st_withdrawal() {

        // given
        testRestTemplate.postForEntity("/withdrawals/" + ANY_CARD_NR,
                new WithdrawalRequest(TEN),
                WithdrawalRequest.class);
        outputEvents.clear();

        // when
        ResponseEntity<RepaymentRequest> a = testRestTemplate.postForEntity("/repayments/" + ANY_CARD_NR,
                new RepaymentRequest(TEN),
                RepaymentRequest.class);

        // then
        assertThat( outputEvents.poll().getPayload() instanceof MoneyRepaid, is(true));
    }

}