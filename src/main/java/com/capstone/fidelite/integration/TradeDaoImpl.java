package com.capstone.fidelite.integration;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
		List<Trade> tradeList = tradeMapper.getAllTradesById(clientId);
		System.out.println(tradeList + "*********INSTRUMENT***");
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
	public void insertPortfolioMyBatis(String clientID, String instrumentId, double currentHoldings,BigDecimal totalInvestment) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clientId", clientID);
		paramMap.put("instrumentId", instrumentId);
		paramMap.put("currentHoldings", currentHoldings);
		paramMap.put("totalInvestment", totalInvestment);

		tradeMapper.insertPortfolio(paramMap);
	}

	@Override
	public int updatePortfolioMyBatis(String clientID, String instrumentId, double currentHoldings,BigDecimal totalInvestment ) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clientId", clientID);
		paramMap.put("instrumentId", instrumentId);
		paramMap.put("currentHoldings", currentHoldings);
		paramMap.put("totalInvestment", totalInvestment);
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
	public BigDecimal getBalance(String clientId) {
		BigDecimal balance = new BigDecimal(tradeMapper.getBalance(clientId)).setScale(2,RoundingMode.HALF_UP);
		return balance;
	}


	@Override
	public int insertBalance(String clientId, BigDecimal balance) {
        int rowsUpdate = tradeMapper.insertBalance(clientId, balance);
        return rowsUpdate;
	}

	@Override
	public int updateBalance(String clientId, BigDecimal balance) {
        int rowsUpdate = tradeMapper.updateBalance(clientId, balance);
        return rowsUpdate;
	}



}
