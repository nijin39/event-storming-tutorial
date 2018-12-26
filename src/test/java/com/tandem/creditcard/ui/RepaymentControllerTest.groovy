package com.tandem.creditcard.ui

import com.tandem.creditcard.CreditcardApplication
import com.tandem.creditcard.model.CreditCard
import com.tandem.creditcard.model.MoneyRepaid
import com.tandem.creditcard.persistance.CreditCardRepository
import groovy.util.logging.Slf4j
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.stream.messaging.Source
import org.springframework.cloud.stream.test.binder.MessageCollector
import org.springframework.http.ResponseEntity
import org.springframework.messaging.Message
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.*
import com.tandem.creditcard.ui.WithdrawalRequest
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.concurrent.BlockingQueue

import static org.mockito.Mockito.*


@SpringBootTest(classes = CreditcardApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class RepaymentControllerTest extends Specification {

    private static final UUID ANY_CARD_NR = UUID.randomUUID();

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MessageCollector messageCollector;

    @Autowired
    Source source;

    BlockingQueue<Message<?>> outputEvents;

    def setup() {
        outputEvents = messageCollector.forChannel(source.output());
    }


    def "test repay"() {
        given:
        CreditCard card = new CreditCard(ANY_CARD_NR);
        card.assignLimit( new BigDecimal(1000) );
        creditCardRepository.save(card);
        testRestTemplate.postForEntity("/withdrawals/" + ANY_CARD_NR,
                new WithdrawalRequest(BigDecimal.TEN),
                WithdrawalRequest.class)

        when:
        testRestTemplate.postForEntity("/repayments/" + ANY_CARD_NR,
                new RepaymentRequest(BigDecimal.TEN),
                RepaymentRequest.class)

        then:
            outputEvents.poll().getPayload() instanceof MoneyRepaid == true

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme