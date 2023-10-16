package com.capstone.fidelite.restcontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.capstone.fidelite.models.Preferences;
import com.capstone.fidelite.services.PreferencesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMybatis
@WebMvcTest(controllers = PreferencesController.class)
class PreferencesControllerWebLayerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private PreferencesService mockService;
	
	@MockBean
	private PreferencesController preferencesController;
	
	@Test
	void testSetPreferences() throws Exception {
		String clientId="Client1";
		String investmentPurpose = "Education";
		String riskTolerance = "LOW";
		String incomeCategory = "10000-200000";
		String lengthOfInvestment = "More than 5 years";
		int roboAdvisorCheck = 1;
		Preferences preferences = new Preferences(investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment,
				roboAdvisorCheck);
		ObjectMapper mapper = new ObjectMapper();
		String preferencesString = mapper.writeValueAsString(preferences);
		mockMvc.perform(post("/client/preferences/setPreferences?clientId="+clientId).contentType(MediaType.APPLICATION_JSON).content(preferencesString)).andDo(print()).andExpect(status().is2xxSuccessful());
		
	}

}
