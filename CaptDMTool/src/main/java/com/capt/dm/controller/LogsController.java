package com.capt.dm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.capt.dm.binding.objects.BindingObject;
import com.capt.dm.delegate.LoginDelegate;
import com.capt.dm.delegate.ODataDelegate;
import com.capt.dm.model.FieldSet;
import com.capt.dm.model.LoginBean;
import com.capt.dm.model.Result;

/**
 * Handles requests for the application login page.
 */
@Controller
public class LogsController {

	private static final Logger logger = LoggerFactory.getLogger(LogsController.class);

	/**
	 * Method to verify the user credentials for login purpose
	 */

	@RequestMapping(value = "/showLogs", method = RequestMethod.GET)
	public ModelAndView showLogs(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inside showLogs Method");

		ModelAndView model = null;
		HttpSession session = request.getSession();

		try {
			model = new ModelAndView("logs");
			String data = session.getAttribute("data").toString();
			logger.info("LogsController: showLogs: logs"+data);
			session.setAttribute("data", data);
			model.addObject("logs", data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

}
