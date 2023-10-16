package com.capstone.fidelite.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.capstone.fidelite.models.Preferences;
import com.capstone.fidelite.services.ClientService;
import com.capstone.fidelite.services.PreferencesService;

@RestController
@RequestMapping("/client/preferences")
@CrossOrigin("http://localhost:4200")
@Transactional
public class PreferencesController {
	@Autowired
	private PreferencesService preferencesService;
	
	@PostMapping("/setPreferences")
	public ResponseEntity<String> setPrefernces(@RequestParam String clientId, @RequestBody Preferences preferences) {
		try {
			preferencesService.setPreferences(clientId, preferences);
			return ResponseEntity.ok("Set preferences completed");
		}catch(Exception e) {
			System.out.println("catch block of preferences");
			e.printStackTrace();
			throw new ServerErrorException("Unable to set preferences",e);
		}
	}
	
	@GetMapping("/getPreference")
	public ResponseEntity<Object> getPreference(@RequestParam String clientId) {
		try {
			
			Preferences p = preferencesService.getPreference(clientId);
			p.setRoboAdvisorCheck(1);
			return ResponseEntity.status(HttpStatus.OK).body(p);
		}catch(ClientPreferenceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	
}
