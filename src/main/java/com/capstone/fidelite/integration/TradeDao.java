package com.capstone.fidelite.integration;

import java.util.List;
import java.util.Map;

import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.PortfolioDetails;
import com.capstone.fidelite.models.Trade;

public interface TradeDao {
	public void insertTrade(Trade trade);

	public List<Trade> getAllTradesById(String clientId);
	
	 List<PortfolioDetails> getUpdatedPortfolios(String clientId);

	Map<String, Object> getPortfolio(String clientId, String instrumentId);

	List<Portfolio> getAllPortfolioMyBatis(String clientId);

	void insertPortfolioMyBatis(String clientID, String instrumentId, double currentHoldings);

	int updatePortfolioMyBatis(String clientID, String instrumentId, double currentHoldings);

	int deletePortfolio(String clientId, String externalId);
	
	double getBalance(String clientId);
	
	int insertBalance(String clientId,double balance);
	
	int updateBalance(String clientId,double balance );

}
