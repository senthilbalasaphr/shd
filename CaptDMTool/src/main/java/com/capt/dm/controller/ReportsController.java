package com.capt.dm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.capt.dm.delegate.ODataDelegate;
import com.capt.dm.view.CreateExcelView;

@Controller
public class ReportsController {

	
@Autowired
ODataDelegate oDataDelegate;

@RequestMapping(value = "/PositionReport", method = RequestMethod.GET)
public ModelAndView getPostionReport()
	
		throws Exception {
	
	ModelAndView model = null;
	model = new ModelAndView("PositionReport");

	
	
	return model;
	

}
	
}
