package com.capstone.fidelite.dao.test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.fidelite.integration.ClientDao;
import com.capstone.fidelite.integration.DatabaseException;
import com.capstone.fidelite.integration.mapper.ClientMapper;
import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientIdentification;
import com.capstone.fidelite.models.Person;
import com.capstone.fidelite.models.Preferences;
//import com.capstone.fidelite.test.integration.DbTestUtils;
import com.capstone.fidelite.services.ClientService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class ClientDaoImplTest {

	@Autowired
	ClientDao clientDao;
	@Autowired
	ClientMapper clientMapper;
	@Autowired
	ClientService clientService;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void testClientServiceObject() {
		assertNotNull(clientService);
	}

	@Test
	void testClientMapperObject() {
		assertNotNull(clientMapper);
	}

	@Test
	void testJdbcTemplateObject() {
		assertNotNull(jdbcTemplate);
	}

	@Test
	void testClientDaoObject() {
		assertNotNull(clientDao);
	}

	@Test
	void testGetClientById() throws SQLException {
		String id = "Client1";
		Client clients = clientDao.getClientsByID("Client1");
		assertEquals(id, clients.getPerson().getId());
	}

	@Test
	void testGetClientByEmail() throws SQLException {
		Client clients = clientDao.getClientsByEmail("client1@example.com");
		System.out.println(clients);
		assertEquals("client1@example.com", clients.getPerson().getEmail());
	}

	@Test
	void testGetNonExistentClient() {
		assertThrows(DatabaseException.class, () -> {
			String id = "NonExistentClient";
			clientDao.getClientsByID(id);
		});
	}

	@Test
	void testGetClientWithNullID() {
		assertThrows(DatabaseException.class, () -> {
			String id = null;
			clientDao.getClientsByID(id);
		});
	}

	@Test
	@Rollback
	void testInsertPersonWorks() {
		int oldSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "c_person");
		Person newPerson = new Person("thfjmn@gmail.com", "7989", "28-11-2009", "India", "2097");
		clientMapper.insertPerson(newPerson);
		System.out.println(oldSize);
		assertEquals(oldSize + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "c_person"));
	}

	@Test
	@Rollback
	void testInsertClient() {
		Person newPerson = new Person("ritiyuio@gmail.com", "7989", "28-11-2009", "India", "2097");
		Set<ClientIdentification> clientIdentificationSet = new HashSet<>();
		clientIdentificationSet.add(new ClientIdentification("pan", "F234"));
		Client newClient = clientDao.addClient(newPerson, clientIdentificationSet);
		assertNotNull(newClient);
	}

	@Test
	@Rollback
	public void testAddClientWithDuplicateIdentification() {
		// Create a client with a specific Person and ClientIdentification
		assertThrows(DatabaseException.class, () -> {
			Person person = new Person("riti12@gmail.com", "1234", "28-11-2000", "India", "2097");
			Set<ClientIdentification> clientIdentificationSet = new HashSet<>();
			ClientIdentification identification = new ClientIdentification("DL", "DL1234"); // Create a
																							// ClientIdentification with
																							// a duplicate number
			clientIdentificationSet.add(identification);
			Client newClient = clientDao.addClient(person, clientIdentificationSet);

		});

	}

	@Test
	@Rollback
	public void testForInvalidEmail() {
		// Create a client with a specific Person and ClientIdentification
		assertThrows(IllegalArgumentException.class, () -> {
			String email = "invalid#gmail";
			clientService.verifyEmailAddress(email);

		});

	}

	@Test
	@Rollback
	public void testAddClientWithMultipleIdentifications() {
		Person person = new Person("john.deo@gmail.com", "Client33", "1990-01-15", "USA", "2098");
		Set<ClientIdentification> clientIdentificationSet = new HashSet<>();
		clientIdentificationSet.add(new ClientIdentification("DL", "Du1234"));
		clientIdentificationSet.add(new ClientIdentification("SSN", "123-45-6789"));
		Client newClient = clientDao.addClient(person, clientIdentificationSet);
		assertNotNull(newClient);
	}

	@Test
	void checkForSuccesfullLogin() {
		Person person = new Person("client1@example.com", "Client1", "1990-01-15", "USA", "2098");
		Set<ClientIdentification> clientIdentificationSet = new HashSet<>();
		clientIdentificationSet.add(new ClientIdentification("Passport", "P123456"));
		Client client = new Client(person, clientIdentificationSet);
		assertNotNull(clientMapper.getClientForLogin(client.getPerson().getEmail(), "P123456"));
	}

	@Test
	void throwExceptionWhenEmailIdIsNotPresent() {

		Person person = new Person("12345", "Client1", "1990-01-15", "USA", "2098");
		Set<ClientIdentification> clientIdentificationSet = new HashSet<>();
		clientIdentificationSet.add(new ClientIdentification("Passport", "P123456"));
		Client client = new Client(person, clientIdentificationSet);
		assertThrows(IllegalArgumentException.class, () -> {
			clientDao.getClientForLogin(client.getPerson().getEmail(), "P123456");

		});
	}

	@Test
	void testForNullEmailAddress() {

		Person person = new Person(null, "Client1", "1990-01-15", "USA", "2098");
		Set<ClientIdentification> clientIdentificationSet = new HashSet<>();
		clientIdentificationSet.add(new ClientIdentification("Passport", "P123456"));
		Client client = new Client(person, clientIdentificationSet);
		assertThrows(NullPointerException.class, () -> {
			clientDao.getClientForLogin(client.getPerson().getEmail(), "P123456");

		});
	}

	@Test
	void testGetIdFromEmail() {
		assertEquals("Client1", clientDao.getIdFromEmail("client1@example.com"));
	}

	@Test
	void testCheckIfRowExists() {
		assertEquals(1, clientDao.checkIfRowExists("Client1"));
	}

	@Test
	void testInsertPreferences() {
		assertTrue(clientDao.insertPreferences("Client6",
				new Preferences("Education", "high", "1000-5000", "1-5 years", 0)));
	}

	@Test
	void testUpdatePreferences() {
		assertTrue(clientDao.updatePreferences("Client1",
				new Preferences("Education", "High", "1000-5000", "1-5 years", 0)));
	}

	@Test
	void testInsertPreferencesBySize() {
		// Pre-conditions
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client6'");
		assertEquals(0, oldSize);
		assertTrue(clientDao.insertPreferences("Client6",
				new Preferences("Education", "High", "100000-300000", "1-5 years", 1)));
		// Post-conditions
		int newSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client6'");
		assertEquals(1, newSize);

	}

	@Test
	void testUpdatePreferenceForExistingPreference() {
		// Pre-conditions
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client5'");
		assertEquals(1, oldSize);

		assertTrue(clientDao.updatePreferences("Client5",
				new Preferences("Family Savings", "Low", "300000-500000", "5-10 years", 1)));

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
			clientDao.insertPreferences("Client6", new Preferences(null, "High", "100000-300000", "1-5 years", 1));
		});
	}

	@Test
	void testUpdateWithInvalidPrefernces() {
		int oldSize = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "c_preferences", "client_id='Client6'");
		assertEquals(0, oldSize);
		assertThrows(Exception.class, () -> {
			clientDao.updatePreferences("Client5", new Preferences(null, "Low", "300000-500000", "1-5 years", 1));
		});
	}
}

