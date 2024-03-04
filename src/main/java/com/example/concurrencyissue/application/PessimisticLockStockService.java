package com.example.concurrencyissue.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.concurrencyissue.domain.Stock;
import com.example.concurrencyissue.domain.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decrease(Long id, long quantity) {
		Stock stock = stockRepository.findByIdWithPessimisticLock(id)
			.orElseThrow(() -> new IllegalArgumentException("not found stock by id"));
		stock.decrease(quantity);
	}
}
