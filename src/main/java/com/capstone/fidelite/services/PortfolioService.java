package com.capstone.fidelite.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.fidelite.integration.DatabaseException;
import com.capstone.fidelite.integration.FMTSDao;
import com.capstone.fidelite.integration.FMTSDaoImpl;
import com.capstone.fidelite.integration.TradeDao;
import com.capstone.fidelite.models.Direction;
import com.capstone.fidelite.models.InvestmentDetails;
import com.capstone.fidelite.models.Order;
import com.capstone.fidelite.models.OrderFMTS;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.PortfolioDetails;
import com.capstone.fidelite.models.Price;
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


	public List<Portfolio> getUpdatedPortfolios(String clientId) {

		List<PortfolioDetails> instrumentList = null;

		List<Portfolio> portList = null;

		LocalDateTime present = LocalDateTime.now();

		long durationOfSeconds = ChronoUnit.SECONDS.between(old, present);

		if (durationOfSeconds > 1 || count == 1) {

			System.out.println("Called Once ****&*&^$&$&%()%@@");

			try {

				instrumentList = tradeDaoImpl.getUpdatedPortfolios(clientId);

				Map<String, InvestmentDetails> instrumentMap = instrumentList.stream().collect(
						Collectors.toMap(PortfolioDetails::getInstrumentId, PortfolioDetails::getInvestmentDetails));

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
				e.printStackTrace();

				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

			}

			count = 2;

			old = LocalDateTime.now();

		}

		System.out.println(durationOfSeconds + "&*&*&******%$#");

		return updatedPortfolioList;

	}
	
	public BigDecimal getBalance(String clientId) {
		BigDecimal balance = null;
		try {
			balance = tradeDaoImpl.getBalance(clientId);
		}
		catch(DataAccessException e) {
			throw new DatabaseException("Problem in accessing the data");
		}
		return balance;
	}

	private Trade executeOrder(OrderFMTS order) {
		Trade trade = null;
		TradeFMTS tradeFMTS = null;
		try {
			
			tradeFMTS = fmtsDao.executeTrade(order);
			if(tradeFMTS == null) {
				return null;
			}
			
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
			System.out.println("Inside executeTrade in portfolio service: " + order);
			trade = executeOrder(order);
			System.out.println("Outside the executeOrder Function ***************");
			if(trade == null) {
				return trade;
			}
			if(tradeDaoImpl.getBalance(order.getClientId()).compareTo(trade.getCashValue())<0) {
				throw new InsufficientBalanceException("No sufficient Balance to execute trade");
			}
	
			System.out.println("Trade Succesfully Executed ************" + trade);

			if (order.getDirection().equals("B")) {
				
				addPortfolio(trade);
				System.out.println("Successfull Adding Portfolio");
				addTrade(trade);
				System.out.println("Successfull Adding Trade");
			} else if (order.getDirection().equals("S")) {
				sellPortfolio(trade);
				addTrade(trade);
			} else {
				throw new IllegalArgumentException("Direction Should be either BUY or SELL");
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
			BigDecimal totalInvestment = (BigDecimal) tempPortfolio.get("TOTAL_INVESTMENT");
			totalInvestment = totalInvestment.setScale(2,RoundingMode.HALF_UP);
			BigDecimal newInvestment = totalInvestment.add(trade.getCashValue().setScale(2,RoundingMode.HALF_UP)).setScale(2,RoundingMode.HALF_UP);
			
			tradeDaoImpl.updatePortfolioMyBatis((String) tempPortfolio.get("CLIENT_ID"),
					(String) tempPortfolio.get("INSTRUMENT_ID"), trade.getQuantity() + holdings.doubleValue(),newInvestment);

		} else {
			tradeDaoImpl.insertPortfolioMyBatis(trade.getClientId(), trade.getInstrumentId(), trade.getQuantity(),trade.getCashValue());
		}
		BigDecimal balance = tradeDaoImpl.getBalance(trade.getClientId()).setScale(2,RoundingMode.HALF_UP);
		balance = balance.subtract(trade.getCashValue().setScale(2,RoundingMode.HALF_UP)).setScale(2,RoundingMode.HALF_UP);
		tradeDaoImpl.updateBalance(trade.getClientId(), balance);
        
	}

	public void sellPortfolio(Trade trade) {

		Map<String, Object> tempPortfolio = tradeDaoImpl.getPortfolio(trade.getClientId(), trade.getInstrumentId());
		if (tempPortfolio != null) {
			if (((BigDecimal) tempPortfolio.get("CURRENT_HOLDINGS")).doubleValue() == trade.getQuantity()) {
				tradeDaoImpl.deletePortfolio(trade.getClientId(), trade.getInstrumentId());
			}
			else if (((BigDecimal) tempPortfolio.get("CURRENT_HOLDINGS")).doubleValue() < trade.getQuantity()) {
				throw new IllegalArgumentException("Not Enough Holdings to sell");
			}
			else {
				BigDecimal totalInvestment = (BigDecimal) tempPortfolio.get("TOTAL_INVESTMENT");
				totalInvestment = totalInvestment.setScale(2,RoundingMode.HALF_UP);
			    BigDecimal averageInvestment = totalInvestment.divide((BigDecimal) tempPortfolio.get("CURRENT_HOLDINGS"),2,RoundingMode.HALF_UP).setScale(2,RoundingMode.HALF_UP);
			    averageInvestment = averageInvestment.multiply(new BigDecimal(trade.getQuantity())).setScale(2,RoundingMode.HALF_UP);
			    totalInvestment = totalInvestment.subtract(averageInvestment);
				
				tradeDaoImpl.updatePortfolioMyBatis(trade.getClientId(), trade.getInstrumentId(),
						((BigDecimal) tempPortfolio.get("CURRENT_HOLDINGS")).doubleValue() - trade.getQuantity(),totalInvestment);
			}
			BigDecimal balance = tradeDaoImpl.getBalance(trade.getClientId()).setScale(2,RoundingMode.HALF_UP);
			balance = balance.add(trade.getCashValue().setScale(2,RoundingMode.HALF_UP)).setScale(2,RoundingMode.HALF_UP);
			tradeDaoImpl.updateBalance(trade.getClientId(), balance);
		} else {
			throw new IllegalStateException("Not Allowed, No Holdings to sell");
		}
		
	}

//	public List<Portfolio> getPortfolioData(String email) {	
//         return portfolioData.get(email);
//    }

	public List<Trade> getTradeHistory(String clientId) {
		List<Trade> tradeHistory = null;
		try {
		 tradeHistory = tradeDaoImpl.getAllTradesById(clientId);
	
		if(Objects.isNull(tradeHistory) || tradeHistory.isEmpty()) {
			throw new IllegalArgumentException("There are no trades for the given clientId");
		}
		}
		catch(DataAccessException e) {
			e.printStackTrace();
			throw new DatabaseException("Server Eroor related to database access");
		}
		
		return tradeHistory;
	}
	
	
	public List<Price> getAllPrices() {
		List<Price> priceList = null;
		try {
			priceList = fmtsDao.getAllPrices(); 
		}
		
		catch (ResponseStatusException e) {

			if (e.getReason().equals(HttpStatus.NOT_ACCEPTABLE))

				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

			if (e.getReason().equals(HttpStatus.NOT_FOUND)) {

				throw new ResponseStatusException(HttpStatus.NOT_FOUND);

			}

		}

		catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return priceList;
	}
	
	public List<Price> getAllPricesByFilter(String category) {
		List<Price> priceList = null;
		try {
			priceList = fmtsDao.getAllPricesByFilter(category); 
		}
		
		catch (ResponseStatusException e) {

			if (e.getReason().equals(HttpStatus.NOT_ACCEPTABLE))

				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

			if (e.getReason().equals(HttpStatus.NOT_FOUND)) {

				throw new ResponseStatusException(HttpStatus.NOT_FOUND);

			}

		}

		catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return priceList;
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
