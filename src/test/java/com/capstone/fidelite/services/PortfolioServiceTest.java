package com.capstone.fidelite.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.fidelite.models.Direction;
import com.capstone.fidelite.models.Instrument;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.Trade;
import com.capstone.fidelite.services.PortfolioService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class PortfolioServiceTest {

	@Autowired
	public PortfolioService portfolioService;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
//	  Map<String, List<Portfolio>>portfolioData;
//	  Map<String, List<Trade>> tradeHistoryData;

	@Test
	void testGetAllPortfolioNotNull() {
		List<Portfolio> listOfPortfolio = portfolioService.getAllPortfolio("Client1");
		assertNotNull(listOfPortfolio);
	}
	
	@Test
	void testGetAllPortfolioReturnsValues() {
		List<Portfolio> listOfPortfolio = portfolioService.getAllPortfolio("Client1");
		assertTrue(listOfPortfolio.size() > 0);
	}
	
	@Test
	void testGetAllPortfolioWhenClientIdInvalid() {
		List<Portfolio> listOfPortfolio = portfolioService.getAllPortfolio("X");
		assertTrue(listOfPortfolio.size() == 0);	}
	
	@Test
	void testGetAllPortfolioWhenClientHasNoPortfolio() {
		List<Portfolio> listOfPortfolio = portfolioService.getAllPortfolio("Client6");
		assertTrue(listOfPortfolio.size() == 0);
	}
	
	@Test
	void testAddPortfolioWhenAlreadyPresent() {
		Trade trade = new Trade(200, 2, Direction.BUY, "AAPL", "Client1", "5", 100);
		portfolioService.addPortfolio(trade);
		assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client1' AND instrument_id = 'AAPL' AND current_holdings = 252"));
	}
	
	@Test
	void testAddPortfolioWhenNotPresent() {
		Trade trade = new Trade(500, 10, Direction.BUY, "GOOGL", "Client1", "5", 100);
		portfolioService.addPortfolio(trade);
		assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client1' AND instrument_id = 'GOOGL'"));
	}
	
	@Test
	void testSellPortfolioWhenAlreadyPresent() {
		Trade trade = new Trade(1500, 10, Direction.SELL, "AAPL", "Client1", "5", 100);
		portfolioService.sellPortfolio(trade);
		assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client1' AND instrument_id = 'AAPL' AND current_holdings = 240"));
	}
	
	@Test
	void testSellPortfolioWhenNotPresent() {
		assertThrows(IllegalStateException.class, ()-> {
			Trade trade = new Trade(500, 10, Direction.SELL, "GOOGL", "Client1", "5", 100);
			portfolioService.sellPortfolio(trade);
		});
	}

	@Test
	void testGetTradeHistoryNotNull() {
		List<Trade> listOfTrade = portfolioService.getTradeHistory("Client2");
		assertNotNull(listOfTrade);
	}
	
	@Test
	void testGetTradeHistoryReturnsValues() {
		List<Trade> listOfTrade = portfolioService.getTradeHistory("Client2");
		assertTrue(listOfTrade.size() > 0);
	}
	
	@Test
	void testGetTradeHistoryWhenClientIdInvalid() {
		List<Trade> listOfTrade = portfolioService.getTradeHistory("X");
		assertTrue(listOfTrade.size() == 0);
	}


//	@Test
//	void testForClientWithoutPortfolio() {
//		List<Portfolio> listOfPortfolio = portfolioService.getPortfolioData("nonexistent@gmail.com");
//		assertNull(listOfPortfolio, "Expected null for a client with no portfolio");
//	}
//
//	@Test
//	void testForClientWithPortfolio() {
//		List<Portfolio> expectedPortfolio = portfolioData.get("riti@gmail.com");
//		List<Portfolio> actualPortfolio = portfolioService.getPortfolioData("riti@gmail.com");
//		assertNotNull(actualPortfolio, "Expected non-null data for a client with a portfolio");
//		assertEquals(expectedPortfolio, actualPortfolio, "Expected portfolio data to match");
//	}
//
//	@Test
//	void testForClientWithoutTrade() {
//		List<Trade> listofTrade = portfolioService.getTradeHistoryData("nonexistent@gmail.com");
//		assertNull(listofTrade, "Expected null for a client with no trade history");
//	}
//
//	@Test
//	void testForClientWithTrade() {
//		List<Trade> expectedTrade = tradeHistoryData.get("history@gmail.com");
//		List<Trade> actualTrade = portfolioService.getTradeHistoryData("history@gmail.com");
//		assertNotNull(actualTrade, "Expected non-null data for a client with a portfolio");
//		assertEquals(expectedTrade, actualTrade, "Expected portfolio data to match");
//	}
//
//	@Test
//	void testForClientTradeHistoryLessThan100() {
//		List<Trade> listOfTradeHistory = new ArrayList<>();
//		for (int i = 0; i < 80; i++) {
//			listOfTradeHistory.add(new Trade(100, 10, "BUY", "goog", "tech", "test", 0));
//		}
//		tradeHistoryData.put("history@gmail.com", listOfTradeHistory);
//		List<Trade> listOfTrade = portfolioService.getClientTradeHistorySorted("history@gmail.com");
//		assertNotNull(listOfTrade, "Expected non-null trade history data");
//		assertTrue(listOfTrade.size() < 100, "Expected trade history count to be less than 100");
//	}
//
//	@Test
//	void testForClientTradeHistoryMoreThan100() {
//		List<Trade> listOfTradeHistory = new ArrayList<>();
//		for (int i = 0; i < 150; i++) {
//			listOfTradeHistory.add(new Trade(100, 10, "BUY", "goog", "tech", "test", 0));
//		}
//		tradeHistoryData.put("history@gmail.com", listOfTradeHistory);
//		List<Trade> listOfTrade = portfolioService.getClientTradeHistorySorted("history@gmail.com");
//		assertNotNull(listOfTrade, "Expected non-null trade history data");
//		assertFalse(listOfTrade.size() > 100, "Expected trade history count to not be more than 100");
//	}
//
//	@Test
//	void testForClientTradeHistoryExactly100() {
//		List<Trade> listOfTradeHistory = new ArrayList<>();
//		for (int i = 0; i < 100; i++) {
//			listOfTradeHistory.add(new Trade(100, 10, "BUY", "goog", "tech", "test", 0));
//		}
//		tradeHistoryData.put("history@gmail.com", listOfTradeHistory);
//
//		List<Trade> listOfTrade = portfolioService.getClientTradeHistorySorted("history@gmail.com");
//		assertNotNull(listOfTrade, "Expected non-null trade history data");
//		assertEquals(100, listOfTrade.size(), "Expected trade history count to be exactly 100");
//	}

}
