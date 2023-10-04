package com.capstone.fidelite.models;

import java.util.Set;

public class ClientFMTS  {
	private String clientId;
	private String email;
	private String dateOfBirth;
	private String country;
	private String postalCode;
	private Set<ClientIdentification> identification;
	private String token;
	
	public ClientFMTS() {}
	
	public ClientFMTS(Client client) {
		this.clientId = client.getPerson().getId();
		this.email = client.getPerson().getEmail();
		this.dateOfBirth = client.getPerson().getDateOfBirth();
		this.country = client.getPerson().getCountry();
		this.postalCode = client.getPerson().getPostalCode();
		this.identification = client.getClientIdentificationSet();
		this.token = "";
	}
	
	public ClientFMTS(String clientId, String email, String dateOfBirth, String country, String postalCode,
			Set<ClientIdentification> identification) {
		super();
		this.clientId = clientId;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.postalCode = postalCode;
		this.identification = identification;
		this.token = "";
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Set<ClientIdentification> getIdentification() {
		return identification;
	}

	public void setIdentification(Set<ClientIdentification> identification) {
		this.identification = identification;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "ClientFMTS [clientId=" + clientId + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", country="
				+ country + ", postalCode=" + postalCode + ", identification=" + identification + ", token=" + token
				+ "]";
	}
	
	
	
}
