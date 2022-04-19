package com.wis.es.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wis.es.service.WebImageScrapingService;

@RestController
public class WebImageScraperController {

	private static Logger logger = LogManager.getLogger(WebImageScraperController.class);

	@Autowired
	private WebImageScrapingService webImageScrapingService;

	@GetMapping(path = "scrape/images")
	public String scrapeImages(@RequestParam(required = true) String searchParam) {

		logger.info("WebImageScraperController::scrapeImages::searchParam: {}", searchParam);

		String response = "";
		try {
			webImageScrapingService.scrapeImages(searchParam);
			response = "Image scraping success";
		} catch (Exception e) {
			logger.error("WebImageScraperController::scrapeImages::Error: {}", e);

			response = "Some thing went wrong, Please try again later";
		}
		return response;
	}

}