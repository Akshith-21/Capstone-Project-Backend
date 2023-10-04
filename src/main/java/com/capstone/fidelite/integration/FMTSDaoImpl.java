package com.capstone.fidelite.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class FMTSDaoImpl implements FMTSDao {

	
	
	@Override
	public ClientFMTS verifyClientInformation(Client client) throws JsonMappingException, JsonProcessingException {
		ClientFMTS response = null;
		RestTemplate restTemplate = new RestTemplate();
		
		ClientFMTS clientFMTS = new ClientFMTS();
		clientFMTS.setClientId("");
		clientFMTS.setEmail(client.getPerson().getEmail());
		clientFMTS.setToken("");

		String url = "http://localhost:3000/fmts/client";

		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
System.out.println(clientFMTS);
		HttpEntity<ClientFMTS> requestEntity = new HttpEntity<>(clientFMTS, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		
		if(responseEntity.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
        	throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE); 
   	
        }
        else if(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
        }
		
		ObjectMapper mapper = new ObjectMapper();
		response = mapper.readValue(responseEntity.getBody(), ClientFMTS.class);
		System.out.println(response);
		
		return response;
	}

}
