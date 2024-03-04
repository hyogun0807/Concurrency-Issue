package com.example.concurrencyissue.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

public interface StockRepository extends JpaRepository <Stock, Long> {

	@Lock(LockModeType.OPTIMISTIC)
	@Query("select s from Stock s where s.id = :id")
	Optional<Stock> findByIdWithOptimisticLock(Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select s from Stock s where s.id = :id")
	Optional<Stock> findByIdWithPessimisticLock(Long id);
}