package com.capstone.fidelite.models;

public class TradeFMTS {
	
	private String instrumentId;
	private double quantity;
	private double executionPrice;
	private String direction;
	private String clientId;
	private OrderFMTS order;
	private String tradeId;
	private double cashValue;
	
	public TradeFMTS() {}
	
	public TradeFMTS(String instrumentId, double quantity, double executionPrice, String direction, String clientId,
			OrderFMTS order, String tradeId, double cashValue) {
		super();
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.executionPrice = executionPrice;
		this.direction = direction;
		this.clientId = clientId;
		this.order = order;
		this.tradeId = tradeId;
		this.cashValue = cashValue;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getExecutionPrice() {
		return executionPrice;
	}

	public void setExecutionPrice(double executionPrice) {
		this.executionPrice = executionPrice;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public OrderFMTS getOrder() {
		return order;
	}

	public void setOrder(OrderFMTS order) {
		this.order = order;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public double getCashValue() {
		return cashValue;
	}

	public void setCashValue(double cashValue) {
		this.cashValue = cashValue;
	}

	@Override
	public String toString() {
		return "TradeFMTS [instrumentId=" + instrumentId + ", quantity=" + quantity + ", executionPrice="
				+ executionPrice + ", direction=" + direction + ", clientId=" + clientId + ", order=" + order
				+ ", tradeId=" + tradeId + ", cashValue=" + cashValue + "]";
	}
	
	
	
	
}
