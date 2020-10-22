package com.sourcecoderepositorywebscraping.util.scrapring;

public class FileLinesCatcher {

	public static int getNumberLines(String valueOrigin) {
		
		//17 lines (17 sloc)
		
		if(valueOrigin == null || valueOrigin.trim().equals("")) {
			
			return 0;
		}
		
		String[] dataSplited = valueOrigin.trim().split(" ");
		
		int numberLines = Integer.parseInt(dataSplited[0].trim());
		
		return numberLines;
	}
}
