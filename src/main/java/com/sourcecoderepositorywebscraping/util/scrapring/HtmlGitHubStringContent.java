package com.sourcecoderepositorywebscraping.util.scrapring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcecoderepositorywebscraping.util.exception.HtmlStringContentException;

@Component
public class HtmlGitHubStringContent implements HtmlStringContent {

	Logger logger = LoggerFactory.getLogger(HtmlGitHubStringContent.class);

	@Autowired
	private RepositoryHtmlTag repositoryHtmlTag;
	
	public String getSourceCodeRepositoryUrl() {
		
		return repositoryHtmlTag.getSourceCodeRepositoryUrl();
	}
	
	public List<HtmlLineContent> getContentList(String repositoryUrl) {
		
		List<HtmlLineContent> htmlLineContentList = new ArrayList<HtmlLineContent>();
		
		HttpURLConnection urlConnection = null;
		
		try {
			
			URL url = new URL(repositoryUrl);
			
			urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.connect();
			
			int code = urlConnection.getResponseCode();
			
            if (code != HttpURLConnection.HTTP_OK) {
            	
            	throw new HtmlStringContentException(urlConnection.getResponseMessage());
            }
			    
			try (BufferedReader bufferedReader = new BufferedReader(
	                new InputStreamReader(urlConnection.getInputStream(), "UTF-8"))) {
	        
				String line;

				boolean catchLines = false;
				boolean catchBytes = false;
	        	
				while ((line = bufferedReader.readLine()) != null) {
		        	
					if(line.equals("")) {
						continue;
					}
					
		        	boolean isDirectory = line.indexOf(repositoryHtmlTag.getTagSearchLinkSubdirectory()) > -1;
		        	
		        	if(isDirectory) {
		        		
		        		htmlLineContentList.add(new HtmlLineContent(line, isDirectory));
		        	}

		        	boolean hasTagSearchLines = line.indexOf(repositoryHtmlTag.getTagSearchLines()) > -1;
		        	
					if(catchLines && !line.trim().equals("")) {
						
						if(line.contains("lines")) {
							
			        		HtmlLineContent lineContent = new HtmlLineContent(line, true, false);
			        		htmlLineContentList.add(lineContent);
			        		
			        		logger.info("line = " + lineContent.toString());
						}
						else {
							
			        		HtmlLineContent lineContent = new HtmlLineContent(line, false, true);
			        		htmlLineContentList.add(lineContent);
							
			        		logger.info("line = " + lineContent.toString());

			        		catchBytes = false;
						}
						
						catchLines = false;
					}

					if(hasTagSearchLines) {
						catchLines = true;
					}

					boolean hasTagSearchBytes = line.indexOf(repositoryHtmlTag.getTagSearchBytes()) > -1;

					if(catchBytes && !line.trim().equals("")) {
						
		        		HtmlLineContent lineContent = new HtmlLineContent(line, false, true);
		        		htmlLineContentList.add(lineContent);
						
		        		logger.info("line = " + lineContent.toString());

		        		catchBytes = false;
					}

					if(hasTagSearchBytes) {
						catchBytes = true;
					}		        	
		        }
			} 
			
		} catch(IOException | HtmlStringContentException e) {
			
			logger.error("Could not connect to " + repositoryUrl, e);

			throw new HtmlStringContentException(e.getMessage());
		
		} finally {
			
			if(urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		
		return htmlLineContentList;
	}
}
