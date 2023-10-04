package com.capstone.fidelite.services;

import java.util.ArrayList;
import java.util.List;

import com.capstone.fidelite.models.Instrument;
import com.capstone.fidelite.models.Price;

public class FMTS {
	private static List<Price> mockInstruments;

	static {
		mockInstruments  = new ArrayList<>();
		mockInstruments.add(new Price(104.75, 104.25, "21-AUG-19 10.00.01.042000000 AM GMT", new Instrument("N123456", "CUSIP",  "46625H100", "STOCK",  "JPMorgan Chase & Co. Capital Stock", 1000, 1)));
	}

	public static List<Price> getMockInstruments() {
		return mockInstruments;
	}

	public void setMockInstruments(List<Price> mockInstruments) {
		this.mockInstruments = mockInstruments;
	}
	
	
	
}
