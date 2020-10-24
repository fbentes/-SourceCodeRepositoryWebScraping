package com.sourcecoderepositorywebscraping.util;

import java.text.DecimalFormat;

/**
 * Format file number bytes from each file in repository url.
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 21/10/2020
 *
 */
public class BytesNumberFormatter {

	/**
	 * Example:
	 * 
	 * If fileNumberOfBytes paramater equal 3.758,08 then returns "3.67 KB"  
	 * If fileNumberOfBytes paramater equal 3.827.302,4 then returns "3,65 MB"  
	 * 
	 * @param fileNumberOfBytes Bytes of file.
	 * @return Bytes formated in KB, MB or GB.
	 */
	public static String getSize(float fileNumberOfBytes) {
				
		String fileNumberOfBytesFormated = getBytesFormat(fileNumberOfBytes) + " Bytes";
		
		float totalFileNumberOfGB = fileNumberOfBytes / (1024 * 1024 * 1024);
		
		if(totalFileNumberOfGB > 1) {
			fileNumberOfBytesFormated = getBytesFormat(totalFileNumberOfGB) + " GB";
		}
		else {
			
			float totalFileNumberOfMB = fileNumberOfBytes / (1024 * 1024);
			
			if(totalFileNumberOfMB > 1) {
				fileNumberOfBytesFormated = getBytesFormat(totalFileNumberOfMB) + " MB";
			}
			else {
				
				float totalFileNumberOfKB = fileNumberOfBytes / 1024;
				
				if(totalFileNumberOfKB > 1) {
					fileNumberOfBytesFormated = getBytesFormat(totalFileNumberOfKB) + " KB";
				}
			}
		}
				
		return fileNumberOfBytesFormated;
	}
	
	private static String getBytesFormat(float value ) {
	      DecimalFormat myFormatter = new DecimalFormat("###,###,###.##");
	      return myFormatter.format(value);
   }
}
