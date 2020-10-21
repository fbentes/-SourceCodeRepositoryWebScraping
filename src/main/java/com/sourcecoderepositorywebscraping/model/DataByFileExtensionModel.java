package com.sourcecoderepositorywebscraping.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model of soluction with properties lines and bytes of all files in repository.
 * 
 * @author fbent
 * @version 1.0.0.0
 * @since 19/10/2020
 *
 */
@Component
public class DataByFileExtensionModel  implements Serializable {

	private static final long serialVersionUID = -5098158762721667207L;
	
	private String fileExtension;
	
	private int fileNumberOfLines;
	
	@JsonIgnore
	private float fileNumberOfBytes;

	public DataByFileExtensionModel() {
		
	}
	
	public DataByFileExtensionModel(String fileExtension) {
		
		this.fileExtension = fileExtension;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public int getFileNumberOfLines() {
		return fileNumberOfLines;
	}
	
	public void addFileNumberOfLines(int numberOfLines) {
		this.fileNumberOfLines += numberOfLines;
	}
	
	public float getFileNumberOfBytes() {
		return fileNumberOfBytes;
	}
	
	public void addFileNumberOfBytes(float numberOfBytes) {
		this.fileNumberOfBytes += numberOfBytes;
	}

	@JsonProperty("fileNumberOfBytes")
	public String getFileNumberOfBytesFormated() {
		return BytesNumberFormatter.getSize(fileNumberOfBytes);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileExtension == null) ? 0 : fileExtension.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataByFileExtensionModel other = (DataByFileExtensionModel) obj;
		if (fileExtension == null) {
			if (other.fileExtension != null)
				return false;
		} else if (!fileExtension.equals(other.fileExtension))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return fileExtension + ", fileNumberOfLines = " + fileNumberOfLines + ", fileNumberOfBytes = " + fileNumberOfBytes;
	}
}
