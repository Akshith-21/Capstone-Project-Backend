package com.capstone.fidelite.models;

import java.util.Objects;

public class PortfolioDetails {
	String instrumentId;
	double currentHoldings;

	public PortfolioDetails() {

	}

	public PortfolioDetails(String instrumentId, double currentHoldings) {
		this.instrumentId = instrumentId;
		this.currentHoldings = currentHoldings;
	}

	@Override
	public int hashCode() {
		return Objects.hash(instrumentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortfolioDetails other = (PortfolioDetails) obj;
		return Objects.equals(instrumentId, other.instrumentId);
	}

	@Override
	public String toString() {
		return "PortfolioDetails [instrumentId=" + instrumentId + ", currentHoldings=" + currentHoldings + "]";
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public double getCurrentHoldings() {
		return currentHoldings;
	}

	public void setCurrentHoldings(double currentHoldings) {
		this.currentHoldings = currentHoldings;
	}
}