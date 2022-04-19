package com.wis.es.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wis.es.domain.WebImages;

public interface WebImagesRepo extends ElasticsearchRepository<WebImages, String> {

}
