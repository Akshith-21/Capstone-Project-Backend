package com.capstone.fidelite.models;

import java.io.Serializable;
import java.util.Set;

public class Client implements Serializable {
	
	public Client() {
	}

	private Person person;
	private Set<ClientIdentification> clientIdentificationSet;
	
	@Override
	public String toString() {
		return "Client [person=" + person + ", clientIdentificationSet=" + clientIdentificationSet + "]";
	}

	public Client(Person person, Set<ClientIdentification> clientIdentificationSet) {
		this.person = person;
		this.clientIdentificationSet = clientIdentificationSet;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Set<ClientIdentification> getClientIdentificationSet() {
		return clientIdentificationSet;
	}

	public void setClientIdentificationSet(Set<ClientIdentification> clientIdentificationSet) {
		this.clientIdentificationSet = clientIdentificationSet;
	}
	
}
