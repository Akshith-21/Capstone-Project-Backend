package com.capstone.fidelite.integration;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.capstone.fidelite.integration.mapper.TradeMapper;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.PortfolioDetails;
import com.capstone.fidelite.models.Trade;

@Repository("trade")
@Primary
public class TradeDaoImpl implements TradeDao {
	@Autowired
	private Logger logger;

	@Autowired
	private TradeMapper tradeMapper;

	@Override
	public void insertTrade(Trade trade) {
		int rowsUpdate = tradeMapper.insertTrade(trade);
	}

	@Override
	public List<Trade> getAllTradesById(String clientId) {
		if (!clientId.matches("[A-Za-z0-9]+")) {
			throw new DatabaseException("The client with given Id is not found in the database please do check the Id");
		}
		List<Trade> tradeList = tradeMapper.getAllTradesById(clientId);
		if (Objects.isNull(tradeList) || tradeList.size() == 0) {
			System.out.println("No Values have been retrieved by Database for the given clientId");
		}

		return tradeList;
	}
	
	@Override
	public List<PortfolioDetails> getUpdatedPortfolios(String clientId){
		List<PortfolioDetails> instrumentList = tradeMapper.getPortFoliobyClientId(clientId);
		System.out.println(instrumentList + "*********INSTRUMENT***");
		return instrumentList;
	}

	@Override
	public Map<String, Object> getPortfolio(String clientId, String instrumentId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clientId", clientId);
		paramMap.put("instrumentId", instrumentId);
		return tradeMapper.getPortfolio(paramMap);
	}

	@Override
	public List<Portfolio> getAllPortfolioMyBatis(String clientId) {
		List<Portfolio> portfolioList = tradeMapper.getAllPortfolio(clientId);
		return portfolioList;
	}

	@Override
	public void insertPortfolioMyBatis(String clientID, String instrumentId, double currentHoldings) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clientId", clientID);
		paramMap.put("instrumentId", instrumentId);
		paramMap.put("currentHoldings", currentHoldings);

		tradeMapper.insertPortfolio(paramMap);
	}

	@Override
	public int updatePortfolioMyBatis(String clientID, String instrumentId, double currentHoldings) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clientId", clientID);
		paramMap.put("instrumentId", instrumentId);
		paramMap.put("currentHoldings", currentHoldings);
		int result = tradeMapper.updatePortfolio(paramMap);
		if (result == 0) {
			throw new DatabaseException("Key invalid");
		}
		return result;
	}

	@Override
	public int deletePortfolio(String clientId, String instrumentId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clientId", clientId);
		paramMap.put("instrumentId", instrumentId);
		int result = tradeMapper.deletePortfolio(paramMap);
		if (result == 0) {
			throw new DatabaseException("Key invalid");
		}
		return result;
	}

	@Override
	public double getBalance(String clientId) {
		double balance = tradeMapper.getBalance(clientId);
		return balance;
	}


	@Override
	public int insertBalance(String clientId, double balance) {
        int rowsUpdate = tradeMapper.insertBalance(clientId, balance);
        return rowsUpdate;
	}

	@Override
	public int updateBalance(String clientId, double balance) {
        int rowsUpdate = tradeMapper.updateBalance(clientId, balance);
        return rowsUpdate;
	}

}
