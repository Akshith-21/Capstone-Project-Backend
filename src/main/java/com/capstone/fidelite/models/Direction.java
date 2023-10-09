package com.capstone.fidelite.models;

public enum Direction {
   BUY("B","BUY"),SELL("S","SELL");
   private String code1;
   private String code2; 
  private Direction(String code1,String code2) {
	  this.code1 = code1;
	  this.code2 = code2;
  }
  
  public static  Direction of(String code) {
	  for(Direction dir:values()) {
		  if(dir.code1.equals(code) || dir.code2.equals(code)) {
			  return dir;
		  }
	  }
	  throw new IllegalArgumentException("Invalid Code Provided");
  }
  
  public String getCode1() {
	  return this.code1;
  }
  public String getCode2() {
	  return this.code2;
  }
}
