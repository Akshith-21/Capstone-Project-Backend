package com.capstone.fidelite.integration;


import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.capstone.fidelite.models.OrderFMTS;
import com.capstone.fidelite.models.TradeFMTS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface FMTSDao {
	ClientFMTS verifyClientInformation(Client client)  throws JsonProcessingException;
	TradeFMTS executeTrade(OrderFMTS orderFMTS) throws JsonMappingException, JsonProcessingException;
}
