package com.example.concurrencyissue.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.concurrencyissue.application.OptimisticLockStockFacade;
import com.example.concurrencyissue.domain.Stock;
import com.example.concurrencyissue.domain.StockRepository;

@SpringBootTest
class OptimisticLockStockFacadeTest {

	@Autowired
	private OptimisticLockStockFacade stockFacade;

	@Autowired
	private StockRepository stockRepository;

	@BeforeEach
	public void before() {
		stockRepository.save(Stock.create(1, 100));
	}

	@AfterEach
	public void after() {
		stockRepository.deleteAll();
	}

	@DisplayName("decrease - 100개의 요청에 대한 재고 감소가 성공적으로 이뤄지는 지 검증")
	@Test
	void decrease_100_request_quantity() throws InterruptedException {
		// Given
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		// When
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					stockFacade.decrease(1L, 1);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		Stock actual = stockRepository.findById(1L)
			.orElseThrow(IllegalArgumentException::new);

		// Then
		assertThat(actual.getQuantity()).isZero();
	}
}
