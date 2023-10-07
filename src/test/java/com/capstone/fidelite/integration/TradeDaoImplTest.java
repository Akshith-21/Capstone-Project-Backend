package com.capstone.fidelite.integration;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.fidelite.integration.*;
import com.capstone.fidelite.models.Direction;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.Trade;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class TradeDaoImplTest {
    
	@Autowired
	@Qualifier("trade")
	private TradeDao tradeDaoMyBatis;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	
	@Test
	void testClientTradeSuccessfullyAdded() {
		Trade trade = new Trade(50.00, Direction.BUY, "AAPL", "Client1", "5", 256.56);
		int oldSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "C_TRADE");
		tradeDaoMyBatis.insertTrade(trade);
		assertEquals(oldSize + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "C_TRADE"));

	}

	@Test
	void testClientTradeHistorySuccesfullyRetrieved() {
		List<Trade> tradeList = tradeDaoMyBatis.getAllTradesById("Client1");
		assertTrue(tradeList.size()>0);
	}
	@Test
	void testClientTradeHistoryInvalidClientIdThrowsException() {
		assertThrows(DatabaseException.class,()->{
			List<Trade> tradeList = tradeDaoMyBatis.getAllTradesById("-1");

		});

	}
	
	@Test
	void testGetPortfolioReturnsNotNull() {
		Map<String, Object> map = tradeDaoMyBatis.getPortfolio("Client1", "AAPL");
		assertNotNull(map);
	}
	
	@Test
	void testGetPortfolioWorks() {
		Map<String, Object> map = tradeDaoMyBatis.getPortfolio("Client1", "AAPL");
		assertEquals((String)map.get("CLIENT_ID"), "Client1");
		assertEquals((String)map.get("INSTRUMENT_ID"), "AAPL");
	}
	
	@Test
	void testGetAllPortfolioReturnsNotNull() {
		List<Portfolio> portfolioList = tradeDaoMyBatis.getAllPortfolioMyBatis("Client1");
		assertNotNull(portfolioList);
	}
	
	@Test
	void testGetAllPortfolioWorks() {
		List<Portfolio> portfolioList = tradeDaoMyBatis.getAllPortfolioMyBatis("Client1");
		assertTrue(portfolioList.size() > 0);
	}
	
	@Test
	void testGetAllPortfolioClientWithNoPortfolioUsingMyBatis() {
		List<Portfolio> portfolioList = tradeDaoMyBatis.getAllPortfolioMyBatis("Client6");
		assertTrue(portfolioList.size() == 0);
	}
	
	@Test
	void testInsertPortfolioWorks() {
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client6'" );
		tradeDaoMyBatis.insertPortfolioMyBatis("Client6", "AAPL", 50);
		assertEquals(oldSize + 1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client6'" ));
	}
	
	@Test
	void testInsertPortfolioThrowsExceptionWhenDuplicateClientIdAndInstrumentId() {
		assertThrows(DuplicateKeyException.class, () -> {
			tradeDaoMyBatis.insertPortfolioMyBatis("Client1", "AAPL", 50);
		});
	}
	
	@Test
	void testInsertPortfolioThrowsExceptionWhenClientIdInvalid() {
		assertThrows(DataIntegrityViolationException.class, () -> {
			tradeDaoMyBatis.insertPortfolioMyBatis("X", "AAPL", 50);
		});
	}
	
	@Test
	void testInsertPortfolioThrowsExceptionWhenInstrumentIdInvalid() {
		assertThrows(DataIntegrityViolationException.class, () -> {
			tradeDaoMyBatis.insertPortfolioMyBatis("Client1", "X", 50);
		});
	}
	
	@Test void testUpdatePortfolioWorks() {
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client1' and instrument_id = 'AAPL' and current_holdings = 250" );
		tradeDaoMyBatis.updatePortfolioMyBatis("Client1", "AAPL", 50);
		assertEquals(oldSize, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client1' and instrument_id = 'AAPL' and current_holdings = 50" ));
	}
	
	//TODO: Write More Update Tests
	
	@Test void testUpdatePortfolioThrowsErrorForInvlaidId() {
		assertThrows(DatabaseException.class, () -> {
			tradeDaoMyBatis.updatePortfolioMyBatis("X", "AAPL", 50);
		});
	}
	
	@Test void testUpdatePortfolioThrowsErrorForInvlaidInstrument() {
		assertThrows(DatabaseException.class, () -> {
			tradeDaoMyBatis.updatePortfolioMyBatis("Client1", "X", 50);
		});
	}
	
	@Test
	void deletePortfolioWorks() {
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client1' and instrument_id = 'AAPL'" );
		tradeDaoMyBatis.deletePortfolio("Client1", "AAPL");
		assertEquals(oldSize - 1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_Portfolio", "client_id = 'Client1' and instrument_id = 'AAPL'" ));
	}
	
	@Test
	void deletePortfolioThrowsExceptionWhenClientIdInvalid() {
		assertThrows(DatabaseException.class, () -> {
			tradeDaoMyBatis.deletePortfolio("X", "AAPL");
		});	
	}
	
	@Test
	void deletePortfolioThrowsExceptionWhenInstrumentIdInvalid() {
		assertThrows(DatabaseException.class, () -> {
			tradeDaoMyBatis.deletePortfolio("Client1", "X");
		});	
	}
	
	@Test
	void deletePortfolioThrowsExceptionWhenClientIdNotPresent() {
		assertThrows(DatabaseException.class, () -> {
			tradeDaoMyBatis.deletePortfolio("Client50", "AAPL");
		});	
	}
	
	@Test
	void testInsertBalanceSuccessful() {
		int oldSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "c_balance");
		tradeDaoMyBatis.insertBalance("Client5", 1000000);
		assertEquals(oldSize + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "c_balance"));
	}
	
	@Test
	void testGetBalanceSuccessful() {
		tradeDaoMyBatis.insertBalance("Client5", 1000000);
		double balance = tradeDaoMyBatis.getBalance("Client5");
		assertEquals(Double.valueOf(1000000), balance);
		
	}
	
	@Test
	void testUpdateBalanceSuccessful() {
		tradeDaoMyBatis.insertBalance("Client5", 1000000);
		double balance = tradeDaoMyBatis.getBalance("Client5");
		tradeDaoMyBatis.updateBalance("Client5", 2000000);
	    assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_balance","client_id = 'Client5'"));
		
	}
}
	
	


