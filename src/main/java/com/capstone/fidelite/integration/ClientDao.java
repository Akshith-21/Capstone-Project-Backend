package com.capstone.fidelite.integration;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.capstone.fidelite.models.Preferences;
import com.capstone.fidelite.models.Person;
import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientIdentification;

public interface ClientDao {
	
	 Client getClientsByEmail(String email) throws SQLException;
	 Client getClientsByID(String id) ;
//	 Client addClient(Person person, Set<ClientIdentification> clientIdentification);

     void insertPerson(Person person);
	 void insertClientIdentification(@Param("clientIdentification")ClientIdentification clientIndentification, @Param("clientId") String clientId);
	 int doesEmailAlreadyExist(String email);
	 int doesClientIdentificationAlreadyExist(Set<ClientIdentification> clientIdentification);
	 
	 Client getClientForLogin(String email, String pswd);
     String getIdFromEmail(String email);
     Integer checkIfRowExists(String string);
     boolean insertPreferences(String clientId, Preferences preference);
     boolean updatePreferences(String clientId, Preferences preference);


}

