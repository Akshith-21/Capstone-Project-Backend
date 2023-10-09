package com.capstone.fidelite.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.fidelite.integration.FMTSDao;
import  com.capstone.fidelite.models.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
@Service
public class RoboAdvisorService {
	@Autowired
     FMTSDao fmtsDao;
   public  List<Price> buyRoboAdvisor(String riskTolerance) throws JsonMappingException, JsonProcessingException, IllegalAccessException{
    	if(riskTolerance.equals("HIGH")) {
    		List<Price> priceList = fmtsDao.getAllPricesByFilter("STOCK");
    		return priceList;
    	}
    	else if(riskTolerance.equals("MEDIUM")) {
    		List<Price> priceList = fmtsDao.getAllPricesByFilter("GOVT");
    		return priceList;
    	}
    	else if(riskTolerance.equals("LOW")) {
    		List<Price> priceList = fmtsDao.getAllPricesByFilter("CD");
    		return priceList;
    	}
    	else {
    		throw new IllegalAccessException("There is no proper risk tolerance value please check it");
    	}
    }
}
