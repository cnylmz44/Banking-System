package com.example.bankingproject.core.utilities.exchange.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.bankingproject.core.utilities.exchange.abstracts.Exchanger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CollectApiCurrencyExchange implements Exchanger {
	private RestTemplate client;
	private HttpHeaders headers;
	private String contentType = "application/json";
	private String authorizationKey = "apikey 2nQdEnX6qpUmaoIT9WPANc:4XK9FD3scr56zboC47Zmp8";
	private String currencyExchangeUrl = "https://api.collectapi.com/economy/exchange?";
	private String goldPricesUrl = "https://api.collectapi.com/economy/goldPrice";
	private HttpEntity<?> requestEntity;

	@Autowired
	public CollectApiCurrencyExchange() {
		// Default Authorization Settings
		client = new RestTemplate();
		headers = new HttpHeaders();
		headers.add("content-type", contentType);
		headers.add("authorization", authorizationKey);
		requestEntity = new HttpEntity<>(headers);
	}

	public double exchangeCurrencies(String senderType, String receiverType, double amount) {
		String url = currencyExchangeUrl + "int=" + (int) amount + "&to=" + receiverType + "&base=" + senderType;
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = client.exchange(url, HttpMethod.GET, requestEntity, String.class);
		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		double exchangedAmount;
		try {
			JsonNode node = objectMapper.readTree(responseBody);
			JsonNode resultNode = node.get("result");
			JsonNode dataNode = resultNode.get("data");
			for (JsonNode value : dataNode) {
				exchangedAmount = value.get("calculated").doubleValue();
				return exchangedAmount;
			}
			return (Double) null;
		} catch (Exception e) {
			return (Double) null;
		}
	}

	public double getGramGoldPriceInTRY(String type) {
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = client.exchange(goldPricesUrl, HttpMethod.GET, requestEntity, String.class);
		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode node = objectMapper.readTree(responseBody);
			JsonNode resultNode = node.get("result");
			for (JsonNode goldNode : resultNode) {
				String name = goldNode.get("name").toString();
				if (name.contains("Gram Altın")) {
					System.out.print(goldNode);
					return goldNode.get(type).doubleValue();
				}

			}
		} catch (Exception e) {
			return (Double) null;
		}
		return (Double) null;
	}

	public double exchange(String senderType, String receiverType, double amount) {
		if (senderType.equals(receiverType))
			return amount;
		else if (senderType.equals("GAU") && !receiverType.equals("GAU"))
			return exchangeGold2Currency(receiverType, amount);
		else if (!senderType.equals("GAU") && receiverType.equals("GAU"))
			return exchangeCurrency2Gold(senderType, amount);
		else
			return exchangeCurrencies(senderType, receiverType, amount);
	}

	private double exchangeCurrency2Gold(String senderType, double amount) {
		double goldPriceInTRY = getGramGoldPriceInTRY("buying");
		System.out.println(goldPriceInTRY);
		// TRY to TRY exchange gives error
		if (senderType.equals("TRY"))
			return amount / goldPriceInTRY;

		// senderType != "TRY"
		double amountInTRY = exchangeCurrencies(senderType, "TRY", amount);
		double totalAmount = amountInTRY / goldPriceInTRY;

		return totalAmount;
	}

	private double exchangeGold2Currency(String receiverType, double amount) {
		double goldPriceInTRY = getGramGoldPriceInTRY("selling");
		// TRY to TRY exchange gives error
		if (receiverType.equals("TRY"))
			return amount * goldPriceInTRY;

		// receiverType != "TRY"
		double amountInTRY = amount * goldPriceInTRY;
		double totalAmount = exchange("TRY", receiverType, amountInTRY);

		return totalAmount;
	}
}
