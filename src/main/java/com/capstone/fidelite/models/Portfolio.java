package com.capstone.fidelite.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Portfolio {
	private String externalId;
	private String externalType;
	private String categoryType;
	private String priceTimestamp;
	private String instrumentDescription;
	private double bidPrice;
	private double askPrice;
	private double currentHoldings;
	private String instrumentId;
	private BigDecimal totalInvestment;
	
	public Portfolio() {
	}

	public Portfolio(String externalId, String externalType, String categoryType, String priceTimestamp,
			String instrumentDescription, double bidPrice, double askPrice, double currentHoldings, String instrumentId,
			BigDecimal totalInvestment) {
		this.externalId = externalId;
		this.externalType = externalType;
		this.categoryType = categoryType;
		this.priceTimestamp = priceTimestamp;
		this.instrumentDescription = instrumentDescription;
		this.bidPrice = bidPrice;
		this.askPrice = askPrice;
		this.currentHoldings = currentHoldings;
		this.instrumentId = instrumentId;
		this.totalInvestment = totalInvestment;
	}
	
	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalType() {
		return externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
	}

	public String getPriceTimestamp() {
		return priceTimestamp;
	}

	public void setPriceTimestamp(String priceTimestamp) {
		this.priceTimestamp = priceTimestamp;
	}

	public String getInstrumentDescription() {
		return instrumentDescription;
	}

	public void setInstrumentDescription(String instrumentDescription) {
		this.instrumentDescription = instrumentDescription;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public double getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}

	public double getCurrentHoldings() {
		return currentHoldings;
	}

	public void setCurrentHoldings(double currentHoldings) {
		this.currentHoldings = currentHoldings;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public BigDecimal getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(BigDecimal totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

	@Override
	public String toString() {
		return "Portfolio [externalId=" + externalId + ", externalType=" + externalType + ", priceTimestamp="
				+ priceTimestamp + ", instrumentDescription=" + instrumentDescription + ", bidPrice=" + bidPrice
				+ ", askPrice=" + askPrice + ", currentHoldings=" + currentHoldings + ", instrumentId=" + instrumentId
				+ ", totalInvestment=" + totalInvestment + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(askPrice, bidPrice, currentHoldings, externalId, externalType, instrumentDescription,
				instrumentId, priceTimestamp, totalInvestment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Portfolio other = (Portfolio) obj;
		return Double.doubleToLongBits(askPrice) == Double.doubleToLongBits(other.askPrice)
				&& Double.doubleToLongBits(bidPrice) == Double.doubleToLongBits(other.bidPrice)
				&& Double.doubleToLongBits(currentHoldings) == Double.doubleToLongBits(other.currentHoldings)
				&& Objects.equals(externalId, other.externalId) && Objects.equals(externalType, other.externalType)
				&& Objects.equals(instrumentDescription, other.instrumentDescription)
				&& Objects.equals(instrumentId, other.instrumentId)
				&& Objects.equals(priceTimestamp, other.priceTimestamp)
				&& Objects.equals(totalInvestment, other.totalInvestment);
	}




	
}