package com.capstone.fidelite.services;


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



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.fidelite.integration.ClientDao;
import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.capstone.fidelite.models.ClientIdentification;
import com.capstone.fidelite.models.Person;
import com.fasterxml.jackson.core.JsonProcessingException;

class ClientServiceTest {


	private Person person;
	private ClientIdentification clientIdentification;
	private Set<ClientIdentification> clientIdentifications;
	private Client client;
	
	
    @Mock
    private ClientDao clientDao;

    @Mock
    private ClientFMTS fmtsDao;

    
    @InjectMocks
    private ClientService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new Person("test@test.com", "123", LocalDate.now().toString(), "India", "test");
        clientIdentification = new ClientIdentification("aadhar","333-22-4444");
		clientIdentifications = new HashSet<ClientIdentification>();
		clientIdentifications.add(clientIdentification);
		client = new Client(person, clientIdentifications);

    }
   
    	@AfterEach
    	void tearDown() throws Exception {
    	}

     
    	@ParameterizedTest
    	@ValueSource(strings = { "client1@example.com","client2@example.com", "client3@example.com"})
    	void verifyExistingEmailAddress(String email) {
    		Mockito.when(clientDao.doesEmailAlreadyExist(email)).thenReturn(1);
    		assertEquals(service.verifyEmailAddress(email), 1);
    	}

     

//    	@ParameterizedTest
//    	@ValueSource(strings = { "Kin.s@gmail.com", "k1@gmail.com", "A_kinar@yahoo.com" })
//    	void TestVerifyEmailAdressFormat_success(String email) {
//    		Mockito.when(clientDao.emailExists(email)).thenReturn(true);
//    		assertDoesNotThrow(() -> clientService.verifyEmailAddress(email));
//    	}
//
//     
//
    	@ParameterizedTest
    	@ValueSource(strings = { "sadsad", "invalidemail@", "@invalidemail.com", "invalidemail.com" })
    	void verifyInvalidEmailAddress(String email) {
    		Mockito.when(clientDao.doesEmailAlreadyExist(email)).thenReturn(0);
    		assertThrows(IllegalArgumentException.class, () -> service.verifyEmailAddress(email));
    	}
//
//     
//
//    	@ParameterizedTest
//    	@ValueSource(strings = { "nonexistentemail@test.com", "notfound@gmail.com", "missingemail@abc.com" })
//    	void verifyNonexistentEmailAddress(String email) {
//    		Mockito.when(clientDao.emailExists(email)).thenReturn(false);
//    		// Should return true for emails that are not registered yet
//    		assertTrue(clientService.verifyEmailAddress(email));
//    	}
//
//     
//
//    	@Test
//    	void registerInvalidClientIdentification() {
//    		clientIdentification.setValue("333-22-44544");
//    		clientIdentifications.add(clientIdentification);
//
//     
//
//    		assertThrows(IllegalArgumentException.class,
//    				() -> clientService.registerClient(person, clientIdentifications, "1234"));
//    	}
//
//     

