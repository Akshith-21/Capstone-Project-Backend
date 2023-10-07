package com.capstone.fidelite.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.capstone.fidelite.models.Preferences;
import com.capstone.fidelite.services.PreferencesService;

@RestController
public class PreferencesController {
	@Autowired
	private PreferencesService preferencesService;
	
	@PostMapping("/setPreferences")
	public ResponseEntity<String> setPrefernces(@RequestParam String clientId, @RequestBody Preferences preferences) {
		try {
			preferencesService.setPreferences(clientId, preferences);
			return ResponseEntity.ok("Set preferences completed");
		}catch(Exception e) {
			throw new ServerErrorException("Unable to set preferences",e);
		}
	}
	
}
