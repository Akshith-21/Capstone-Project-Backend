package com.capstone.fidelite.models;

import java.util.Objects;

public class PortfolioDetails {
	String instrumentId;
	InvestmentDetails investmentDetails;

	public PortfolioDetails() {

	}

	public PortfolioDetails(String instrumentId, InvestmentDetails investmentDetails) {
		this.instrumentId = instrumentId;
		this.investmentDetails = investmentDetails;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public InvestmentDetails getInvestmentDetails() {
		return investmentDetails;
	}

	public void setInvestmentDetails(InvestmentDetails investmentDetails) {
		this.investmentDetails = investmentDetails;
	}

	@Override
	public String toString() {
		return "PortfolioDetails [instrumentId=" + instrumentId + ", investmentDetails=" + investmentDetails + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(instrumentId, investmentDetails);
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
		return Objects.equals(instrumentId, other.instrumentId)
				&& Objects.equals(investmentDetails, other.investmentDetails);
	}

	
}