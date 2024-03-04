package com.example.concurrencyissue.application;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {
	private final OptimisticLockStockService stockService;

	public void decrease(Long id, long quantity) throws InterruptedException {
		while (true) {
			try {
				stockService.decrease(id, quantity);
				break;
			} catch (Exception e) {
				Thread.sleep(50);
			}
		}
	}
}
