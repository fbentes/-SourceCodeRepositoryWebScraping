package com.sourcecoderepositorywebscraping.util;

public class FileExtensionByLinkCatcher {

	public static String getExtension(String link) {
		
		String[] dataSplited = link.split("\\.");
		
		if(dataSplited.length == 0 || link.lastIndexOf("/") > link.lastIndexOf(".")) {
			
			return "";
		}
		
		String extension = dataSplited[dataSplited.length-1];
		
		return extension;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(getExtension("https://github.com/fbentes/FrameworkWebDesk/blob/master/FrameworkWebDesk/DataObjectLayer.Business/bin/Debug/log4net.xml"));
		System.out.println(getExtension("https://github.com/fbentes/FrameworkWebDesk/blob/master/FrameworkWebDesk/DataObjectLayer.Business/obj/Debug/DataObjectLayer.Business.dll	"));
		System.out.println(getExtension("https://github.com/fbentes/FrameworkWebDesk/blob/master/FrameworkWebDesk/DataObjectLayer.Business/obj/Debug/ResolveAssemblyReference.cache"));
		System.out.println(getExtension("https://github.com/fbentes/FrameworkWebDesk/tree/master/FrameworkWebDesk/DataObjectLayer.Business/obj"));
	}

}
