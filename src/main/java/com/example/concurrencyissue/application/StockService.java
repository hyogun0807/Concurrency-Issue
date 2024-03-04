package com.example.concurrencyissue.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.concurrencyissue.domain.Stock;
import com.example.concurrencyissue.domain.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	public synchronized  void decrease(Long id, long quantity) {
		Stock stock = stockRepository.findById(id)
			.orElseThrow(() -> new
				IllegalArgumentException("not found stock by id"));

		stock.decrease(quantity);
		stockRepository.saveAndFlush(stock);
	}
}
