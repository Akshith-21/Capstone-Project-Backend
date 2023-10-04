package com.capstone.fidelite.models;

public class Trade {
    public double cashValue;
    public double quantity;
    public Direction direction;
    public String instrumentId;
    public String clientId;
    public String tradeId;
    public double executionPrice;
    
	public Trade() {}
    
    public Trade(double cashValue, double quantity, Direction direction, String instrumentId, String clientId,
			String tradeId, double executionPrice) {
		this.cashValue = cashValue;
		this.quantity = quantity;
		this.direction = direction;
		this.instrumentId = instrumentId;
		this.clientId = clientId;
		this.tradeId = tradeId;
		this.executionPrice = executionPrice;
	}

	public Trade(double quantity, Direction direction, String instrumentId, String clientId, String tradeId,
			double executionPrice) {
		
		this.quantity = quantity;
		this.direction = direction;
		this.instrumentId = instrumentId;
		this.clientId = clientId;
		this.tradeId = tradeId;
		this.executionPrice = executionPrice;
	}

	public double getCashValue() {
		return cashValue;
	}

	public void setCashValue(double cashValue) {
		this.cashValue = cashValue;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public double getExecutionPrice() {
		return executionPrice;
	}

	public void setExecutionPrice(double executionPrice) {
		this.executionPrice = executionPrice;
	}

	

}
