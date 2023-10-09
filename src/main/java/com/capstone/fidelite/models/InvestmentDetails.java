package com.capstone.fidelite.models;

import java.math.BigDecimal;
import java.util.Objects;

public class InvestmentDetails {
	double currentHoldings;
	BigDecimal totalInvestment;

	public InvestmentDetails(double currentHoldings, BigDecimal totalInvestment) {
		this.currentHoldings = currentHoldings;
		this.totalInvestment = totalInvestment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentHoldings, totalInvestment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvestmentDetails other = (InvestmentDetails) obj;
		return Double.doubleToLongBits(currentHoldings) == Double.doubleToLongBits(other.currentHoldings)
				&& Objects.equals(totalInvestment, other.totalInvestment);
	}

	public InvestmentDetails() {

	}

	public double getCurrentHoldings() {
		return currentHoldings;
	}

	public void setCurrentHoldings(double currentHoldings) {
		this.currentHoldings = currentHoldings;
	}

	public BigDecimal getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(BigDecimal totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

}
