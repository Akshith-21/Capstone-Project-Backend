package com.capstone.fidelite.models;

public class OrderFMTS {
	private String instrumentId;
	private double quantity;
	private double targetPrice;
	private String direction;
	private String clientId;
	private String orderId;
	private String token;
	
	public OrderFMTS(String instrumentId, double quantity, double targetPrice, String direction, String clientId,
			String orderId, String token) {
		super();
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.targetPrice = targetPrice;
		this.direction = direction;
		this.clientId = clientId;
		this.orderId = orderId;
		this.token = token;
	}
	
	public OrderFMTS(Order order) {
		this.instrumentId = order.getInstrumentId();
		this.quantity = order.getQuantity();
		this.targetPrice = order.getTargetPrice();
		this.direction = order.getDirection().getCode();
		this.clientId = order.getClientId();
		this.orderId = order.getOrderId();
		this.token = order.getToken();
	}

	public OrderFMTS() {}

 

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

 

	public double getTargetPrice() {
		return targetPrice;
	}

 

	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
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

 

	public String getOrderId() {
		return orderId;
	}

 

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "OrderFMTS [instrumentId=" + instrumentId + ", quantity=" + quantity + ", targetPrice=" + targetPrice
				+ ", direction=" + direction + ", clientId=" + clientId + ", orderId=" + orderId + ", token=" + token
				+ "]";
	}
	
}
