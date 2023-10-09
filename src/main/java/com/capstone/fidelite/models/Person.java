package com.capstone.fidelite.models;

public class Person  {
	
	public Person() {
	}

	private String email;
	private String id;
	private String dateOfBirth;
	private String country;
	private String postalCode;
	
	public Person(String email, String id, String dateOfBirth, String country, String postalCode) {
		this.email = email;
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.postalCode = postalCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Person [email=" + email + ", id=" + id + ", dateOfBirth=" + dateOfBirth + ", country=" + country
				+ ", postalCode=" + postalCode + "]";
	}
	
}
