package com.sourcecoderepositorywebscraping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;
import com.sourcecoderepositorywebscraping.service.SourceCodeRepositoryWebScrapingService;
import com.sourcecoderepositorywebscraping.util.SourceCodeRepositoryUrlValidator;

/**
 * Each requisition will be executed in separate thread.
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 16/10/2020
 *
 */

@CrossOrigin
@RestController
public class SourceCodeRepositoryWebScrapingController {

	@Autowired
    private SourceCodeRepositoryWebScrapingService sourceCodeRepositoryWebScrapingService;
	
	@GetMapping("/fetchDataRepository")
	public ResponseEntity<GroupDataByFileExtensionModel> fetchDataRepository(
			@RequestParam(value = "repositoryUrl", required = true) String repositoryUrl) {

		if(!SourceCodeRepositoryUrlValidator.isValidRespositoryUrl(repositoryUrl)) {
			
			return SourceCodeRepositoryUrlValidator.getInstance().getMessageResult();
		}
		
		GroupDataByFileExtensionModel result = sourceCodeRepositoryWebScrapingService.getRepositotyUrlContentModel(repositoryUrl);
		
		return ResponseEntity.ok(result);
	}	
	
	@PostMapping("/clearAllCache")
    public ResponseEntity<String> clearAllCache(){
	
		sourceCodeRepositoryWebScrapingService.clearAllCache();

        return ResponseEntity.ok("All cache was destroied sucessfuly !");
	}
	
	@PostMapping("/clearCache")
    public ResponseEntity<String> clearCache(@RequestParam(value = "repositoryUrl") String repositoryUrl){
		
    	if(repositoryUrl == null) {
    		
        	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    	}

    	sourceCodeRepositoryWebScrapingService.clearCache(repositoryUrl);
        
        return ResponseEntity.ok("Cache "+repositoryUrl+" was destroied sucessfuly !");
    }	
}
