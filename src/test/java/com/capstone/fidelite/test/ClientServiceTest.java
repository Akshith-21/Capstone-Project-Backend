//package com.capstone.fidelite.test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import javax.sql.DataSource;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import java.util.Map;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import com.capstone.fidelite.models.ClientIdentification;
//import com.capstone.fidelite.models.Person;
//import com.capstone.fidelite.dao.ClientDaoImpl;
//import com.capstone.fidelite.dao.DatabaseException;
//import com.capstone.fidelite.dao.SimpleDataSource;
//import com.capstone.fidelite.dao.TransactionManager;
//import com.capstone.fidelite.dao.test.DbTestUtils;
//import com.capstone.fidelite.dao.test.SimpleDataSourceTest;
//import com.capstone.fidelite.models.Client;
//import com.capstone.fidelite.models.Preferences;
//import java.util.List;
//import java.util.Map;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Set;
//import java.util.HashSet;
//
//import com.capstone.fidelite.services.ClientService;
//import com.capstone.fidelite.models.Client;
//import com.capstone.fidelite.models.ClientIdentification;
//import com.capstone.fidelite.models.Person;
//import com.capstone.fidelite.models.Preferences;
//import com.capstone.fidelite.services.ClientService;
//
//import com.capstone.fidelite.models.Client;
//import com.capstone.fidelite.models.ClientIdentification;
//import com.capstone.fidelite.models.Person;
//import com.capstone.fidelite.models.Preferences;
//import com.capstone.fidelite.services.ClientService;
//import java.util.Set;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.HashSet;
//
//
//class ClientServiceTest {
//
//	private ClientService clientService;
//	private SimpleDataSource dataSource;
//	TransactionManager transactionManager;
//
////	  private DbTestUtils dbTestUtils;
//	private Connection connection;
//	private ClientDaoImpl clientDao;
//	DbTestUtils dbTestUtils;
//	JdbcTemplate jdbcTemplate;
//
//	@BeforeEach
//	void setUp() throws SQLException {
//		dataSource = new SimpleDataSource();
//		connection = dataSource.getConnection();
//		clientDao = new ClientDaoImpl(dataSource);
//		transactionManager = new TransactionManager(dataSource);
//		transactionManager.startTransaction();
////		clientIdentificationSet.add(new ClientIdentification("test", "test"));
////		clientData.put("test@test.com", new Client(new Person("test@test.com", "1", LocalDate.now().toString(), "India", "test"), clientIdentificationSet));
////		clientData.put("test123@test.com", new Client(new Person("test123@test.com", "2", LocalDate.now().toString(), "US", "test1"), clientIdentificationSet));
//		Client newClient1 = clientDao.getClientsByEmail("client1@example.com");
//		Client newClient2 = clientDao.getClientsByEmail("client2@example.com");
//		dbTestUtils = new DbTestUtils(connection);
//		jdbcTemplate = dbTestUtils.initJdbcTemplate();
////        clientData.add(newClient2);
////        clientData.add(newClient1);
//		Map<String, Preferences> preferencesData = new HashMap<>();
//		Map<String, Client> clientData = new HashMap<>();
//
//        
//		clientService = new ClientService(clientDao);
//	}
//
//	@AfterEach
//	void cleanUp() {
//		clientService = null;
//	}
//
//	@Test
//	void clientServiceConstrucotrWorks() {
//		assertNotNull(clientService);
//	}
//
//	@Test
//	void clientServiceConstructorThrowsErrorWhenClientDataNull() {
//		assertThrows(NullPointerException.class, () -> {
//			Map<String, Preferences> preferencesData = new HashMap<>();
//			preferencesData.put("test@test.com", new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//			new ClientService(null, preferencesData);
//		});
//	}
//
//	@Test
//	void clientServiceConstructorThrowsErrorWhenPreferencesDataNull() {
//		assertThrows(NullPointerException.class, () -> {
//			Map<String, Client> clientData = new HashMap<>();
//			Set<ClientIdentification> clientIdentificationSet = new HashSet<>();
//			clientIdentificationSet.add(new ClientIdentification("test", "test"));
//			clientData.put("test@test.com",
//					new Client(new Person("test@test.com", "1", LocalDate.now().toString(), "India", "test"),
//							clientIdentificationSet));
//
//			new ClientService(clientData, null);
//		});
//	}
//
//	@Test
//	void clientServiceSignUpWorks() throws SQLException {
//		Client client = null;
//		client = clientDao.getClientsByEmail("client1@example.com");
//		if (client == null) {
//			clientService.register(client);
//		}
//		System.out.println(client.getPerson().getEmail());
//		assertFalse(clientService.verifyEmailAddress(client.getPerson().getEmail()));
//	}
//
//	@Test
//	void clientServiceSignUpThrowsErrorWhenEmailAlreadyExists() throws SQLException {
//		assertThrows(NullPointerException.class, () -> {
//			Client client = null;
//			client = clientDao.getClientsByEmail("client1@example.com");
//
//			clientService.register(client);
//		});
//	}
//
//	@Test
//	void clientServiceSignUpThrowsErrorWhenIdentificationAlreadyExists() throws SQLException {
//
//		assertThrows(NullPointerException.class, () -> {
//			Client client = null;
//			client = clientDao.getClientsByEmail("client1@example.com");
//			clientService.register(client);
//		});
//	}
//
//	@Test
//	void clientServicesVerifyEmailWorksWhenExists() throws SQLException {
//		Client client = clientDao.getClientsByEmail("client1@example.com");
//		assertTrue(clientService.verifyEmailAddress(client.getPerson().getEmail()));
//	}
//
//
//	@Test
//	void clientServicesVerifyEmailThrowsErrorWhenNotInCorrectFormat() {
//		assertThrows(IllegalArgumentException.class, () -> {
//			clientService.verifyEmailAddress("test123");
//		});
//	}
//
//	@Test
//	void clientServicesVerifyClientIdentificationWorks() throws SQLException {
//		Client client = null;
//		client = clientDao.getClientsByEmail("client1@example.com");
//		assertFalse(clientService.verifyClientIdentificationExists(client.getClientIdentificationSet()));
//	}
//
//	@Test
//	void clientServicesVerifyClientIdentificationExistsThrowsErrorWhenNull() {
//		assertThrows(NullPointerException.class, () -> {
//			clientService.verifyClientIdentificationExists(null);
//		});
//	}
//
//	@Test
//	public void testInvalidCredentials() throws SQLException {
//
//		String inputId = "some_id";
//		String expectedOutput = "Invalid email, Sign Up first";
//		Exception exception = assertThrows(DatabaseException.class, () -> {
//			 String email = "client2@gmail.com";
//				String inputPassword = "P123457";
//				clientService.login(email, inputPassword);
//		});
//		assertEquals(expectedOutput, exception.getMessage());
//	}
//
//	@Test
//	public void testEmailDoesNotExist() throws SQLException {
//
//		String inputId = "test";
//		String expectedOutput = "Invalid email, Sign Up first";
//		Exception exception = assertThrows(DatabaseException.class, () -> {
//			 String email = "client7@gmail.com";
//			 String inputPassword = "P123456";
//			clientService.login(email, inputPassword);
//		});
////        assertEquals(expectedOutput, exception.getMessage());
//	}
//
//	@Test
//	public void testClientIdentificationDoesNotMatch() throws SQLException {
//
//		String expectedOutput = "Client Identification is Invalid";
//		Exception exception = assertThrows(DatabaseException.class, () -> {
//			 String email = "client1@gmail.com";
//				String inputPassword = "P123457";
//				clientService.login(email, inputPassword);
//		});
//		assertEquals(expectedOutput, exception.getMessage());
//	}
//
//	@Test
//	public void testClientLoginSuccessful() throws SQLException {
//	    String email = "client1@gmail.com";
//		String inputPassword = "P123456";
//		clientService.login(email, inputPassword);
//		assertTrue(clientService.isLoggedIn);
//	}
//
//	@Test
//	void testAddClientPreferencesBySize() {
//		clientService.addPreferences("test@test.com",
//				new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//		assertEquals(1, clientService.getPreferencesData().size());
//	}
//
//	@Test
//	void testAddClientPreferencesByPurpose() {
//		clientService.addPreferences("test@test.com",
//				new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//		assertEquals("Education", clientService.getPreferencesData().get("test@test.com").getInvestmentPurpose());
//	}
//
//	@Test
//	void testAddClientPreferencesWithException() {
//		assertThrows(IllegalArgumentException.class, () -> {
//			clientService.addPreferences("best@test.com",
//					new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//		});
//	}
//
//	@Test
//	void testAddClientPreferencesWithFalseCheck() {
//		assertThrows(IllegalArgumentException.class, () -> {
//			clientService.addPreferences("best@test.com",
//					new Preferences("123t","Education", "High", "2000000", "1-5 years", 0));
//		});
//	}
//
//	@Test
//	void testUpdateClientPreferencesBySize() {
//		clientService.addPreferences("test123@test.com",
//				new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//		clientService.updatePreferences("test123@test.com",
//				new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//		assertEquals(1, clientService.getPreferencesData().size());
//	}
//
//	@Test
//	void testUpdateClientPreferencesByPurpose() {
//		clientService.addPreferences("client1@example.com",
//				new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//		clientService.updatePreferences("client1@example.com",
//				new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//		assertEquals("Long term insurance",
//				clientService.getPreferencesData().get("test123@test.com").getInvestmentPurpose());
//	}
//
//	@Test
//	void testUpdateClientPreferencesWithException() {
//		assertThrows(IllegalArgumentException.class, () -> {
//			clientService.updatePreferences("test@test.com",
//					new Preferences("123t","Education", "High", "2000000", "1-5 years", 1));
//		});
//	}
//
//}
