package com.capstone.fidelite.restcontroller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.services.ClientService;

@RestController
@RequestMapping("/trade")
public class TradeController {
	@Autowired
	Logger logger;

	@Autowired
	ClientService clientService;

	@PostMapping("/execute")
	ResponseEntity<ClientDataTransferObject> registerClient(@RequestBody Client client) throws SQLException{
}
