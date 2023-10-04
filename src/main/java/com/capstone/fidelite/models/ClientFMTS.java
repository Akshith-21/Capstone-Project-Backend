package com.capstone.fidelite.models;

import java.util.Set;

public class ClientFMTS  {
	private String clientId;
	private String email;
	private String token;
	
	public ClientFMTS() {}

	public ClientFMTS(String clientId, String email, String token) {
		super();
		this.clientId = clientId;
		this.email = email;
		this.token = token;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}