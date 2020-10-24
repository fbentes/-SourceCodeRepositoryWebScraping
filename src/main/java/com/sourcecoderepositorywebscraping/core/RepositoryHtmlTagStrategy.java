package com.sourcecoderepositorywebscraping.core;

/**
 * String Constants to use in GitHub html repository fetch data string .
 * 
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 21/10/2020
 *
 */
public interface RepositoryHtmlTagStrategy {

	/**
	 * Returns repository url.
	 * @return html link of repository.
	 */
	String getSourceCodeRepositoryUrl();
	
	/**
	 * Returns tag that represents the link to subdirectory in repository html.
	 * @return html tag that indicates where is subdiretory in repository html string.
	 */
	String getTagSearchLinkSubdirectory();
	
	/**
	 * Returns tag that represents the number of lines of file by link in repository html.
	 * @return html tag that indicates where is file number lines in repository html string.
	 */
	String getTagSearchLines();
	
	/**
	 * Returns tag that represents the number of bytes of file by link in repository html.
	 * @return tml tag that indicates where is file number bytes in repository html string.
	 */
	String getTagSearchBytes();
}
