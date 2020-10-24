package com.sourcecoderepositorywebscraping.core;

import org.springframework.stereotype.Component;

/**
 * String Constants to use in GitHub html repository fetch data string .
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 21/10/2020
 *
 */
@Component
public class RepositoryHtmlTagGitHub implements RepositoryHtmlTagStrategy {

	/**
	 * Returns repository url.
	 */
	@Override
	public String getSourceCodeRepositoryUrl() {
		return "https://github.com/";
	}

	/**
	 * Returns tag that represents the link to subdirectory in repository html.
	 */
	@Override
	public String getTagSearchLinkSubdirectory() {
		return "<a class=\"js-navigation-open link-gray-dark\"";
	}

	/**
	 * Returns tag that represents the number of lines of file by link in repository html.
	 */
	@Override
	public String getTagSearchLines() {
		return "<div class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1 mt-2 mt-md-0\">";
	}

	/**
	 * Returns tag that represents the number of bytes of file by link in repository html.
	 */
	@Override
	public String getTagSearchBytes() {
		return "<span class=\"file-info-divider\"></span>";
	}
}
