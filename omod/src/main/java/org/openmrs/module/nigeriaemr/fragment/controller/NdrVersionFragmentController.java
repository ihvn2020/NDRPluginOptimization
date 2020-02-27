package org.openmrs.module.nigeriaemr.fragment.controller;

import com.google.gson.Gson;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.omodmodels.Version;
import org.openmrs.ui.framework.SimpleObject;

import javax.servlet.http.HttpServletRequest;

public class NdrVersionFragmentController {
	
	public void controller() {
		
	}
	
	public String getVersionNumber(HttpServletRequest request) {
		Version version = null;
		try {
			version = Utils.getNmrsVersion();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		return gson.toJson(version);
	}
	
	public SimpleObject getVersionNumberAsObject(HttpServletRequest request) {
		Version version = null;
		try {
			version = Utils.getNmrsVersion();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return SimpleObject.fromObject(version, null, new String[] {});
	}
}
