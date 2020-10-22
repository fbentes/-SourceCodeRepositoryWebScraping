package com.sourcecoderepositorywebscraping.util.scrapring;

import org.springframework.stereotype.Component;

/**
 * String Constants to use GitHub fetch data.
 * 
 * @author fbent
 *
 */
@Component
public class RepositoryHtmlTagGitHub implements RepositoryHtmlTag {

	@Override
	public String getSourceCodeRepositoryUrl() {
		return "https://github.com/";
	}

	@Override
	public String getTagSearchLinkSubdirectory() {
		return "<a class=\"js-navigation-open link-gray-dark\"";
	}

	@Override
	public String getTagSearchLines() {
		return "<div class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1 mt-2 mt-md-0\">";
	}

	@Override
	public String getTagSearchBytes() {
		return "<span class=\"file-info-divider\"></span>";
	}
}
