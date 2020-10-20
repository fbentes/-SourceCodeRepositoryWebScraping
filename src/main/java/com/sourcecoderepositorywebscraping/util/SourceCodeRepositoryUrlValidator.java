package com.sourcecoderepositorywebscraping.util;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.ResponseEntity;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;

public class SourceCodeRepositoryUrlValidator {
	
	private static SourceCodeRepositoryUrlValidator validator;
	
	private ResponseEntity<GroupDataByFileExtensionModel> result;

	private SourceCodeRepositoryUrlValidator() {
		
	}
	
	public static SourceCodeRepositoryUrlValidator getInstance() {
		
		if(validator == null) {
			throw new NullPointerException(ConstantsMessages.INSTANCE_VALIDATOR_IS_NULL);
		}
		
		return validator;
	}
	
	public ResponseEntity<GroupDataByFileExtensionModel> getMessageResult() {
		
		return result;
	}
	
	public static boolean isValidRespositoryUrl(String repositoryUrl) {
		
		 GroupDataByFileExtensionModel groupDataByFileExtensionModel = new GroupDataByFileExtensionModel();
		 
		 groupDataByFileExtensionModel.setRepositoryUrl(repositoryUrl);

		 validator = new SourceCodeRepositoryUrlValidator();
		 
		if(repositoryUrl == null) {

			 groupDataByFileExtensionModel.setHttpError(ConstantsMessages.REPOSITORYURL_IS_NULL);
			 
			 validator.result = ResponseEntity.badRequest().body(groupDataByFileExtensionModel);
			 
			return false;
	    }
		
		 String[] schemes = {"http","https"};

		 UrlValidator urlValidator = new UrlValidator(schemes);
	    
		 if (!urlValidator.isValid(repositoryUrl)) {
			 
			 groupDataByFileExtensionModel.setHttpError(ConstantsMessages.REPOSITORYURL_IS_INVALID);
			 
			 validator.result = ResponseEntity.badRequest().body(groupDataByFileExtensionModel);

			 return false;
		 }
		 
		 validator.result = ResponseEntity.ok().body(groupDataByFileExtensionModel);
		 
		 return true;
	}
}
