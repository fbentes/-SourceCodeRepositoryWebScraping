package com.sourcecoderepositorywebscraping.service;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;

public interface SourceCodeRepositoryWebScrapingService {

	void evictSingleCacheValue(String cacheKey);
	
	void evictAllCacheValues();
	
	GroupDataByFileExtensionModel getRepositotyUrlContentModel(final String repositoryUrl);
}
