package com.sourcecoderepositorywebscraping.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
import com.sourcecoderepositorywebscraping.util.FileExtensionByLinkCatcher;
import com.sourcecoderepositorywebscraping.util.FileLinesCatcher;
import com.sourcecoderepositorywebscraping.util.FileSizeBytesConverter;
import com.sourcecoderepositorywebscraping.util.RepositoryHtmlTag;

/**
 * Responsável pela leitura de uma URL
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 16/10/2020
 *
 */
@Service
public class GitHubWebScrapingServiceImpl implements SourceCodeRepositoryWebScrapingService {
	
	Logger logger = LoggerFactory.getLogger(GitHubWebScrapingServiceImpl.class);
	
	@Autowired
	private RepositoryHtmlTag repositoryHtmlTag;
	
	@Autowired
	CacheManager cacheManager;

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
    public GroupDataByFileExtensionModel getRepositotyUrlContentModel(final String repositoryUrl) { 

		GroupDataByFileExtensionModel groupDataByFileExtensionModel = new GroupDataByFileExtensionModel();

		try {
		
			groupDataByFileExtensionModel = prepareRepositotyUrlContentModel(repositoryUrl, groupDataByFileExtensionModel);
		
		} catch (IOException e) {
			
			groupDataByFileExtensionModel.setHttpError(String.format(ConstantsMessages.REPOSITORY_URL_CONTENT_MODEL_ERROR,e.getMessage()));
		}
		
		return groupDataByFileExtensionModel;
	}

	private GroupDataByFileExtensionModel prepareRepositotyUrlContentModel(
			final String repositoryUrl, 
			GroupDataByFileExtensionModel groupDataByFileExtensionModel) throws IOException {
		
		URL url = new URL(repositoryUrl);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine;
		
		boolean catchLines = false;
		boolean catchBytes = false;
		
		int fileNumberLines;
		float fileNumberSize;
		
		String fileExtension = FileExtensionByLinkCatcher.getExtension(repositoryUrl);
		
		while ((inputLine = bufferedReader.readLine()) != null) {
			
			int pos = inputLine.indexOf(repositoryHtmlTag.getTagSearchLinkSubdirectory());
			
			if(pos > -1) {
				
				String href = "href=";
				
				int posHref = inputLine.indexOf(href);
				int endPosHref = inputLine.indexOf("</a>");
				
				String macrolink = inputLine.substring(posHref + 7, endPosHref - 1);
				
				String linkNotTreatable = "";
				
				for(String s :  macrolink.split("/")) {
					linkNotTreatable += "/" + s;
				}
				
				String linkTreatable = linkNotTreatable.substring(1, linkNotTreatable.indexOf("\">"));			
				
				logger.info(repositoryHtmlTag.getSourceCodeRepositoryUrl() + linkTreatable);
				
				groupDataByFileExtensionModel = prepareRepositotyUrlContentModel(
						repositoryHtmlTag.getSourceCodeRepositoryUrl() + linkTreatable,
						groupDataByFileExtensionModel);
			}
			else {
				
				pos = inputLine.indexOf(repositoryHtmlTag.getTagSearchLines());
				
				if(catchLines && !inputLine.trim().equals("")) {
					
					logger.info(inputLine);
					
					if(inputLine.contains("lines")) {
						
						fileNumberLines = FileLinesCatcher.getNumberLines(inputLine);
						
						DataByFileExtensionModel dataByFileExtensionModel= new DataByFileExtensionModel(fileExtension);
						dataByFileExtensionModel.addFileNumberOfLines(fileNumberLines);
						
						groupDataByFileExtensionModel.addFileNumberOfLines(dataByFileExtensionModel);
					}
					else {
						
						fileNumberSize = FileSizeBytesConverter.getValueInBytes(inputLine);
						
						DataByFileExtensionModel dataByFileExtensionModel = new DataByFileExtensionModel(fileExtension);
						dataByFileExtensionModel.addFileNumberOfBytes(fileNumberSize);
						
						groupDataByFileExtensionModel.addFileNumberOfBytes(dataByFileExtensionModel);
						
						catchBytes = false;
					}
					
					catchLines = false;
				}

				if(pos > -1) {
					catchLines = true;
				}
				
				pos = inputLine.indexOf(repositoryHtmlTag.getTagSearchBytes());
				
				if(catchBytes && !inputLine.trim().equals("")) {
					
					logger.info(inputLine);

					fileNumberSize = FileSizeBytesConverter.getValueInBytes(inputLine);
					
					DataByFileExtensionModel dataByFileExtensionModel = new DataByFileExtensionModel(fileExtension);
					dataByFileExtensionModel.addFileNumberOfBytes(fileNumberSize);
					
					groupDataByFileExtensionModel.addFileNumberOfBytes(dataByFileExtensionModel);
					
					catchBytes = false;
				}

				if(pos > -1) {
					catchBytes = true;
				}
			}
		}
		
		bufferedReader.close();
		
		groupDataByFileExtensionModel.setRepositoryUrl(repositoryUrl);
		
		return groupDataByFileExtensionModel;
	}	
}
