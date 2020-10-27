package com.sourcecoderepositorywebscraping.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcecoderepositorywebscraping.core.GroupDataByFileExtensionModelCache;
import com.sourcecoderepositorywebscraping.core.HtmlStringParserContentStrategy;
import com.sourcecoderepositorywebscraping.core.WebScrapingThreadExecutor;
import com.sourcecoderepositorywebscraping.core.WebScrapingThreadExecutorFactory;
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
public class SourceCodeRepositoryWebScrapingServiceImpl implements SourceCodeRepositoryWebScrapingService {
	
	private static Logger logger = LoggerFactory.getLogger(SourceCodeRepositoryWebScrapingServiceImpl.class);

	@Autowired
	private HtmlStringParserContentStrategy htmlGitHubStringContent;
	
	@Autowired
	private GroupDataByFileExtensionModelCache groupDataByFileExtensionModelCache;	
	
	@Autowired
	private WebScrapingThreadExecutorFactory webScrapingThreadExecutorFactory;
	
	@Override
	public void clearCache(String repositoryUrl) {
		groupDataByFileExtensionModelCache.clearCache(repositoryUrl);
	}
	
	@Override
	public void clearAllCache() {
		groupDataByFileExtensionModelCache.clearAllCache();
	}		
	
    @Override
    public GroupDataByFileExtensionModel getRepositotyUrlContentModel(final String repositoryUrl) 
    		throws WaitTimeExccededToRequestException { 

		synchronized(this) {
    		
	    	GroupDataByFileExtensionModel groupDataByFileExtensionModel = groupDataByFileExtensionModelCache.getItem(repositoryUrl);
	    	
	    	if(groupDataByFileExtensionModel != null) {
	    		return groupDataByFileExtensionModel;
	    	}

    		WebScrapingThreadExecutor webScrapingThreadExecutor = 
    				webScrapingThreadExecutorFactory.createNewInstanceOrReturnExistent(repositoryUrl);
    		  	
    		webScrapingThreadExecutor.setHtmlGitHubStringContent(htmlGitHubStringContent);
    		webScrapingThreadExecutor.setGroupDataByFileExtensionModelCache(groupDataByFileExtensionModelCache);
    		
    		 webScrapingThreadExecutor.start();

    		synchronized(webScrapingThreadExecutor){
        		
           		try {
           			
    				while(groupDataByFileExtensionModelCache.getItem(repositoryUrl) == null) {
               			
    					webScrapingThreadExecutor.wait();
    				}
    				
    			} catch (InterruptedException e) {
        	    	
    				logger.info(e.getMessage());
    				
    				throw new HtmlStringContentException(e.getMessage());
    			}
        	}

    		return webScrapingThreadExecutor.getGroupDataByFileExtensionModel();
    	}
	}
}
