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

	private final int timeToWaitOhterRequisition = 20000;  // 10s
	
	@Autowired
	private RepositoryHtmlTag repositoryHtmlTag;
	
	public String getSourceCodeRepositoryUrl() {
		
		return repositoryHtmlTag.getSourceCodeRepositoryUrl();
	}
	
	private boolean isSucceedConnection(HttpURLConnection urlConnection) throws IOException {
		
		int attemptsToConnectIfUrlConnectionReturnDifferentHttpOk = 1	;
		
		int responseCode = HttpURLConnection.HTTP_OK;
		
		do {
			
			urlConnection.connect();
			
			responseCode = urlConnection.getResponseCode();
			
			if(responseCode != HttpURLConnection.HTTP_OK) {
				
				try {
					
					if(attemptsToConnectIfUrlConnectionReturnDifferentHttpOk == 3) {
						break;
					}

					attemptsToConnectIfUrlConnectionReturnDifferentHttpOk ++;
					
					Thread.sleep(timeToWaitOhterRequisition);
				
				} catch (InterruptedException e) {

					logger.error(e.getMessage());
					
					throw new HtmlStringContentException(e.getMessage());
				}
			}
			
		} while (responseCode != HttpURLConnection.HTTP_OK);
		
        return responseCode == HttpURLConnection.HTTP_OK;
	}
	
	public List<HtmlLineContent> getContentList(String repositoryUrl) {
		
		List<HtmlLineContent> htmlLineContentList = new ArrayList<HtmlLineContent>();
		
		HttpURLConnection urlConnection = null;
		
		try {
			
			URL url = new URL(repositoryUrl);
			
			urlConnection = (HttpURLConnection) url.openConnection();
					
            if (!isSucceedConnection(urlConnection)) {
            	
            	throw new HtmlStringContentException(urlConnection.getResponseMessage());
            }
			
            int attemptsToConnectIfThrowsTooManyRequests = 1;
			
            do {
            	
                try (BufferedReader bufferedReader = new BufferedReader(
    	                new InputStreamReader(urlConnection.getInputStream(), "UTF-8"))) {
    	        
    				String line;

    				boolean isCatchNumberFileLines = false;
    				boolean isCatchNumberFileBytes = false;
    	        	
    				while ((line = bufferedReader.readLine()) != null) {
    		        	
    					if(line.equals("")) {
    						continue;
    					}
    					
    		        	boolean isLinkToSubDirectory = line.indexOf(repositoryHtmlTag.getTagSearchLinkSubdirectory()) > -1;
    		        	
    		        	if(isLinkToSubDirectory) {
    		        		
    		        		htmlLineContentList.add(new HtmlLineContent(line, isLinkToSubDirectory));
    		        	}

    		        	boolean hasTagSearchLines = line.indexOf(repositoryHtmlTag.getTagSearchLines()) > -1;
    		        	
    					if(isCatchNumberFileLines && !line.trim().equals("")) {
    						
    						if(line.contains("lines")) {
    							
    			        		HtmlLineContent lineContent = new HtmlLineContent(line, true, false);
    			        		htmlLineContentList.add(lineContent);
    						}
    						else {
    							
    			        		HtmlLineContent lineContent = new HtmlLineContent(line, false, true);
    			        		htmlLineContentList.add(lineContent);
    							
    			        		isCatchNumberFileBytes = false;
    						}
    						
    						isCatchNumberFileLines = false;
    					}

    					if(hasTagSearchLines) {
    						isCatchNumberFileLines = true;
    					}

    					boolean hasTagSearchBytes = line.indexOf(repositoryHtmlTag.getTagSearchBytes()) > -1;

    					if(isCatchNumberFileBytes && !line.trim().equals("")) {
    						
    		        		HtmlLineContent lineContent = new HtmlLineContent(line, false, true);
    		        		htmlLineContentList.add(lineContent);
    						
    		        		isCatchNumberFileBytes = false;
    					}

    					if(hasTagSearchBytes) {
    						isCatchNumberFileBytes = true;
    					}		        	
    		        }
    				
    				attemptsToConnectIfThrowsTooManyRequests = 4;
    			}
                catch (Exception e) {
                	
        			if(e.getMessage().toLowerCase().contains("too many requests")) {

        				try {
							
        					attemptsToConnectIfThrowsTooManyRequests ++;
        					
        					Thread.sleep(timeToWaitOhterRequisition);
							
						} catch (InterruptedException e1) {
							
							logger.error(e1.getMessage());
							
							throw new HtmlStringContentException(e1.getMessage());
						}
        			}
        			else {
        				logger.error(e.getMessage());
        				
        				throw new HtmlStringContentException(e.getMessage());
        			}
                }

            } while(attemptsToConnectIfThrowsTooManyRequests <= 3);
		
		} catch(IOException | HtmlStringContentException e) {
			
			if(e instanceof IOException) {
				logger.error("Could not connect to " + repositoryUrl, e);
			} else {
				logger.error(e.getMessage());
			}

			throw new HtmlStringContentException(e.getMessage());
		
		} finally {
			
			if(urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		
		return htmlLineContentList;
	}
}
