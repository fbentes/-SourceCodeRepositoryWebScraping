package com.sourcecoderepositorywebscraping.core;

/**
 * {@literal
 * Represents each html string line from repository url.
 * 
 * Example of 3 instances types:
 * 
 * 1) {content="125 lines (90 sloc)", isDirectory=false, hasTagSearchLines=true, hasTagSearchBytes=true}
 * 
 * 2) {content="            3.67 KB", isDirectory=false, hasTagSearchLines=false, hasTagSearchBytes=true}
 * 
 * 3) {content="href="/fbentes/EmitesJavaJobApplicationChallenge/tree/master/IMDbQueryProject/javadoc">javadoc</a></span>", 
 *     isDirectory=true, 
 *     hasTagSearchLines=false, 
 *     hasTagSearchBytes=false}
 *     }
 *     
 * @author Fábio Bentes
 * @version 1.0.0.0
 * @since 21/10/2020
 *
 */
public class HtmlLineContent {

	private String content;
	private boolean isDirectory;
	
	boolean hasTagSearchLines;
	boolean hasTagSearchBytes;
	
	public HtmlLineContent(String content, boolean isDirectory) {
		
		this.content = content;
		this.isDirectory = isDirectory;
	}

	public HtmlLineContent(String content, boolean hasTagSearchLines, boolean hasTagSearchBytes) {
		
		this.content = content;
		this.hasTagSearchLines = hasTagSearchLines;
		this.hasTagSearchBytes = hasTagSearchBytes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public boolean isHasTagSearchLines() {
		return hasTagSearchLines;
	}

	public boolean isHasTagSearchBytes() {
		return hasTagSearchBytes;
	}
	
	@Override
	public String toString() {
		
		return content + " - isDirectory = " + isDirectory + 
						" - hasTagSearchLines = " + hasTagSearchLines + 
						" - hasTagSearchBytes = " +hasTagSearchBytes;
	}
}
