package com.sourcecoderepositorywebscraping.util.scrapring;

public class FileExtensionByLinkCatcher {

	public static String getExtension(String link) {
		
		String[] dataSplited = link.split("\\.");
		
		if(dataSplited.length == 0 || link.lastIndexOf("/") > link.lastIndexOf(".")) {
			
			return "";
		}
		
		String extension = dataSplited[dataSplited.length-1];
		
		return extension;
	}	
}
