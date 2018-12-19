package com.coupang.elba.creditcard.persistance;

import com.coupang.elba.creditcard.model.Withdrawal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface WithdrawalRepository extends CrudRepository<Withdrawal, UUID> {

    List<Withdrawal> findByCardId(UUID cardId);
}