package com.capstone.fidelite.models;

import java.math.BigDecimal;

public class BalanceDataTransferObject {
   BigDecimal balance;

  public BalanceDataTransferObject() {
	
  }

  public BalanceDataTransferObject(BigDecimal balance) {
	this.balance = balance;
  }

 public BigDecimal getBalance() {
	return balance;
 }

 public void setBalance(BigDecimal balance) {
	this.balance = balance;
 }
  
  
  
  
   
}
