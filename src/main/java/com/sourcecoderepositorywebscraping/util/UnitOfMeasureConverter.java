package com.sourcecoderepositorywebscraping.util;

public class UnitOfMeasureConverter {

	public static float ToBytes(String size, String unitOfMeasure) {
		float byteSize = 0;

		if (size == null) {
			size = "0";
		}

		if (unitOfMeasure == null) {
			unitOfMeasure = "";
		}

		switch (unitOfMeasure) {
		case "KB": {
			byteSize = Float.parseFloat(size) * 1024;
			break;
		}
		case "Bytes": {
			byteSize = Float.parseFloat(size);
			break;
		}
		default: {
			break;
		}
		}

		return byteSize;
	}
}
