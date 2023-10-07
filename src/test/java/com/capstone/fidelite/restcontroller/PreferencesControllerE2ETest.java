package com.capstone.fidelite.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.capstone.fidelite.models.Preferences;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PreferencesControllerE2ETest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	void testSetPrefernecesForExistingClient() {
		String clientId = "Client1";
		assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "C_PREFERENCES","client_Id = '"+clientId+"'"));
		String investmentPurpose = "Education";
		String riskTolerance = "riskTolerance";
		String incomeCategory = "incomeCategory";
		String lengthOfInvestment = "lengthOfinvestment";
		int roboAdvisorCheck = 1;
		Preferences preferences = new Preferences(investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment,
				roboAdvisorCheck);

		ResponseEntity<String> response = restTemplate.postForEntity("/setPreferences?clientId="+clientId,preferences,String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "C_PREFERENCES","client_Id = '"+clientId+"'"));
		
	}

	@Test
	void testSetPrefernecesForNewClient() {
		String clientId = "Client5";
		assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "C_PREFERENCES","client_Id = '"+clientId+"'"));
		String investmentPurpose = "Education";
		String riskTolerance = "riskTolerance";
		String incomeCategory = "incomeCategory";
		String lengthOfInvestment = "lengthOfinvestment";
		int roboAdvisorCheck = 1;
		Preferences preferences = new Preferences(investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment,
				roboAdvisorCheck);

		ResponseEntity<String> response = restTemplate.postForEntity("/setPreferences?clientId="+clientId,preferences,String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "C_PREFERENCES","client_Id = '"+clientId+"'"));
		
	}

}
