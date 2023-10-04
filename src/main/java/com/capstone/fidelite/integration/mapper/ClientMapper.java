package com.capstone.fidelite.integration.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientIdentification;
import com.capstone.fidelite.models.Person;
import com.capstone.fidelite.models.Preferences;

public interface ClientMapper {
	 Client getClientsByEmail(String email) throws SQLException;
	 Client getClientsByID(String id);
	 
	 void insertPerson(Person person);
	 void insertClientIdentification(@Param("clientIdentification")ClientIdentification clientIndentification, @Param("clientId") String clientId);
	 int doesEmailAlreadyExist(String email);
	 int doesClientIdentificationAlreadyExist(ClientIdentification clientIdentification);
	 
	 Client getClientForLogin(@Param("email")String email,@Param("value")String pswd);

	 public String getIdFromEmail(String email);
	 public Integer checkIfRowExists(String clientId);
	 public Integer insertPreferences(@Param("clientId") String clientId,@Param("preference") Preferences preference);
	 public Integer updatePreferences(@Param("clientId") String clientId,@Param("preference") Preferences preference);

}