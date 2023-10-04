package com.capstone.fidelite.restcontroller;

public class ClientDataTransferObject {
	
	String clientId;
	String token;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ClientDataTransferObject() {
	
	}
	public ClientDataTransferObject(String clientId, String token) {
		
		this.clientId = clientId;
		this.token = token;
	}
	@Override
	public String toString() {
		return "ClientDataTransferObject [clientId=" + clientId + ", token=" + token + "]";
	}
	

}