//	private JdbcTemplate jdbcTemplate;
//	private SimpleDataSource dataSource;
//	TransactionManager transactionManager;
//
///private DbTestUtils dbTestUtils;
//	private Connection connection;
//	private ClientDaoImpl clientDao;
//	DbTestUtils dbTestUtils;
//	JdbcTemplate jdbcTemplate;
//    
//
//	@BeforeEach
//	void setUp() {
//		dataSource = new SimpleDataSource();
////		connection = dataSource.getConnection();
////		clientDao = new ClientDaoImpl(dataSource);
//		transactionManager = new TransactionManager(dataSource);
//		transactionManager.startTransaction();
////  dbTestUtils = new DbTestUtils(connection);
////  jdbcTemplate = dbTestUtils.initJdbcTemplate();
//	}
//
//	@AfterEach
//	void tearDown() {
//		transactionManager.rollbackTransaction();
//		dataSource.shutdown();
//
//	}
//
//	@Test
//	void testDaoObject() {
//		assertNotNull(clientDao);
//	}
//
//	@Test
//	void testGetCLientbyEmail() throws SQLException {
//		String email = "client1@example.com";
//		Client client = clientDao.getClientsByEmail(email);
//		assertNotNull(client);
//	}
//
//	@Test
//	void testValueOfQueryClientbyEmail() throws SQLException {
//		Client clients = clientDao.getClientsByEmail("client1@example.com");
//		assertEquals("USA", clients.getPerson().getCountry());
//	}
//	
//	
//
//	@Test
//	void testInsertClient() {
//		int oldSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "c_person");
//		Person newPerson = new Person("riti@gmail.com", "144", "11-02-1002", "India", "12345");
//		Set<ClientIdentification> clientSet = new HashSet<>();
//		clientSet.add(new ClientIdentification("pan","SBFPR3421"));
//		clientDao.addClient(new Client(newPerson,clientSet));
//		int newSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "c_person");
//		assertEquals(oldSize + 1, newSize);
//
//	}
//	
//	@Test
//	void testGetCLientbyId() throws SQLException {
//		String id = "Client1";
//		Client client = clientDao.getClientsByID(id);
//		assertNull(client);
//	}
//	
//
////	@Test
////	void testGetClients_BySize() {
////		List<com.capstone.fidelite.models.Preferences> clients = clientDao.getPreferences();
////		assertEquals(5, clients.size());	
////	}
////	
//	@Test
//	void testInsertClient_BySize() {
//		int oldSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "preferences");
//		//String clientQuery = "select * from aa_client where client_id = " + clientId;
//		Preferences newClient = new Preferences("Client7", "Higher Studies", "less than 1 year", "HIGH","200000-300000",1);
//		clientDao.insertClientPreferences(newClient);
//		int newSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "preferences");
//		assertEquals(oldSize + 1, newSize);
//	}
//	
//

//	@Test
//	void testUpdateClient() {
//		String clientId = "Client2";
//		//String clientQuery = "select * from aa_client where client_id = " + clientId;
//		Preferences newClient = new Preferences("Client2","Health","HIGH","100000-200000","less than 1 year",1);
//		clientDao.updateClientPreferences(clientId, newClient);
//		clientDao.updateClientPreferences("Client2", newClient)
//		assertEquals("Peer pressure",clientDao .getInvestmentPurpose());
//	}
