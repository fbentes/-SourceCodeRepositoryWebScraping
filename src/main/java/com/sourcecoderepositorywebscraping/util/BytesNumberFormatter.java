package com.sourcecoderepositorywebscraping.util;

import java.text.DecimalFormat;

public class BytesNumberFormatter {

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
