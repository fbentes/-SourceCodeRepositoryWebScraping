package com.sourcecoderepositorywebscraping.util.scrapring;

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
