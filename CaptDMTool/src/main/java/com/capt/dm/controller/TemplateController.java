package com.capt.dm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capt.dm.delegate.ODataDelegate;
import com.google.gson.Gson;

@Controller
public class TemplateController {
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	ODataDelegate templateDelegate;

	@ResponseBody
	@RequestMapping(value = "/getTemplate/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String getTemplate(@PathVariable("clientId") String id) throws Exception{
		
		logger.info("TemplateController: Inside getTemplate Method");

		String tmpGrp = templateDelegate.getTemplate(id); 
		Gson gson = new Gson();
		
		logger.info("TemplateController: getTemplate: result: "+gson.toJson(tmpGrp));
		logger.info("TemplateController: getTemplate: result: "+tmpGrp);
		
		return tmpGrp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getTemplateGrp/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String getTemplateGrp(@PathVariable("clientId") String id) throws Exception{
		
		logger.info("TemplateController: Inside getTemplateGrp Method");

		String tmpGrp = templateDelegate.getTemplateGrp(id); 
		Gson gson = new Gson();
		
		logger.info("TemplateController: getTemplateGrp: result: "+gson.toJson(tmpGrp));
		logger.info("TemplateController: getTemplateGrp: result: "+tmpGrp);
		
		return tmpGrp;
	}

}
