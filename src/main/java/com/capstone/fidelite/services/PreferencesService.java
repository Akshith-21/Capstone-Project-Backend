package com.capstone.fidelite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.fidelite.integration.ClientDaoImpl;
import com.capstone.fidelite.models.Preferences;
import com.capstone.fidelite.restcontroller.ClientPreferenceNotFoundException;

@Service
public class PreferencesService {

	@Autowired
	private ClientDaoImpl clientDaoImpl;
	
	public void setPreferences(String clientId, Preferences preferences) {
		try {
			//String clientId = clientDaoImpl.getIdFromEmail(email);
			int isPresent = clientDaoImpl.checkIfRowExists(clientId);
			if(isPresent >0) {
				clientDaoImpl.updatePreferences(clientId, preferences);
			}else{
				clientDaoImpl.insertPreferences(clientId, preferences);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	public Preferences getPreference(String clientId) {
		Preferences p = clientDaoImpl.getPreference(clientId);
		if(p==null) {
			throw new ClientPreferenceNotFoundException("Client Preferences are not found");
		}
		return p;
	}
	
	
}
