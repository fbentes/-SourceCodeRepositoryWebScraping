package com.sourcecoderepositorywebscraping.service;

import com.sourcecoderepositorywebscraping.exception.WaitTimeExccededToRequestException;
import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;

public interface SourceCodeRepositoryWebScrapingService {

	void clearCache(String cacheKey);
	
	void clearAllCache();
	
	GroupDataByFileExtensionModel getRepositotyUrlContentModel(final String repositoryUrl)
	  throws WaitTimeExccededToRequestException;
}
