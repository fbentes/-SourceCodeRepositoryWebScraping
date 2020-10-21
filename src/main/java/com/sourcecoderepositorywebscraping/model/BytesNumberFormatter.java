package com.sourcecoderepositorywebscraping.model;

public class BytesNumberFormatter {

	public static String getSize(float fileNumberOfBytes) {
				
		String fileNumberOfBytesFormated = String.format("%,.2f",fileNumberOfBytes) + " Bytes";
		
		float totalFileNumberOfGB = fileNumberOfBytes / (1024 * 1024 * 1024);
		
		if(totalFileNumberOfGB > 1) {
			fileNumberOfBytesFormated = String.format("%,.2f",fileNumberOfBytes) + " GB";
		}
		else {
			
			float totalFileNumberOfMB = fileNumberOfBytes / (1024 * 1024);
			
			if(totalFileNumberOfMB > 1) {
				fileNumberOfBytesFormated = String.format("%,.2f",fileNumberOfBytes) + " MB";
			}
			else {
				
				float totalFileNumberOfKB = fileNumberOfBytes / 1024;
				
				if(totalFileNumberOfKB > 1) {
					fileNumberOfBytesFormated = String.format("%,.2f",fileNumberOfBytes) + " KB";
				}
			}
		}
				
		return fileNumberOfBytesFormated;
	}
}
