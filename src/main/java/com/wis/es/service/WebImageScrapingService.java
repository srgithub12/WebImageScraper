package com.wis.es.service;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wis.es.domain.WebImages;
import com.wis.es.response.ImageScrapingResponse;
import com.wis.es.response.ImagesResult;

@Service
public class WebImageScrapingService {

	private static Logger logger = LogManager.getLogger(WebImageScrapingService.class);

	@Autowired
	private RestHighLevelClient client;

	private static final String API_KEY = "1aeadc13fd76503504574dbdec0c24a6a10cd3117adad2c204463f4c7e358f83";

	private static final String URL1 = "https://serpapi.com/search.json?q=";

	private static final String URL2 = "&tbm=isch&ijn=";

	private static final String URL3 = "&api_key=";

	public void scrapeImages(String searchParam) throws Exception {

		logger.info("WebImageScrapingService::scrapeImages::Start");

		BulkRequest bulkRequest = getBulkRequest(searchParam);

		RequestOptions options = RequestOptions.DEFAULT;
		client.bulkAsync(bulkRequest, options, new ActionListener<BulkResponse>() {
			@Override
			public void onResponse(BulkResponse response) {
				logger.info("Items: {} , Time took in millis: {}", response.getItems(),
						response.getIngestTookInMillis());
			}

			@Override
			public void onFailure(Exception e) {
				logger.error("Error: {}", e);
			}
		});

		logger.info("WebImageScrapingService::scrapeImages::End");

	}

	private BulkRequest getBulkRequest(String searchParam) {

		BulkRequest bulkRequest = new BulkRequest();

		RestTemplate restTemplate = new RestTemplate();
		// String searchParam = "south indian food images";

		for (int i = 0;; i++) {

			String url = URL1 + searchParam + URL2 + i + URL3 + API_KEY;

			ImageScrapingResponse response = restTemplate.getForEntity(url, ImageScrapingResponse.class).getBody();

			if (!CollectionUtils.isEmpty(response.getImages_results())) {

				for (ImagesResult imagesResult : response.getImages_results()) {
					IndexRequest indexRequest = new IndexRequest("images");
					WebImages images = new WebImages();
					images.setOriginal(imagesResult.getOriginal());
					images.setUrl(imagesResult.getThumbnail());
					Map<String, Object> imagesMap = new ObjectMapper().convertValue(images, Map.class);
					indexRequest.source(imagesMap);
					bulkRequest.add(indexRequest);
				}

			} else {
				break;
			}

		}
		return bulkRequest;
	}
}
