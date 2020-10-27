package com.sourcecoderepositorywebscraping.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;

@Component
public class GroupDataByFileExtensionModelCache {

	private Map<String, GroupDataByFileExtensionModel> groupDataByFileExtensionModelCache; 
	
	public GroupDataByFileExtensionModelCache() {
		
		groupDataByFileExtensionModelCache = new HashMap<>();
	}
	
	public void addItem(String repositoryUrl, GroupDataByFileExtensionModel groupDataByFileExtensionModel) {
		
		groupDataByFileExtensionModelCache.put(repositoryUrl, groupDataByFileExtensionModel);
	}

	public GroupDataByFileExtensionModel getItem(String repositoryUrl) {
		
		return groupDataByFileExtensionModelCache.get(repositoryUrl);
	}

	public void clearCache(String repositoryUrl) {
		
		groupDataByFileExtensionModelCache.remove(repositoryUrl);
	}
	
	public void clearAllCache() {
		
		groupDataByFileExtensionModelCache.clear();
	}
}
