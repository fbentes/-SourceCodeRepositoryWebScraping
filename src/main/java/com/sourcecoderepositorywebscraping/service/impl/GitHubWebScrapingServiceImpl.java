package com.sourcecoderepositorywebscraping.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sourcecoderepositorywebscraping.core.HtmlStringParserContentStrategy;
import com.sourcecoderepositorywebscraping.core.WebScrapingServiceThreadExecutor;
import com.sourcecoderepositorywebscraping.exception.HtmlStringContentException;
import com.sourcecoderepositorywebscraping.exception.WaitTimeExccededToRequestException;
import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.service.SourceCodeRepositoryWebScrapingService;

/**
 * Reading each requisition concurrently, one by one, using cache, to avoid message: "HTTP 429: Too Many Requests".
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 16/10/2020
 *
 */
@Service
public class GitHubWebScrapingServiceImpl implements SourceCodeRepositoryWebScrapingService {
	
	private static Logger logger = LoggerFactory.getLogger(GitHubWebScrapingServiceImpl.class);
	
	@Autowired
	private HtmlStringParserContentStrategy htmlGitHubStringContent;
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void clearCache(String cacheKey) {
	    cacheManager.getCache("repository").evict(cacheKey);
	}
	
	@Override
	public void clearAllCache() {
	    cacheManager.getCache("repository").clear();
	}		
	
	@Cacheable(value = "repository", key = "#repositoryUrl")
    @Override
    public synchronized GroupDataByFileExtensionModel getRepositotyUrlContentModel(final String repositoryUrl) throws WaitTimeExccededToRequestException { 

		WebScrapingServiceThreadExecutor webScrapingServiceThreadExecutor = 
				new WebScrapingServiceThreadExecutor(repositoryUrl, htmlGitHubStringContent);
    	
    	webScrapingServiceThreadExecutor.start();

    	synchronized(webScrapingServiceThreadExecutor){
    		
       		try {
				
       			webScrapingServiceThreadExecutor.wait();
				
			} catch (InterruptedException e) {
    	    	
				logger.info(e.getMessage());
				
				throw new HtmlStringContentException(e.getMessage());
			}
    	}		
    	
		return webScrapingServiceThreadExecutor.getGroupDataByFileExtensionModel();
	}
}
