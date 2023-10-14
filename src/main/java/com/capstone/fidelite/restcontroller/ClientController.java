package com.capstone.fidelite.restcontroller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import com.capstone.fidelite.services.ClientService;
import com.capstone.fidelite.services.RoboAdvisorService;
import com.capstone.fidelite.integration.mapper.ClientMapper;
import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.capstone.fidelite.models.ClientIdentification;
import com.capstone.fidelite.models.Person;
import com.capstone.fidelite.models.Price;
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/client")

public class ClientController {
	@Autowired
	Logger logger;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	ClientMapper clientMapper;
	
	@Autowired
	RoboAdvisorService roboAdvisorService;
	
	@PostMapping("/register")
	 ResponseEntity<?> registerClient(@RequestBody Client client) throws SQLException{
//		System.out.println(client + "************");
		ResponseEntity<?> response = null;
		ClientFMTS clientFmts = null;
		try {
			
			clientFmts = clientService.register(client);
			if(clientFmts == null) {
				response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or ClientIdentification already exist");
			}
			else {
				response = ResponseEntity.status(HttpStatus.OK).body(new ClientDataTransferObject(clientFmts.getClientId(), clientFmts.getToken()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error");
		}
		return response;	
	}

	@PostMapping("/login")
	ResponseEntity<?> clientLogin(@RequestBody LoginRequest loginRequest) throws SQLException{
		String email = loginRequest.getEmail();
		String pswd = loginRequest.getPswd();
		System.out.println(email+"controllerpswd");
		ClientFMTS clientFmts;
		ResponseEntity<?> response = null;
		try {
			clientFmts = clientService.login(email,pswd);
			if(clientFmts==null) {
				response =ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Not Registered Please Sign Up");
			}
			response = ResponseEntity.status(HttpStatus.OK).body(new ClientDataTransferObject(clientFmts.getClientId(), clientFmts.getToken()));
		}
		catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error");
		}
		return response;
	}
	
	@GetMapping("/roboadvisor/{clientId}")
	ResponseEntity<?> getRoboAdvisor(@PathVariable String clientId ){
		List<Price> priceList = null;
		try {
			String riskTolerance = clientMapper.getRiskToleranceByClientId(clientId);
		    priceList = roboAdvisorService.buyRoboAdvisor(riskTolerance);
		    
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Proper Value Of Risk Tolerance Is Not Passed");
		}
		catch (ResponseStatusException e) {
	        	e.printStackTrace();

				if (e.getStatus().equals(HttpStatus.NOT_ACCEPTABLE))

					return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

				if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {

					return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

				}

			}

	    	catch(Exception e) {

	    		e.printStackTrace();

	    		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data Access Exception");

	    	}
		
		return ResponseEntity.status(HttpStatus.OK).body(priceList);
	}
	
	

}
