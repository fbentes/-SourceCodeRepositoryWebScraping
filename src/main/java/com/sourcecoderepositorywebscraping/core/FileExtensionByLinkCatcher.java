package com.sourcecoderepositorywebscraping.core;

/**
 * Catch the file extension from repository url.
 * 
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 19/10/2020
 *
 */
public class FileExtensionByLinkCatcher {

	
	/**
	 * Example: 
	 * 
	 * If link argument equal "https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge/blob/master/IMDbQueryProject/src/test/java/com/imdb/query/util/protocol/impl/IMDbCommunicationProtocolTest.java"
	 * then 
	 * returns "java"
	 *
	 * @param link Link to file from repository.
	 * @return File name extension
	 */
	public static String getFileExtension(String link) {
		
		String[] dataSplited = link.split("\\.");
		
		if(dataSplited.length == 0 || link.lastIndexOf("/") > link.lastIndexOf(".")) {
			
			return "";
		}
		
		String extension = dataSplited[dataSplited.length-1];
		
		return extension;
	}	
}
