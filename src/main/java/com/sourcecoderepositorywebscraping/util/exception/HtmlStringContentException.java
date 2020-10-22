package com.sourcecoderepositorywebscraping.util.exception;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;

public class HtmlStringContentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private GroupDataByFileExtensionModel groupDataByFileExtensionModel;
	
	public HtmlStringContentException(String message) {
		super(message);
		
		groupDataByFileExtensionModel = new GroupDataByFileExtensionModel();
		
		groupDataByFileExtensionModel.setHttpError(message);
	}

	public GroupDataByFileExtensionModel getGroupDataByFileExtensionModel() {
		return groupDataByFileExtensionModel;
	}
}
