package com.tandem.creditcard.persistance;

import com.tandem.creditcard.model.Withdrawal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WithdrawalRepository extends CrudRepository<Withdrawal, UUID> {

    List<Withdrawal> findByCardId(UUID cardId);
}