package com.sourcecoderepositorywebscraping.util.scrapring;

import java.util.List;

public interface HtmlStringContent {

	String getSourceCodeRepositoryUrl();
	
	List<HtmlLineContent> getContentList(String repositoryUrl);
}
