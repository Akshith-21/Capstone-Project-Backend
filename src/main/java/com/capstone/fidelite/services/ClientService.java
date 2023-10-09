package com.capstone.fidelite.services;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.fidelite.integration.*;
import com.capstone.fidelite.integration.mapper.ClientMapper;
import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.capstone.fidelite.models.ClientIdentification;
import com.capstone.fidelite.models.Person;
import com.capstone.fidelite.models.Preferences;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class ClientService {

	@Autowired
	private ClientDaoImpl clientDao;
	
	@Autowired
	private TradeDaoImpl tradeDao;

	@Autowired
	private FMTSDao fmtsDao;

	@Autowired
	Logger logger;
	
	public ClientFMTS login(String email, String pswd) throws SQLException, JsonProcessingException {
		System.out.println(email+"email");
		Client 	client = clientDao.getClientsByEmail(email);
		System.out.println(client+"client");
		ClientFMTS fmtsResponse= null;
		if(client!=null) {
			System.out.println("in client");
			Set<ClientIdentification> identifications = client.getClientIdentificationSet();
						if (identifications != null) 
						{
							System.out.println("in client 2");

							for (ClientIdentification identification : identifications) {
							    	String value = identification.getValue();
							    	System.out.println(value+"value");
							    	System.out.println(pswd+"pswd");
							    	if (value.equals(pswd))
							    	{
										System.out.println("in client 3");
										 fmtsResponse = fmtsDao.verifyClientInformation(client);
							    		break;
							    	}
							}			
						}
						System.out.println(fmtsResponse.getClientId()+"hfjdf");
				return fmtsResponse;
		}
		else {
			throw new DatabaseException("Client does exist in db, register first");
		}
		
	}

	public ClientFMTS register(Client client) throws SQLException {
		System.out.println(client);
		String enteredEmail = client.getPerson().getEmail();
		
		Set<ClientIdentification> clientIdentification = client.getClientIdentificationSet();
		ClientFMTS fmtsResponse = null;
		System.out.println(clientDao.doesClientIdentificationAlreadyExist(clientIdentification) == 0);
		if (verifyEmailAddress(enteredEmail) == 0
				&& clientDao.doesClientIdentificationAlreadyExist(clientIdentification) == 0) {
            System.out.println("PASSED FUNCTIONALITY **********");
			try {
				fmtsResponse = fmtsDao.verifyClientInformation(client);
             
                client.getPerson().setId(fmtsResponse.getClientId());
                clientDao.insertPerson(client.getPerson());
                for(ClientIdentification identification:clientIdentification) {
                clientDao.insertClientIdentification(identification,fmtsResponse.getClientId());
                }
                tradeDao.insertBalance(client.getPerson().getId(), new BigDecimal(1000000.00));
			} catch (Exception e) {
				String msg = "Error while inserting Person, email Already exist";
				e.printStackTrace();
				throw new DatabaseException(msg, e);
			}
			
			return fmtsResponse;
			
		}
		else {
			return null;
		}
	}

	public int verifyEmailAddress(String email) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Email is not in the correct format");

		}
		return clientDao.doesEmailAlreadyExist(email);

	}

//	private boolean verifyPreferences(String email) {
//		return preferencesData.containsKey(email);
//	}
//
//	public boolean verifyClientIdentificationExists(Set<ClientIdentification> clientIdentification) {
//		if (clientIdentification == null) {
//
//			throw new NullPointerException("Client Identification cannot be null");
//		}
//		for (Map.Entry<String, Client> mapElement : clientData.entrySet()) {
//			Set<ClientIdentification> set = mapElement.getValue().getClientIdentificationSet();
//			if (set.contains(clientIdentification)) {
//				return true;
//			}
//		}
//		return false;
//	}

//	public Set<ClientIdentification> getId(String email) {
//		Client temp = clientData.get(email);
//		if (temp != null) {
//			return temp.getClientIdentificationSet();
//		} else {
//			return null;
//		}
//	}

	public boolean isLoggedIn = false;

	

//	public void addPreferences(String email, Preferences preference) {
//
//		if (preference.getRoboAdvisorCheck() == 0) {
//			throw new IllegalArgumentException("Robo Advisor check is not accepted");
//		}
//
//		if (verifyEmailAddress(email) == true) {
//			preferencesData.put(email, preference);
//		} else
//			throw new IllegalArgumentException("Email is not registered");
//
//	}
//
//	public void updatePreferences(String email, Preferences preference) {
//
//		if (verifyPreferences(email) == true) {
//			preferencesData.put(email, preference);
//		} else
//			throw new IllegalArgumentException("Initial Preferences are not added yet");
//	}
//
//	public String getRiskToleranceByMail(String email) {
//		return preferencesData.get(email).getRiskTolerance();
//	}

}
