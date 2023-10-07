package com.capstone.fidelite.restcontroller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.Order;
import com.capstone.fidelite.models.OrderFMTS;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.Trade;
import com.capstone.fidelite.services.ClientService;
import com.capstone.fidelite.services.PortfolioService;

@RestController
@RequestMapping("/trade")
public class TradeController {
	@Autowired
	Logger logger;

	@Autowired
	PortfolioService portfolioService;
	

	@GetMapping("/portfolio/{clientId}")

    ResponseEntity<List<Portfolio>> getPortfolioDetails(@PathVariable String clientId){

		//System.out.println(clientId + "**************************8");

    	ResponseEntity<List<Portfolio>> response = null;

    	try {

    	List<Portfolio> updatedPortfolioList = portfolioService.getUpdatedPortfolios(clientId);

    	if(updatedPortfolioList == null) {

    		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    	}

         response =  ResponseEntity.status(HttpStatus.OK).body(updatedPortfolioList);

    	}

        catch (ResponseStatusException e) {
        	
        	e.printStackTrace();

			if (e.getReason().equals(HttpStatus.NOT_ACCEPTABLE))

				return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

			if (e.getReason().equals(HttpStatus.NOT_FOUND)) {

				return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			}

		}

    	catch(Exception e) {

    		e.printStackTrace();

    		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    	}

    	return response;

}

	@PostMapping("/execute")
	ResponseEntity<String> executeTrade(@RequestBody OrderFMTS order){
		Trade trade = null;
		System.out.println("Inside controller: " + order);
		try {
			trade = portfolioService.executeTrade(order);
			if(trade == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			return ResponseEntity.status(HttpStatus.OK).body("Order Successful");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
