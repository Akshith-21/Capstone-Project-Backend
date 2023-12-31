package com.capstone.fidelite.integration;


import java.util.List;
import java.util.Map;

import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.capstone.fidelite.models.InvestmentDetails;
import com.capstone.fidelite.models.OrderFMTS;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.Price;
import com.capstone.fidelite.models.TradeFMTS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface FMTSDao {
	ClientFMTS verifyClientInformation(Client client)  throws JsonProcessingException;
	List<Portfolio> queryUpdatedPortfolios(Map<String,InvestmentDetails> instrumentMap)throws JsonMappingException, JsonProcessingException;
	TradeFMTS executeTrade(OrderFMTS orderFMTS) throws JsonMappingException, JsonProcessingException;
	List<Price> getAllPrices() throws JsonMappingException, JsonProcessingException;
	List<Price> getAllPricesByFilter(String category)throws JsonMappingException, JsonProcessingException;
}
