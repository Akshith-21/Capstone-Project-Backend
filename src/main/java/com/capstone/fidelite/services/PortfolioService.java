package com.capstone.fidelite.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.fidelite.integration.FMTSDao;
import com.capstone.fidelite.integration.FMTSDaoImpl;
import com.capstone.fidelite.integration.TradeDao;
import com.capstone.fidelite.models.Direction;
import com.capstone.fidelite.models.Order;
import com.capstone.fidelite.models.OrderFMTS;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.PortfolioDetails;
import com.capstone.fidelite.models.Trade;
import com.capstone.fidelite.models.TradeFMTS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class PortfolioService {

	@Autowired
	Logger logger;

	@Autowired
	TradeDao tradeDaoImpl;

	@Autowired
	private FMTSDao fmtsDao;

	private LocalDateTime old = LocalDateTime.now();
	private List<Portfolio> updatedPortfolioList = null;
	private int count = 1;

	public PortfolioService() {
	}

	public List<Portfolio> getAllPortfolio(String clientId) {
		return tradeDaoImpl.getAllPortfolioMyBatis(clientId);
	}

	public List<Portfolio> getUpdatedPortfolios(String clientId) {

		List<PortfolioDetails> instrumentList = null;

		List<Portfolio> portList = null;

		LocalDateTime present = LocalDateTime.now();

		long durationOfMinutes = ChronoUnit.MINUTES.between(old, present);

		if (durationOfMinutes > 1 || count == 1) {

			System.out.println("Called Once ****&*&^$&$&%()%@@");

			try {

				instrumentList = tradeDaoImpl.getUpdatedPortfolios(clientId);

				Map<String, Double> instrumentMap = instrumentList.stream().collect(
						Collectors.toMap(PortfolioDetails::getInstrumentId, PortfolioDetails::getCurrentHoldings));

				if (instrumentList.isEmpty()) {

					return null;

				}
				System.out.println("in Service " + instrumentMap);
				updatedPortfolioList = fmtsDao.queryUpdatedPortfolios(instrumentMap);
				System.out.println(updatedPortfolioList);

			} catch (JsonProcessingException e) {
				System.out.println("OOOOOOOOOOOOOOOOOOOOOO");
				e.printStackTrace();

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

			} catch (ResponseStatusException e) {

				if (e.getReason().equals(HttpStatus.NOT_ACCEPTABLE))

					throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

				if (e.getReason().equals(HttpStatus.NOT_FOUND)) {

					throw new ResponseStatusException(HttpStatus.NOT_FOUND);

				}

			}

			catch (Exception e) {

				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

			}

			count = 2;

			old = LocalDateTime.now();

		}

		System.out.println(durationOfMinutes + "&*&*&******%$#");

		return updatedPortfolioList;

	}

	private Trade executeOrder(OrderFMTS order) {
		Trade trade = null;
		TradeFMTS tradeFMTS = null;
		try {
			tradeFMTS = fmtsDao.executeTrade(order);
//			System.out.println(")(*!#$*&)(#!&$)(*#!&$)#!*)(*$#!$&)!#&$");
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		trade = new Trade(tradeFMTS);
		return trade;

	}

	public Trade executeTrade(OrderFMTS order) {
		Trade trade = null;
		try {
			System.out.println("Inside executeTrade in portfolio service: " + order);
			trade = executeOrder(order);

			if (order.getDirection().equals("B")) {
				addPortfolio(trade);
				addTrade(trade);
			} else if (order.getDirection().equals("S")) {
				sellPortfolio(trade);
				addTrade(trade);
			} else {
				throw new IllegalArgumentException("Direction Should be either BUY or SELL");
			}
		} catch (IllegalArgumentException e) {
			logger.error("Unable to execute trade");
			e.printStackTrace();
		} catch (Exception e) {
			throw e;
		}
		return trade;
	}

	public void addTrade(Trade trade) {
		tradeDaoImpl.insertTrade(trade);
	}

	public void addPortfolio(Trade trade) {

		Map<String, Object> tempPortfolio = tradeDaoImpl.getPortfolio(trade.getClientId(), trade.getInstrumentId());
		if (tempPortfolio != null) {
			BigDecimal holdings = (BigDecimal) tempPortfolio.get("CURRENT_HOLDINGS");
			tradeDaoImpl.updatePortfolioMyBatis((String) tempPortfolio.get("CLIENT_ID"),
					(String) tempPortfolio.get("INSTRUMENT_ID"), trade.getQuantity() + holdings.doubleValue());

		} else {
			tradeDaoImpl.insertPortfolioMyBatis(trade.getClientId(), trade.getInstrumentId(), trade.getQuantity());
		}
		double balance = tradeDaoImpl.getBalance(trade.getClientId());
		balance = balance - trade.getCashValue();
		tradeDaoImpl.updateBalance(trade.getClientId(), balance);

	}

	public void sellPortfolio(Trade trade) {

		Map<String, Object> tempPortfolio = tradeDaoImpl.getPortfolio(trade.getClientId(), trade.getInstrumentId());
		if (tempPortfolio != null) {
			if (((BigDecimal) tempPortfolio.get("CURRENT_HOLDINGS")).doubleValue() == trade.getQuantity()) {
				tradeDaoImpl.deletePortfolio(trade.getClientId(), trade.getInstrumentId());
			} else {
				tradeDaoImpl.updatePortfolioMyBatis(trade.getClientId(), trade.getInstrumentId(),
						((BigDecimal) tempPortfolio.get("CURRENT_HOLDINGS")).doubleValue() - trade.getQuantity());
			}
			double balance = tradeDaoImpl.getBalance(trade.getClientId());
			balance = balance + trade.getCashValue();
			tradeDaoImpl.updateBalance(trade.getClientId(), balance);
		} else {
			throw new IllegalStateException("Not Allowed, No Holdings to sell");
		}

	}

//	public List<Portfolio> getPortfolioData(String email) {	
//         return portfolioData.get(email);
//    }

	public List<Trade> getTradeHistory(String clientId) {
		List<Trade> tradeHistory = tradeDaoImpl.getAllTradesById(clientId);
		return tradeHistory;
	}
//    
//    public List<Trade> getClientTradeHistorySorted(String email) {
//        List<Trade> tradeHistory = tradeHistoryData.get(email);
//        if (tradeHistory == null) {
//            return Collections.emptyList(); 
//        }
//           tradeHistory.sort(Comparator.comparingDouble(Trade::getCashValue).reversed());
//           if (tradeHistory.size() > 100) {
//            tradeHistory = tradeHistory.subList(0, 100);
//        }
//
//        return tradeHistory;
//    }

}
