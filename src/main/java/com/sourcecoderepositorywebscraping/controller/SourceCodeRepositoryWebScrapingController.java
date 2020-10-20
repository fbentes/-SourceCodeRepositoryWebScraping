package com.sourcecoderepositorywebscraping.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
public class SourceCodeRepositoryWebScrapingController {

	Logger logger = LoggerFactory.getLogger(SourceCodeRepositoryWebScrapingController.class);
	
	@Autowired
	private SourceCodeRepositoryWebScrapingService sourceCodeRepositoryWebScrapingService;
	
	@CrossOrigin
	@GetMapping("/fetchDataRepository")
	public ResponseEntity<GroupDataByFileExtensionModel> fetchDataRepository(
			@RequestParam(value = "repositoryUrl", required = true) String repositoryUrl) throws MalformedURLException, IOException, InterruptedException, ExecutionException {

		if(!SourceCodeRepositoryUrlValidator.isValidRespositoryUrl(repositoryUrl)) {
			
			return SourceCodeRepositoryUrlValidator.getInstance().getMessageResult();
		}
		
		logger.info("repositoryUrl = " + repositoryUrl);
		
		CompletableFuture<GroupDataByFileExtensionModel> resultAsync
		  = CompletableFuture.supplyAsync(() -> {
			  
			  return  sourceCodeRepositoryWebScrapingService.getRepositotyUrlContentModel(repositoryUrl);
		});
		
		GroupDataByFileExtensionModel result = resultAsync.get();

    	logger.info(result.toJson());
    	
		return ResponseEntity.ok(result);
	}	
	
	@CrossOrigin
	@PostMapping("/clearAllCache")
    public ResponseEntity<String> clearAllCache(){
	
		sourceCodeRepositoryWebScrapingService.evictAllCacheValues();

        return ResponseEntity.ok("All cache was destroied sucessfuly !");
	}
	
	@CrossOrigin
	@PostMapping("/clearCache")
    public ResponseEntity<String> clearCache(@RequestParam(value = "repositoryUrl") String repositoryUrl){
		
    	if(repositoryUrl == null) {
    		
        	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    	}

    	sourceCodeRepositoryWebScrapingService.evictSingleCacheValue(repositoryUrl);
        
        return ResponseEntity.ok("Cache "+repositoryUrl+" was destroied sucessfuly !");
    }	
}
