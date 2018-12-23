package com.tandem.creditcard.ui

import groovy.util.logging.Log4j
import groovy.util.logging.Slf4j
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class WithdrawalsControllerTest {

    private static final String ANY_CARD_NO = "no";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void should_show_correct_number_of_withdrawals() {
        // when
        testRestTemplate.postForEntity("/withdrawals/" + ANY_CARD_NO,
                new WithdrawalRequest(BigDecimal.TEN),
                WithdrawalRequest.class);

        // then
        ResponseEntity res = testRestTemplate.getForEntity(
                "/withdrawals/" + ANY_CARD_NO,
                WithdrawalRequest.class);
        log.info(res.toString());
        assertThat(res.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(res.getBody(), hasSize(1))
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme