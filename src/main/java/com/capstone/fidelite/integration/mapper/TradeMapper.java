package com.capstone.fidelite.integration.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.PortfolioDetails;
import com.capstone.fidelite.models.Trade;

public interface TradeMapper {
	public int insertTrade(Trade trade);

	public List<Trade> getAllTradesById(String clientId);
	
	List<PortfolioDetails> getPortFoliobyClientId(String clientId);

	Map<String, Object> getPortfolio(Map<String, Object> map);

	List<Portfolio> getAllPortfolio(String clientId);

	void insertPortfolio(Map<String, Object> map);

	int updatePortfolio(Map<String, Object> map);

	int deletePortfolio(Map<String, Object> map);

	double getBalance(String clientId);

	int insertBalance(@Param("clientId")String clientId,@Param("balance") BigDecimal balance);

	int updateBalance(@Param("clientId")String clientId,@Param("balance") BigDecimal balance);

}
