package com.capstone.fidelite.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.fidelite.integration.ClientDaoImpl;
import com.capstone.fidelite.models.Preferences;

@SpringBootTest
@Transactional
class PreferencesTest {
//	
//	Preferences pref;
//
//	@BeforeEach
//	void setUp() throws Exception {
//		pref = new Preferences("Education","Medium","100000-200000","1-5 years", true);
//	}
//
//	@AfterEach
//	void tearDown() throws Exception {
//		pref = null;
//	}
//
//	@Test
//	void testSuccessFulObjectCreation() {
//		assertNotNull(pref);
//	}
//
//	@Test
//	void testPreferencesWithNullPurpose(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences(null, "Medium","100000-200000","1-5 years", true);
//		});
//	}
//	
//
//	@Test
//	void testPreferencesWithEmptyPurpose(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences("", "Medium","100000-200000","1-5 years", true);
//		});
//	}
//	
//	@Test
//	void testPreferencesWithNullRiskTolerance(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences("Education", null,"100000-200000","1-5 years", true);
//		});
//	}
//	
//	@Test
//	void testPreferencesWithEmptyRiskTolerance(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences("Education", "","100000-200000","1-5 years", true);
//		});
//	}
//	
//	@Test
//	void testPreferencesWithNullIncome(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences("Education", "Medium",null,"1-5 years", true);
//		});
//	}
//	
//	@Test
//	void testPreferencesWithEmptyIncome(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences("Education", "Medium","","1-5 years", true);
//		});
//	}
//	
//	@Test
//	void testPreferencesWithNullLength(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences("Education", "Medium","100000-200000",null, true);
//		});
//	}
//	
//	@Test
//	void testPreferencesWithEmptyLength(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences("Education", "Medium","100000-200000","", true);
//		});
//	}
//	
//	@Test
//	void testPreferencesWithNullCheck(){
//		assertThrows(IllegalArgumentException.class,()->{
//			new Preferences("Education", "Medium","100000-200000","1-5 years",null);
//		});
//	}
	
	@Autowired
	private ClientDaoImpl dao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@Test
	void testGetIdFromEmail() {
		assertEquals("Client1", dao.getIdFromEmail("client1@example.com"));
	}

	@Test
	void testCheckIfRowExists() {
		assertEquals(1, dao.checkIfRowExists("Client1"));
	}
	
	@Test
	void testInsertPreferences() {
		assertTrue(dao.insertPreferences("Client6",
				new Preferences("Educationssss", "high", "1000-5000", "1-5 years", 0)));
	}

	@Test
	void testUpdatePreferences() {
		assertTrue(dao.updatePreferences("Client1", new Preferences("education", "high", "1000-5000", "1-5 years", 0)));
	}
	
	@Test
	void testInsertPreferencesBySize() {
		// Pre-conditions
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client6'");
		assertEquals(0, oldSize);
		assertTrue(dao.insertPreferences("Client6", new Preferences("Education", "High", "100000-300000", "1-5 years", 1)));
		// Post-conditions
		int newSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client6'");
		assertEquals(1, newSize);

	}

	@Test
	void testUpdatePreferenceForExistingPreference() {
		// Pre-conditions
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client5'");
		assertEquals(1, oldSize);

		assertTrue(dao.updatePreferences("Client5", new Preferences("Family Savings", "Low", "300000-500000", "5-10 years", 1)));

		// Post-conditions
		int newSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences",
				"client_id='Client5' and investment_purpose = 'Family Savings'");
		assertEquals(1, newSize);
	}

	@Test
	void testInsertWithInvalidPreferences() {
		// Pre-conditions
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client6'");
		assertEquals(0, oldSize);
		assertThrows(Exception.class, () -> {
			dao.insertPreferences("Client6", new Preferences(null, "High", "100000-300000", "1-5 years", 1));
		});
	}
	
	
	@Test 
	void testUpdateWithInvalidPrefernces() {
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client6'");
		assertEquals(0, oldSize);
		assertThrows(Exception.class, () -> {
			dao.updatePreferences("Client5", new Preferences(null, "Low", "300000-500000", "1-5 years", 1));
		});
		
	}
                    
	
	
	
	
	

}
