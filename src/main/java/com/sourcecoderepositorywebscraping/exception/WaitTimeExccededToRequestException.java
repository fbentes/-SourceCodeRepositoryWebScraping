package com.sourcecoderepositorywebscraping.exception;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.util.ConstantsMessages;

/**
 * Exception if the current thread excceded limited time to request when http 429 throwed: max 20s.
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 22/10/2020
 * 
 */
public class WaitTimeExccededToRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private GroupDataByFileExtensionModel groupDataByFileExtensionModel;
	
	public WaitTimeExccededToRequestException() {
		super(ConstantsMessages.WAIT_TIME_TO_REQUEST_EXCEPTION_MESSAGE);
		
		groupDataByFileExtensionModel = new GroupDataByFileExtensionModel();
		
		groupDataByFileExtensionModel.setHttpError(ConstantsMessages.WAIT_TIME_TO_REQUEST_EXCEPTION_MESSAGE);
	}

	public GroupDataByFileExtensionModel getGroupDataByFileExtensionModel() {
		return groupDataByFileExtensionModel;
	}
}
