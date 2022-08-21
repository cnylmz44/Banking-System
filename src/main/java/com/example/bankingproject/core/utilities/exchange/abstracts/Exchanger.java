package com.example.bankingproject.core.utilities.exchange.abstracts;

import org.springframework.stereotype.Service;

@Service
public interface Exchanger {
	double exchange(String senderType, String receiverType, double amount);
}
