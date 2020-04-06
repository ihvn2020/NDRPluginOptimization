/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.xmlgenerator;

import java.io.File;

/**
 * @author lordmaul
 */
public class Tester {
	
	public static void main(String[] args) {
		System.out.println("testing");
		Tester t = new Tester();
		String path = "reports";
		t.ensureDownloadFolderExist(path);
		String startDate = "2019-01-01 00:00:00";
		String endDate = "2019-06-12 23:59:59";
		
		XMLGenerator xml = new XMLGenerator(path, null, startDate, endDate);
		xml.startGenerating();
	}
	
	public String ensureDownloadFolderExist(String path) {
		//create report download folder at the server. skip if already exist
		
		// old implementation // String folder = new File(request.getRealPath(request.getContextPath())).getParentFile().toString() + "\\downloads"; //request.getRealPath(request.getContextPath()) + "\\reports";
		
		File dir = new File(path);
		Boolean b = dir.mkdir();
		System.out.println("Creating download folder : " + path + "was successful : " + b);
		return path;
	}
	
}
