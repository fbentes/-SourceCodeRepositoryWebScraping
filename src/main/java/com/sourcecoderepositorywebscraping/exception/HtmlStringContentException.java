package com.sourcecoderepositorywebscraping.exception;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;

/**
 * Exception to any throws by any object involved with the request on parser html repository.
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 22/10/2020
 *
 */
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
