package com.capstone.fidelite.integration;

import org.springframework.http.ResponseEntity;

import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface FMTSDao {
	ClientFMTS verifyClientInformation(Client client)  throws JsonProcessingException;
}
