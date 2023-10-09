package com.capstone.fidelite.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.fidelite.integration.ClientDaoImpl;
import com.capstone.fidelite.models.Preferences;

@SpringBootTest
class PreferencesServiceTest {

	@Mock
	private ClientDaoImpl mockDao;

	@InjectMocks
	private PreferencesService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSetPreferencesForExistingClient() {
		String email = "john@gmail.com";
		String clientId = "2";
		String investmentPurpose = "Education";
		String riskTolerance = "HIGH";
		String incomeCategory = "10000-20000";
		String lengthOfInvestment = "1-5 Years";
		int roboAdvisorCheck = 1;
		Preferences preferences = new Preferences(investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment,
				roboAdvisorCheck);

		when(mockDao.getIdFromEmail(email)).thenReturn(clientId);
		when(mockDao.checkIfRowExists(clientId)).thenReturn(1);
		when(mockDao.updatePreferences(clientId, preferences)).thenReturn(true);

		service.setPreferences(clientId, preferences);

		//verify(mockDao, times(1)).getIdFromEmail(email);
		verify(mockDao, times(1)).checkIfRowExists(clientId);
		verify(mockDao, times(1)).updatePreferences(clientId, preferences);
	}

	@Test
	void testSetPreferencesForNewClient() {
		String email = "john@gmail.com";
		String clientId = "2";
		String investmentPurpose = "Education";
		String riskTolerance = "LOW";
		String incomeCategory = "more than 200000";
		String lengthOfInvestment = "1-5 Years";
		int roboAdvisorCheck = 1;
		Preferences preferences = new Preferences(investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment,
				roboAdvisorCheck);

		when(mockDao.getIdFromEmail(email)).thenReturn(clientId);
		when(mockDao.checkIfRowExists(clientId)).thenReturn(0);
		when(mockDao.insertPreferences(clientId, preferences)).thenReturn(true);

		service.setPreferences(clientId, preferences);

		//verify(mockDao, times(1)).getIdFromEmail(email);
		verify(mockDao, times(1)).checkIfRowExists(clientId);
		verify(mockDao, times(1)).insertPreferences(clientId, preferences);
	}

	@Test
	void testSetPreferencesCatchesErrorThrownByMockDao() {
		when(mockDao.checkIfRowExists("")).thenThrow(new RuntimeException());
		
		assertThrows(Exception.class,()->{
			service.setPreferences("",new Preferences(null, null, null, null, 0));
		});
	}

}
