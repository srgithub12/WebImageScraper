# WebImageScraper

This project is developed using spring boot and elastic search to scrape images and stored them into elastic search.

Below are required to execute the project.

1) SerpAPI Key (signup in https://serpapi.com/ to get this)
2) Elasticsearch 8.1.2 (ES) (This need to be installed, Refer this link https://www.elastic.co/guide/en/elasticsearch/reference/8.1/install-elasticsearch.html)
3) JRE >= 8
4) Gradle(I have Used 7.4.2)

Execution:
1) Build and execute in eclipse/STS/IntelliJ
2) Build and deploy jar in any webcontainer(Ex:tomcat)

Invocation:
1) For Image scraping:
http://localhost:8080/scrape/images?searchParam=south indian food images

2) To check the number of records in the index:
http://localhost:8080/scrape/images/count
