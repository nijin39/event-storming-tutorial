package com.tandem.creditcard.ui

import com.tandem.creditcard.model.CreditCard
import com.tandem.creditcard.model.Withdrawal
import com.tandem.creditcard.persistance.CreditCardRepository
import groovy.util.logging.Log4j
import groovy.util.logging.Slf4j
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class WithdrawalsControllerTest {

    private static final UUID ANY_CARD_NO = UUID.randomUUID();

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Test
    public void should_show_correct_number_of_withdrawals() {
        //given
        CreditCard card = new CreditCard(ANY_CARD_NO);
        card.assignLimit( new BigDecimal(1000) );
        creditCardRepository.save(card);


        // when
        ResponseEntity postRes=testRestTemplate.postForEntity("/withdrawals/" + ANY_CARD_NO,
                new WithdrawalRequest(BigDecimal.TEN),
                WithdrawalRequest.class);

        log.info(":::::::::: {}",postRes.toString());

        // then
        ResponseEntity<List<Withdrawal>> withdrawals =
                testRestTemplate.exchange("/withdrawals/{uuid}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Withdrawal>>() {}, ["uuid": ANY_CARD_NO])
        assertThat(withdrawals.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(withdrawals.getBody(), hasSize(1))
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme