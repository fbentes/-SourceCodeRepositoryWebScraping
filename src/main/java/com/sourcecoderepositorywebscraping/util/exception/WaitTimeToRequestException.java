package com.sourcecoderepositorywebscraping.util.exception;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.util.ConstantsMessages;

public class WaitTimeToRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private GroupDataByFileExtensionModel groupDataByFileExtensionModel;
	
	public WaitTimeToRequestException() {
		super(ConstantsMessages.WAIT_TIME_TO_REQUEST_EXCEPTION_MESSAGE);
		
		groupDataByFileExtensionModel = new GroupDataByFileExtensionModel();
		
		groupDataByFileExtensionModel.setHttpError(ConstantsMessages.WAIT_TIME_TO_REQUEST_EXCEPTION_MESSAGE);
	}

	public GroupDataByFileExtensionModel getGroupDataByFileExtensionModel() {
		return groupDataByFileExtensionModel;
	}
}
