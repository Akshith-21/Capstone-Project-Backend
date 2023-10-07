package com.capstone.fidelite.integration;


import java.util.List;
import java.util.Map;

import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.capstone.fidelite.models.OrderFMTS;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.TradeFMTS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface FMTSDao {
	ClientFMTS verifyClientInformation(Client client)  throws JsonProcessingException;
	List<Portfolio> queryUpdatedPortfolios(Map<String,Double> instrumentMap)throws JsonMappingException, JsonProcessingException;
	TradeFMTS executeTrade(OrderFMTS orderFMTS) throws JsonMappingException, JsonProcessingException;
}
