package com.capstone.fidelite.models;

public enum Direction {
   BUY("B"),SELL("S");
   private String code; 
  private Direction(String code) {
	  this.code = code;
  }
  
  public static  Direction of(String code) {
	  for(Direction dir:values()) {
		  if(dir.code.equals(code)) {
			  return dir;
		  }
	  }
	  throw new IllegalArgumentException("Invalid Code Provided");
  }
  
  public String getCode() {
	  return this.code;
  }
}
