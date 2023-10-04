package com.capstone.fidelite.models;

 

public class Order {
	private String instrumentId;
	private double quantity;
	private double targetPrice;
	private Direction direction;
	private String clientId;
	private String orderId;

 

	public Order(String instrumentId, double quantity, double targetPrice, Direction direction, String clientId,
			String orderId) {
		super();
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.targetPrice = targetPrice;
		this.direction = direction;
		this.clientId = clientId;
		this.orderId = orderId;
	}

 

	public Order() {
		super();
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

 

	public double getTargetPrice() {
		return targetPrice;
	}

 

	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
	}

 

	public Direction getDirection() {
		return direction;
	}

 

	public void setDirection(Direction direction) {
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
}