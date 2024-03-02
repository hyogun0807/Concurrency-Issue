package com.example.concurrencyissue.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private long productId;

	@Column
	private long quantity;

	@Builder
	private Stock(long productId, long quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public static Stock create(long productId, long quantity) {
		return new Stock(productId, quantity);
	}
}
