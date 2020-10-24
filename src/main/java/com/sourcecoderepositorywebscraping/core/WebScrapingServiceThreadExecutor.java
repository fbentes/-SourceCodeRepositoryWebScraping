package com.sourcecoderepositorywebscraping.core;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sourcecoderepositorywebscraping.exception.HtmlStringContentException;
import com.sourcecoderepositorywebscraping.model.DataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.util.ConstantsMessages;
import com.sourcecoderepositorywebscraping.util.FileSizeBytesConverter;

/**
 * Each request is processed in separate thread to parsing the url and return json.
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 22/10/2020
 *
 */
public class WebScrapingServiceThreadExecutor extends Thread {

	private static Logger logger = LoggerFactory.getLogger(WebScrapingServiceThreadExecutor.class);
	
	private String repositoryUrl;
	
	private HtmlStringParserContentStrategy htmlGitHubStringContent;
	
	private GroupDataByFileExtensionModel groupDataByFileExtensionModel;
	
	public WebScrapingServiceThreadExecutor(String repositoryUrl, HtmlStringParserContentStrategy htmlGitHubStringContent) {
		
		this.repositoryUrl = repositoryUrl;
		this.htmlGitHubStringContent = htmlGitHubStringContent;
	}
	
    public GroupDataByFileExtensionModel getGroupDataByFileExtensionModel() {
		return groupDataByFileExtensionModel;
	}

	@Override
	public void run() {
		
    	 synchronized(this) {
    	 
    			groupDataByFileExtensionModel = new GroupDataByFileExtensionModel();

    			try {
    			
    				groupDataByFileExtensionModel = getRepositotyUrlContentModel(repositoryUrl, groupDataByFileExtensionModel);
    			
    			} catch (Exception e) {
    				
    				logger.error(e.getMessage());
    				
    				groupDataByFileExtensionModel.setHttpError(String.format(ConstantsMessages.REPOSITORY_URL_CONTENT_MODEL_ERROR,e.getMessage()));

    			} finally {
    				
        	    	notify();
    			}
    			
    	    	logger.info(groupDataByFileExtensionModel.toJson());
    	 }
    }
    
	/**
	 * Object model to represent data of file number line and bytes by each file extension.
	 * 
	 * @param repositoryUrl
	 * @param groupDataByFileExtensionModel
	 * @return
	 * @throws IOException
	 */
	private GroupDataByFileExtensionModel getRepositotyUrlContentModel(
			final String repositoryUrl, 
			GroupDataByFileExtensionModel groupDataByFileExtensionModel) throws IOException {
		
		try {
						
			List<HtmlLineContent> lineHtmlContentList = htmlGitHubStringContent.getContentList(repositoryUrl);
			
			int fileNumberLines;
			float fileNumberSize;
			
			String fileExtension = FileExtensionByLinkCatcher.getFileExtension(repositoryUrl);
			
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
					
					groupDataByFileExtensionModel = getRepositotyUrlContentModel(
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