//    	@Test
//    	void registerValidClient() throws SQLException {
//
//     
//
//    		Mockito.doNothing().when(clientDao).register(Mockito.any(Client.class), Mockito.anyString());
//
//     
//
//    		assertDoesNotThrow(() -> clientService.registerClient(person, clientIdentifications, "123456"));
//    	}
//
//     
//
//    	@Test
//    	void registrationFailsOnExistingClientEmail() {
//
//     
//
//    		Mockito.when(clientDao.emailExists(person.getEmail())).thenReturn(true);
//
//     
//
//    		assertThrows(ClientAlreadyExistsException.class,
//    				() -> clientService.registerClient(person, clientIdentifications, "123456"));
//    	}
//
//     
//
//    	@Test
//    	void registrationAddsExistingClientWithNewEmail() {
//
//     
//
//    		Mockito.doThrow(ClientAlreadyExistsException.class).when(clientDao).register(Mockito.any(Client.class),
//    				Mockito.anyString());
//
//     
//
//    		assertThrows(ClientAlreadyExistsException.class,
//    				() -> clientService.registerClient(person, clientIdentifications, "123456"));
//    	}
//
//     
//
//    	@Test
//    	void testAddPreference() throws Exception {
//
//     
//
//    		Mockito.when(clientDao.getClient(client.getClientId())).thenReturn(client);
//    		Mockito.when(clientDao.addClientPreferences(preferences,
//    				client.getClientId())).thenReturn(1);
//
//     
//
//    		assertDoesNotThrow(() -> clientService.addPreferences(client.getClientId(), preferences, true));
//    	}
//
//     
//
//    	@Test
//    	public void testAddPreferencesWithNullPreference() {
//    		Mockito.doThrow(NullPointerException.class).when(clientDao).addClientPreferences(null,
//    				"123");
//    		assertThrows(NullPointerException.class, () -> clientService.addPreferences("123", null, true));
//    	}
//
//     
//
//    	@Test
//    	public void testAddPreferencesWithoutAcceptingTerms() {
//    		Preferences preferences = new Preferences("Retirement", "Low", "1-3 years", "Less than $50,000");
//    		assertThrows(RuntimeException.class, () -> clientService.addPreferences("123", preferences, false));
//
//     
//
//    	}
//
//     
//
//    	@Test
//    	public void testUpdatePreferenceWithExistingPreference() throws Exception {
//
//     
//
//    		Preferences newPreference = new Preferences("Retirement", "High", "1-3 years", "Less than $50,000");
//
//     
//
//    		Mockito.when(clientDao.updateClientPreferences(newPreference, "123")).thenReturn(1);
//
//     
//
//    		assertDoesNotThrow(() -> clientService.updatePreferences("123", newPreference));
//    	}
//
//     
//
//    	@Test
//    	public void testUpdatePreferenceWithNullPreference() {
//
//     
//
//    		Mockito.doThrow(NullPointerException.class).when(clientDao).updateClientPreferences(null, "123");
//
//     
//
//    		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//    				() -> clientService.updatePreferences("123", null));
//    		assertEquals("Preference cannot be null", exception.getMessage());
//    	}
//
//     
//
//    	@Test
//    	public void invalidEmailLogin() {
//
//     
//
//    		String email = "bhavesh@gmail.com";
//    		String password = "333-22-44445";
//
//     
//
//    		Mockito.when(clientDao.login(email, password)).thenReturn(false);
//
//     
//
//    		assertThrows(InvalidCredentialsException.class, () -> clientService.login(email, password));
//    	}
//
//     
//
//    	@Test
//    	public void validEmailLogin() {
//
//     
//
//    		String email = "bhavesh@gmail.com";
//    		String password = "333-22-4444";
//
//     
//
//    		Mockito.when(clientDao.login(email, password)).thenReturn(true);
//
//     
//
//    		assertTrue(clientService.login(email, password));
//    	}
//
//     
//
//    }
//
//    @Test
//    void testLoginSuccessful() throws SQLException, JsonProcessingException {
//        String email = "test@example.com";
//        String pswd = "password";
//
//        Client mockClient;
//        
//        Person mockPerson = new Person();
//        mockPerson.setEmail(email);
//
//        ClientIdentification mockIdentification = new ClientIdentification();
//        mockIdentification.setValue(pswd);
//        
//        mockClient.setPerson(mockPerson);
//        mockClient.getClientIdentificationSet(mockIdentification);
//
//        // Create a mock ClientFMTS object
//        ClientFMTS mockFMTSResponse = new ClientFMTS();
//        mockFMTSResponse.setClientId("1");
//
//        // Set up the mock behavior for clientDao
//        when(clientDao.getClientsByEmail(email)).thenReturn(mockClient);
//        Set<ClientIdentification> identifications = new HashSet<>();
//        identifications.add(mockIdentification);
//        when(mockClient.getClientIdentificationSet()).thenReturn(identifications);
//
//        // Set up the mock behavior for fmtsDao
//        when(fmtsDao.verifyClientInformation(mockClient)).thenReturn(mockFMTSResponse);
//
//        // Perform the login
//        String result = service.login(email, pswd);
//
//        // Verify the result
//        assertEquals("1", result);
//    }
//
//    @Test
//    void testLoginFailed() throws SQLException {
//        String email = "test@example.com";
//        String pswd = "password";
//
//        // Set up the mock behavior for clientDao when login fails
//        when(clientDao.getClientsByEmail(email)).thenReturn(null);
//
//        // Perform the login
//        Throwable exception = assertThrows(DatabaseException.class, () -> service.login(email, pswd));
//
//        // Verify the exception message
//        assertEquals("Client does exist in db, register first", exception.getMessage());
//    }
}



