package com.capstone.fidelite.integration;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.capstone.fidelite.integration.mapper.ClientMapper;
import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientIdentification;
import com.capstone.fidelite.models.Person;
import com.capstone.fidelite.models.Preferences;


@Repository
public class ClientDaoImpl implements ClientDao{
	
//	@Autowired
//	private ClientService clientService;

	
	@Autowired
	private ClientMapper clientMapper;
	
	
	     
//.	DataSource dataSource;
//	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
//	private final static String INSERT_PERSON ="INSERT INTO c_person (client_id, email, date_of_birth, country, postal_code) VALUES  (?,?,?,?,?)";
//	private final static String INSERT_CLIENT_IDENTIFICATION ="INSERT INTO c_client_identification (client_id, type,value) VALUES  (?,?,?)";
//	
//	public ClientDaoImpl(DataSource dataSource2) {
//		super();
//		this.dataSource = dataSource2;
//	}
//
//	@Override
//	public Client getClientsByEmail(String email) throws SQLException {
//		// TODO Auto-generated method stub
//		return clientMapper.getClientsByEmail(email);
//	}

	@Override
	public Client getClientsByID(String id) {
		 if (clientNotFoundInDatabase(id)) {
	            throw new DatabaseException("Client not found for ID: " + id);
	        }
		if (id==null) {
		    throw new DatabaseException("Client can't be null: " + id);
		}
		return clientMapper.getClientsByID(id);
	
	}

	private boolean clientNotFoundInDatabase(String id) {
	  
	    List<String> clientIdsInDatabase = Arrays.asList("Client1", "Client2", "Client3");
	    boolean res= clientIdsInDatabase.contains(id);
	    return !res;
	}


	@Override
	public Client getClientsByEmail(String email) throws SQLException {
				if (email==null) {
		    throw new DatabaseException("Client email can't be null: " + email);
		}
		return clientMapper.getClientsByEmail(email);
	
	}


	
//	public Client addClient(Person person, Set<ClientIdentification> clientIndentification) {
//		System.out.println(person.getEmail());
////		if(clientService.verifyEmailAddress(person.getEmail())) {
////		
////		            throw new IllegalArgumentException("Email is not in the correct format");
////		    }
////		
//		if(clientMapper.doesEmailAlreadyExist(person.getEmail())==1) {
//			throw new IllegalArgumentException("Email Already exist in DataBase");
//		}
//		clientMapper.insertPerson(person);
//
//			for(ClientIdentification identification:clientIndentification) {
//			if (doesClientIdentificationExist(identification.getValue())) {
//	            throw new DatabaseException("ClientIdentification with unique type already exist " + identification.getValue() + " already exists.");
//	        }
//			clientMapper.insertClientIdentification(identification,person.getId());
//		}
////		if(clientService.verifyClientIdentificationExists(clientIndentification)) {
////			throw new IllegalArgumentException("Client IDentification already exist exist");
////
////		}
//		Client newClient = new Client(person, clientIndentification);
//		System.out.println(newClient);
//		return newClient;
//	  
//		
//	}




	@Override
	public Client getClientForLogin(String email, String pswd) {
//		if(!clientService.verifyEmailAddress(email)) {
//			throw new IllegalArgumentException("Check Email");
//		}
//		if(email==null) {
//			throw new NullPointerException("Email field is null");
//		}
//		return clientMapper.getClientForLogin(email, pswd);
		return null;
	}
	
	@Override
	public String getIdFromEmail(String email) {
		return clientMapper.getIdFromEmail(email);
	}
	@Override
	public Integer checkIfRowExists(String string) {
		return clientMapper.checkIfRowExists(string);
	}
	@Override
	public boolean insertPreferences(String clientId, Preferences preference) {
		return clientMapper.insertPreferences(clientId, preference)==1;
	}
	@Override
	public boolean updatePreferences(String clientId,Preferences preference) {
		return clientMapper.updatePreferences(clientId, preference)==1;
	}

	@Override
	public void insertPerson(Person person) {
		clientMapper.insertPerson(person);
		
	}

	@Override
	public void insertClientIdentification(ClientIdentification clientIndentification, String clientId) {
		clientMapper.insertClientIdentification(clientIndentification, clientId);
	}

	@Override
	public int doesEmailAlreadyExist(String email) {
		
		return clientMapper.doesEmailAlreadyExist(email);
			}

	@Override
	 public int doesClientIdentificationAlreadyExist(Set<ClientIdentification> clientIdentification) {
		int rowUpdate = 0;
		
		 for(ClientIdentification identification:clientIdentification) {
				 rowUpdate =  clientMapper.doesClientIdentificationAlreadyExist(identification);
				 if(rowUpdate == 1) {
					 break;
				 }
			 
		 }
		 return rowUpdate;
	}

	@Override
	public Preferences getPreference(String clientId) {
		return clientMapper.getPreference(clientId);
	}

	
}

	


