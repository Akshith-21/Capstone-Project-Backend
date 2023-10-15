package com.capstone.fidelite.integration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.capstone.fidelite.models.InvestmentDetails;
import com.capstone.fidelite.models.Order;
import com.capstone.fidelite.models.OrderFMTS;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.Price;
import com.capstone.fidelite.models.TradeFMTS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class FMTSDaoImpl implements FMTSDao {

	@Override
	public ClientFMTS verifyClientInformation(Client client) throws JsonMappingException, JsonProcessingException {
		ClientFMTS response = null;
		RestTemplate restTemplate = new RestTemplate();

		ClientFMTS clientFMTS = new ClientFMTS();
		clientFMTS.setClientId("");
		clientFMTS.setEmail(client.getPerson().getEmail());
		clientFMTS.setToken("");

		String url = "http://localhost:3000/fmts/client";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		System.out.println(clientFMTS);
		HttpEntity<ClientFMTS> requestEntity = new HttpEntity<>(clientFMTS, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				String.class);
		System.out.println(responseEntity.getStatusCode() + "********STRING FMTS RESPONSE*********");
		if (responseEntity.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

		} else if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		ObjectMapper mapper = new ObjectMapper();
		response = mapper.readValue(responseEntity.getBody(), ClientFMTS.class);
		System.out.println(response);

		return response;
	}

	private boolean checkPortfolioInstrumentId(String instrumentId, Map<String, InvestmentDetails> instrumentMap ) {

		return instrumentMap.containsKey(instrumentId);
	}

	private Portfolio convertToPortFolio(Price p, Map<String, InvestmentDetails> instrumentMap) {
		Portfolio transformPrice = new Portfolio();
		transformPrice.setAskPrice(p.getAskPrice());
		transformPrice.setBidPrice(p.getBidPrice());
		transformPrice.setExternalId(p.getInstrument().getExternalId());
		transformPrice.setExternalType(p.getInstrument().getExternalIdType());
		transformPrice.setInstrumentDescription(p.getInstrument().getInstrumentDescription());
		transformPrice.setInstrumentId(p.getInstrument().getInstrumentId());
		transformPrice.setPriceTimestamp(p.getPriceTimestamp());
		transformPrice.setCategoryType(p.getInstrument().getCategoryId());
		transformPrice.setCurrentHoldings(instrumentMap.get(transformPrice.getInstrumentId()).getCurrentHoldings());
		transformPrice.setTotalInvestment(instrumentMap.get(transformPrice.getInstrumentId()).getTotalInvestment());
		return transformPrice;
	}

	
	
	@Override
	public List<Portfolio> queryUpdatedPortfolios(Map<String, InvestmentDetails> instrumentMap)
			throws JsonMappingException, JsonProcessingException {
		List<Portfolio> updatedPortfolioList = null;
		List<Price> priceList = getAllPrices();
		priceList = priceList.stream()
				.filter(p -> checkPortfolioInstrumentId(p.getInstrument().getInstrumentId(), instrumentMap))
				.collect(Collectors.toList());
		updatedPortfolioList = priceList.stream().map(p -> convertToPortFolio(p, instrumentMap))
				.collect(Collectors.toList());

		return updatedPortfolioList;

	}

	@Override
	public TradeFMTS executeTrade(OrderFMTS orderFMTS) throws JsonMappingException, JsonProcessingException {
		TradeFMTS response = null;
		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:3000/fmts/trades/trade";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

//		OrderFMTS orderFMTS = new OrderFMTS(order);

		HttpEntity<OrderFMTS> requestEntity = new HttpEntity<>(orderFMTS, headers);
		System.out.println(orderFMTS + "----)(*!#$*&)(#!&$)(*#!&$)#!*)(*$#!$&)!#&$");
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				String.class);
		System.out.println("---->> " + responseEntity.getBody());

		if (responseEntity.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

		} else if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		ObjectMapper mapper = new ObjectMapper();
		System.out.println("----)(*!#$*&)(#!&$)(*#!&$)#!*)(*$#!$&)!#&$");
		response = mapper.readValue(responseEntity.getBody(), TradeFMTS.class);
		System.out.println(responseEntity.getBody());
		System.out.println("----)(*!#$*&)(#!&$)(*#!&$)#!*)(*$#!$&)!#&$");

		return response;
	}

	@Override
	public List<Price> getAllPrices() throws JsonMappingException, JsonProcessingException {
		Price[] updatedPrices = null;
		String url = "http://localhost:3000/fmts/trades/prices";
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		
		responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
				System.out.println(responseEntity.getStatusCode() + "******STATUS CODE***************");

		if (responseEntity.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

		} else if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
//				System.out.println(responseEntity.getBody());
		ObjectMapper mapper = new ObjectMapper();
		updatedPrices = mapper.readValue(responseEntity.getBody(), Price[].class);
				System.out.println(updatedPrices[0].getInstrument().getInstrumentId()+"****************MYINSTRUMENT************");
		List<Price> priceList = Arrays.asList(updatedPrices);
		
		
		return priceList;
	}

	@Override
	public List<Price> getAllPricesByFilter(String category) throws JsonMappingException, JsonProcessingException {
		List<Price> priceList = getAllPrices();
		priceList = priceList.stream().filter(p -> 
			p.getInstrument().getCategoryId().equals(category)).collect(Collectors.toList());
		return priceList;
	}

}
