package com.sourcecoderepositorywebscraping.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sourcecoderepositorywebscraping.model.DataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.service.SourceCodeRepositoryWebScrapingService;
import com.sourcecoderepositorywebscraping.util.ConstantsMessages;
import com.sourcecoderepositorywebscraping.util.FileSizeBytesConverter;
import com.sourcecoderepositorywebscraping.util.exception.HtmlStringContentException;
import com.sourcecoderepositorywebscraping.util.exception.WaitTimeToRequestException;
import com.sourcecoderepositorywebscraping.util.scrapring.FileExtensionByLinkCatcher;
import com.sourcecoderepositorywebscraping.util.scrapring.FileLinesCatcher;
import com.sourcecoderepositorywebscraping.util.scrapring.HtmlLineContent;
import com.sourcecoderepositorywebscraping.util.scrapring.HtmlStringContent;

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
	private HtmlStringContent htmlGitHubStringContent;
	
	@Autowired
	private CacheManager cacheManager;

	@Override
	public void evictSingleCacheValue(String cacheKey) {
	    cacheManager.getCache("repository").evict(cacheKey);
	}
	 
	@Override
	public void evictAllCacheValues() {
	    cacheManager.getCache("repository").clear();
	}	
	
	@Cacheable(value = "repository", key = "#repositoryUrl")
    @Override
    public synchronized GroupDataByFileExtensionModel getRepositotyUrlContentModel(final String repositoryUrl) throws WaitTimeToRequestException { 

		logger.info("repositoryUrl = " + repositoryUrl);
		
		GroupDataByFileExtensionModel groupDataByFileExtensionModel = new GroupDataByFileExtensionModel();

		try {
		
			groupDataByFileExtensionModel = prepareRepositotyUrlContentModel(repositoryUrl, groupDataByFileExtensionModel);
		
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			groupDataByFileExtensionModel.setHttpError(String.format(ConstantsMessages.REPOSITORY_URL_CONTENT_MODEL_ERROR,e.getMessage()));
		} 
		
    	logger.info(groupDataByFileExtensionModel.toJson());
    	
		return groupDataByFileExtensionModel;
	}

	private GroupDataByFileExtensionModel prepareRepositotyUrlContentModel(
			final String repositoryUrl, 
			GroupDataByFileExtensionModel groupDataByFileExtensionModel) throws IOException {
		
		try {
						
			List<HtmlLineContent> lineHtmlContentList = htmlGitHubStringContent.getContentList(repositoryUrl);
			
			int fileNumberLines;
			float fileNumberSize;
			
			String fileExtension = FileExtensionByLinkCatcher.getExtension(repositoryUrl);
			
			for (HtmlLineContent htmlLineContent : lineHtmlContentList) {
				
				if(htmlLineContent.isDirectory()) {
					
					String href = "href=";
					
					int posHref = htmlLineContent.getContent().indexOf(href);
					int endPosHref = htmlLineContent.getContent().indexOf("</a>");
					
					String macrolink = htmlLineContent.getContent().substring(posHref + 7, endPosHref - 1);
					
					String linkNotTreatable = "";
					
					for(String s :  macrolink.split("/")) {
						linkNotTreatable += "/" + s;
					}
					
					String linkTreatable = linkNotTreatable.substring(1, linkNotTreatable.indexOf("\">"));			
					
					groupDataByFileExtensionModel = prepareRepositotyUrlContentModel(
							htmlGitHubStringContent.getSourceCodeRepositoryUrl() + linkTreatable,
							groupDataByFileExtensionModel);
				}
				else {
					
						if(htmlLineContent.isHasTagSearchLines()) {
							
							fileNumberLines = FileLinesCatcher.getNumberLines(htmlLineContent.getContent());
							
							DataByFileExtensionModel dataByFileExtensionModel= new DataByFileExtensionModel(fileExtension);
							dataByFileExtensionModel.addFileNumberOfLines(fileNumberLines);
							
							groupDataByFileExtensionModel.addFileNumberOfLines(dataByFileExtensionModel);
						}
						
						if(htmlLineContent.isHasTagSearchBytes()) {
							
							fileNumberSize = FileSizeBytesConverter.getValueInBytes(htmlLineContent.getContent());
							
							DataByFileExtensionModel dataByFileExtensionModel = new DataByFileExtensionModel(fileExtension);
							dataByFileExtensionModel.addFileNumberOfBytes(fileNumberSize);
							
							groupDataByFileExtensionModel.addFileNumberOfBytes(dataByFileExtensionModel);
						}
				}
			}
		}
		catch (HtmlStringContentException e) {
			
			groupDataByFileExtensionModel.setHttpError(e.getMessage());
			
			logger.error(e.getMessage());
		}
		finally {
			
			groupDataByFileExtensionModel.setRepositoryUrl(repositoryUrl);
			
			groupDataByFileExtensionModel.finish();
		}
		
		return groupDataByFileExtensionModel;
	}	
}
