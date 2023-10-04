package com.capstone.fidelite.services;

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
	private FMTSDao fmtsDao;

	@Autowired
	Logger logger;
	
	public String login(String email, String pswd) throws SQLException, JsonProcessingException {
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
				return fmtsResponse.getClientId();
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
		if (verifyEmailAddress(enteredEmail) == 0
				&& clientDao.doesClientIdentificationAlreadyExist(clientIdentification) == 0) {

			try {
				fmtsResponse = fmtsDao.verifyClientInformation(client);
             
                client.getPerson().setId(fmtsResponse.getClientId());
                clientDao.insertPerson(client.getPerson());
                for(ClientIdentification identification:clientIdentification) {
                clientDao.insertClientIdentification(identification,fmtsResponse.getClientId());
                }
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
//	
//
//	
//
//	public boolean isLoggedIn = false;
//
//	public void login(String email, String inputPassword) throws SQLException {
//		Client client = clientDao.getClientsByEmail(email);
//		if (client == null) {
//			throw new DatabaseException("Client email is not there please register first");
//		}
//		Set<ClientIdentification> identifications = client.getClientIdentificationSet();
//
//		if (identifications != null) {
//			boolean validIdentification = false;
//
//			for (ClientIdentification identification : identifications) {
//				String value = identification.getValue();
//				if (value.equals(inputPassword)) {
//
//					validIdentification = true;
//					isLoggedIn = true;
//				}
//			}
//
//			if (!validIdentification) {
//				throw new IllegalArgumentException("Client Identification is Invalid");
//
//			}
//		} else {
//			throw new IllegalArgumentException("No client identification found for the email");
//
//		}
//	}
//
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
