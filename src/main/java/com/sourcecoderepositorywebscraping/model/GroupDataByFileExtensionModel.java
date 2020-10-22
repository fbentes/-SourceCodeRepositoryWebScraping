package com.sourcecoderepositorywebscraping.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.Gson;
import com.sourcecoderepositorywebscraping.util.BytesNumberFormatter;

/**
 * Model aggregate all the models that contais count lines and bytes of all files in repository.
 * 
 * @author fbent
 *
 * @author fbent
 * @version 1.0.0.0
 * @since 19/10/2020
 */
@Component
@JsonPropertyOrder({ "repositoryUrl", "totalFileNumberOfLines", "totalFileNumberOfBytes", "httpError", "dataByFileExtensionModelList" })
public class GroupDataByFileExtensionModel  implements Serializable {

	private static final long serialVersionUID = -3410669692176028544L;
	
	private String repositoryUrl;
	
	private int totalFileNumberOfLines;
	
	@JsonIgnore
	private float totalFileNumberOfBytes;
	
	@JsonProperty("totalFileNumberOfBytes")
    private String fileNumberOfBytesFormated;
	
	private String httpError;
	
	private List<DataByFileExtensionModel> dataByFileExtensionModelList;
	
	public GroupDataByFileExtensionModel() {
		totalFileNumberOfLines = 0;
		totalFileNumberOfBytes = 0;
		dataByFileExtensionModelList = new ArrayList<>();
	}
	
	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public int getTotalFileNumberOfLines() {
		return totalFileNumberOfLines;
	}

	public float getTotalFileNumberOfBytes() {
		return totalFileNumberOfBytes;
	}

	@JsonProperty("totalFileNumberOfBytes")
	public String getTotalFileNumberOfBytesFormated() {
		return fileNumberOfBytesFormated;
	}

	public void addFileNumberOfLines(DataByFileExtensionModel dataByFileExtensionModel) {
	
		int index = dataByFileExtensionModelList.indexOf(dataByFileExtensionModel);
		
		if(index > -1) {
			dataByFileExtensionModelList.get(index).addFileNumberOfLines(dataByFileExtensionModel.getFileNumberOfLines());
		}
		else {
			dataByFileExtensionModelList.add(dataByFileExtensionModel);
		}
		
		totalFileNumberOfLines += dataByFileExtensionModel.getFileNumberOfLines();
	}

	public void addFileNumberOfBytes(DataByFileExtensionModel dataByFileExtensionModel) {
		
		int index = dataByFileExtensionModelList.indexOf(dataByFileExtensionModel);
		
		if(index > -1) {
			dataByFileExtensionModelList.get(index).addFileNumberOfBytes(dataByFileExtensionModel.getFileNumberOfBytes());
		}
		else {
			dataByFileExtensionModelList.add(dataByFileExtensionModel);
		}
		
		totalFileNumberOfBytes += dataByFileExtensionModel.getFileNumberOfBytes();
	}

	public List<DataByFileExtensionModel> getDataByFileExtensionModelList() {
		
		return Collections.unmodifiableList(dataByFileExtensionModelList);
	}
	
	public String getHttpError() {
		return httpError;
	}

	public void setHttpError(String httpError) {
		this.httpError = httpError;
	}

	public String toJson( ) {
		
		Gson gson = new Gson();
		
		String json = gson.toJson(this);
		
		return json;
	}

	public void finish() {
		fileNumberOfBytesFormated = BytesNumberFormatter.getSize(totalFileNumberOfBytes);
	}
}
