package com.capstone.fidelite.models;

public class Price {
	private double askPrice;
    private double bidPrice;
    private String priceTimestamp;
    private Instrument instrument;
    
	public Price(double askPrice, double bidPrice, String priceTimestamp, Instrument instrument) {
		this.askPrice = askPrice;
		this.bidPrice = bidPrice;
		this.priceTimestamp = priceTimestamp;
		this.instrument = instrument;
	}

	public double getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String getPriceTimestamp() {
		return priceTimestamp;
	}

	public void setPriceTimestamp(String priceTimestamp) {
		this.priceTimestamp = priceTimestamp;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
    
}
