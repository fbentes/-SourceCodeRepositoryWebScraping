package com.sourcecoderepositorywebscraping.core;

/**
 *  Catch file number lines from html line string from respository.
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 19/10/2020
 *
 */
public class FileLinesCatcher {

	/**
	 * Example: 
	 *
	 * If valueOrigin argument equal "125 lines (90 sloc)" from repository "https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge/blob/master/IMDbQueryProject/src/test/java/com/imdb/query/util/protocol/impl/IMDbCommunicationProtocolTest.java" 
	 * then
	 * returns 125
	 * 
	 * @param valueOrigin Html string line from html file from repository.
	 * @return File number
	 */
	public static int getNumberLines(String valueOrigin) {
		
		if(valueOrigin == null || valueOrigin.trim().equals("")) {
			
			return 0;
		}
		
		String[] dataSplited = valueOrigin.trim().split(" ");
		
		int numberLines = Integer.parseInt(dataSplited[0].trim());
		
		return numberLines;
	}
}
