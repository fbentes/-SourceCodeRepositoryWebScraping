package com.sourcecoderepositorywebscraping.service;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.util.exception.WaitTimeToRequestException;

public interface SourceCodeRepositoryWebScrapingService {

	void evictSingleCacheValue(String cacheKey);
	
	void evictAllCacheValues();
	
	GroupDataByFileExtensionModel getRepositotyUrlContentModel(final String repositoryUrl)
	  throws WaitTimeToRequestException;
}
