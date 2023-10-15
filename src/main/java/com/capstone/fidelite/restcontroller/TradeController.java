package com.capstone.fidelite.restcontroller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.fidelite.integration.DatabaseException;
import com.capstone.fidelite.integration.TradeDaoImpl;
import com.capstone.fidelite.models.BalanceDataTransferObject;
import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.Order;
import com.capstone.fidelite.models.OrderFMTS;
import com.capstone.fidelite.models.Portfolio;
import com.capstone.fidelite.models.Price;
import com.capstone.fidelite.models.Trade;
import com.capstone.fidelite.services.ClientService;
import com.capstone.fidelite.services.InsufficientBalanceException;
import com.capstone.fidelite.services.PortfolioService;

@RestController
@RequestMapping("/trade")
@CrossOrigin("http://localhost:4200")
public class TradeController {
	@Autowired
	Logger logger;

	@Autowired
	PortfolioService portfolioService;

	@GetMapping("/portfolio/{clientId}")

	ResponseEntity<?> getPortfolioDetails(@PathVariable String clientId) {

		ResponseEntity<?> response = null;

		try {

			List<Portfolio> updatedPortfolioList = portfolioService.getUpdatedPortfolios(clientId);

			if (updatedPortfolioList == null) {
				System.out.println("It is null");
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("There is No portfolio please perform a BUY trade");

			}

			response = ResponseEntity.status(HttpStatus.OK).body(updatedPortfolioList);

		}

		catch (ResponseStatusException e) {

			if (e.getStatus().equals(HttpStatus.NOT_ACCEPTABLE))

				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

			if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			}

			if (e.getStatus().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

			}

		}

		catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data Access Exception");

		}

		return response;

	}

	@PostMapping("/execute")
	ResponseEntity<?> executeTrade(@RequestBody OrderFMTS order) {
		Trade trade = null;
		System.out.println("Inside controller: " + order);
		try {
			trade = portfolioService.executeTrade(order);
			if (trade == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
						.body("Target Price And Execution Price are not in 1% range");
			}
			return ResponseEntity.status(HttpStatus.OK).body("Order Successful");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Does Not Have Enough Holdings");
		}

		catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body("Does Not Have the required stock in portfolio to sell");
		}

		catch (InsufficientBalanceException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body("Insufficient Balance Cannot Execute the Trade");
		} 
		catch (ResponseStatusException e) {

			if (e.getStatus().equals(HttpStatus.NOT_ACCEPTABLE))

				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

			else if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			}
			else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server is Down!!!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server is Down!!!!");
		}
	}

	@GetMapping("fetchtrade/{clientId}")
	ResponseEntity<?> getAllTrades(@PathVariable String clientId) {
		List<Trade> tradeList = null;
		try {
			tradeList = portfolioService.getTradeHistory(clientId);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Trades Yet Please Perform a Trade");
		} catch (DataAccessException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database Is Down");
		}
		return ResponseEntity.status(HttpStatus.OK).body(tradeList);
	}

	@GetMapping("/prices")
	ResponseEntity<?> getAllPriceFromFmts() {
		List<Price> priceList = null;

		try {
			priceList = portfolioService.getAllPrices();
		} catch (ResponseStatusException e) {

			e.printStackTrace();

			if (e.getStatus().equals(HttpStatus.NOT_ACCEPTABLE))

				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

			if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			}

		}

		catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data Access Exception");

		}

		return ResponseEntity.status(HttpStatus.OK).body(priceList);
	}

	@GetMapping("/balance/{clientId}")
	ResponseEntity<?> getAllPrice(@PathVariable String clientId) {
		BigDecimal balance = null;
		try {
			balance = portfolioService.getBalance(clientId);
		} catch (DatabaseException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem while accessing the data");
		}
		return ResponseEntity.status(HttpStatus.OK).body(new BalanceDataTransferObject(balance));

	}

	@GetMapping("/prices/filter")
	public ResponseEntity<?> getPricesFilterByCategoryType(
			@RequestParam(name = "category", required = true) String category) {

		List<Price> priceList = null;

		try {
			priceList = portfolioService.getAllPricesByFilter(category);
			if (priceList == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Stocks Availaible with the given filter");
			}
		} catch (ResponseStatusException e) {

			e.printStackTrace();

			if (e.getStatus().equals(HttpStatus.NOT_ACCEPTABLE))

				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

			if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			}

		}
		return ResponseEntity.status(HttpStatus.OK).body(priceList);
	}
}
