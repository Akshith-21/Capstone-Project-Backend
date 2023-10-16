package com.capstone.fidelite.models;

public class Preferences {
	
	private String clientId;
	private String investmentPurpose;
	private String riskTolerance;
	private String incomeCategory;
	private String lengthOfInvestment;
	private int roboAdvisorCheck;
	
	public Preferences() {};
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Preferences(String clientId, String investmentPurpose, String riskTolerance, String incomeCategory,
			String lengthOfInvestment) {
		super();
		this.clientId = clientId;
		this.investmentPurpose = investmentPurpose;
		this.riskTolerance = riskTolerance;
		this.incomeCategory = incomeCategory;
		this.lengthOfInvestment = lengthOfInvestment;
	}
	
	public Preferences(String investmentPurpose, String riskTolerance, String incomeCategory,
			String lengthOfInvestment, int roboAdvisorCheck) {
		if((investmentPurpose == null)  || (investmentPurpose.isEmpty())){
			throw new IllegalArgumentException("investment purpose cannot be empty");
		}
		this.investmentPurpose = investmentPurpose;
		if((riskTolerance == null)  || (riskTolerance.isEmpty())) {
			throw new IllegalArgumentException("risk tolerance cannot be empty");
		}
		this.riskTolerance = riskTolerance;
		if((incomeCategory == null) || (incomeCategory.isEmpty())) {
			throw new IllegalArgumentException("Income Category cannot be empty");
		}
		this.incomeCategory = incomeCategory;
		
		if((lengthOfInvestment == null) || (lengthOfInvestment.isEmpty())) {
			throw new IllegalArgumentException("Length Of Investment cannot be empty");
		}
		this.lengthOfInvestment = lengthOfInvestment;
		
//		if(roboAdvisorCheck == ) {
//			throw new IllegalArgumentException("Robo Advisor Check cannot be null");
//		}
		this.roboAdvisorCheck = roboAdvisorCheck;
	}

	public String getInvestmentPurpose() {
		return investmentPurpose;
	}

	public void setInvestmentPurpose(String investmentPurpose) {
		this.investmentPurpose = investmentPurpose;
	}

	public String getRiskTolerance() {
		return riskTolerance;
	}

	public void setRiskTolerance(String riskTolerance) {
		this.riskTolerance = riskTolerance;
	}

	public String getIncomeCategory() {
		return incomeCategory;
	}

	public void setIncomeCategory(String incomeCategory) {
		this.incomeCategory = incomeCategory;
	}

	public String getLengthOfInvestment() {
		return lengthOfInvestment;
	}

	public void setLengthOfInvestment(String lengthOfInvestment) {
		this.lengthOfInvestment = lengthOfInvestment;
	}

	public int getRoboAdvisorCheck() {
		return roboAdvisorCheck;
	}

	public void setRoboAdvisorCheck(int roboAdvisorCheck) {
		this.roboAdvisorCheck = roboAdvisorCheck;
	}
	
	
}
