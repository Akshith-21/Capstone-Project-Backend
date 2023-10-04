package com.capstone.fidelite.models;

import java.util.Objects;

public class Portfolio {
	private String externalId;
	private String externalType;
    private String priceTimestamp;
    private String instrumentDescription;
    private double bidPrice;
    private double askPrice;
    private double currentHoldings;
    
	public Portfolio() {}
    
//    public Portfolio(String externalId, String externalType, String priceTimestamp, String instrumentDescription,
//			double bidPrice, double askPrice, double currentHoldings) {
//		this.externalId = externalId;
//		this.externalType = externalType;
//		this.priceTimestamp = priceTimestamp;
//		this.instrumentDescription = instrumentDescription;
//		this.bidPrice = bidPrice;
//		this.askPrice = askPrice;
//		this.currentHoldings = currentHoldings;
//	}

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

	@Override
	public int hashCode() {
		return Objects.hash(askPrice, bidPrice, externalId, externalType, instrumentDescription, priceTimestamp);
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
				&& Objects.equals(externalId, other.externalId) && Objects.equals(externalType, other.externalType)
				&& Objects.equals(instrumentDescription, other.instrumentDescription)
				&& Objects.equals(priceTimestamp, other.priceTimestamp);
	}

	@Override
	public String toString() {
		return "Portfolio [externalId=" + externalId + ", externalType=" + externalType + ", priceTimestamp="
				+ priceTimestamp + ", instrumentDescription=" + instrumentDescription + ", bidPrice=" + bidPrice
				+ ", askPrice=" + askPrice + ", currentHoldings=" + currentHoldings + "]";
	}
	
	
    
		
}

