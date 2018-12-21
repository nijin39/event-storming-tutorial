package com.tandem.creditcard;

import com.tandem.creditcard.model.CreditCard;
import com.tandem.creditcard.persistance.CreditCardRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@EnableBinding(Source.class)
//@EnableScheduling
public class CreditcardApplication {

	private final CreditCardRepository creditCardRepository;

	public CreditcardApplication(CreditCardRepository creditCardRepository) {
		this.creditCardRepository = creditCardRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CreditcardApplication.class, args);
	}

//	@Scheduled(fixedRate = 2000)
//	public void randomCard() {
//		CreditCard creditCard = new CreditCard(UUID.randomUUID());
//		creditCard.assignLimit(BigDecimal.TEN);
//		creditCard.withdraw(BigDecimal.TEN);
//		creditCardRepository.save(creditCard);
//	}

}

