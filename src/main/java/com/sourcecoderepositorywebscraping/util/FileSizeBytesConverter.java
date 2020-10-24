package com.sourcecoderepositorywebscraping.util;

public class FileSizeBytesConverter {
	
	public static Float getValueInBytes(String valueOrigin) {
		
		String[] dataSplited = valueOrigin.trim().split(" ");
		
		Float value = Float.valueOf(dataSplited[0].trim());
		
		String unitOfMeasurement = dataSplited[1].toUpperCase();
		
		switch (unitOfMeasurement) {
		case "KB": 
			value = value * 1024;
			break;

		case "MB": 
			value = value * 1024 * 1024;
			break;

		case "GB": 
			value = value * 1024 * 1024 * 1024;
			break;

		default:
			break;
		}
		
		return value;
	}
}
