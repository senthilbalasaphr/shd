package com.capt.dm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginDelegate loginDelegate;

	@Autowired
	private ODataDelegate templateDelegate;

	/**
	 * Method to verify the user credentials for login purpose
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("loginBean") LoginBean loginBean, BindingResult br) {
		logger.info("Inside Login Method");

		ModelAndView model = null;

		if (br.hasErrors()) {
			return new ModelAndView("login");
		}

		try {
			boolean isValidUser = loginDelegate.isValidUser(loginBean.getUserName(), loginBean.getPassword());

			if (isValidUser) {
				Map<String, String> clientMap = templateDelegate.getClient();

				model = new ModelAndView("valueSelect");
				model.addObject("clients", clientMap);

			} else {
				model = new ModelAndView("login");
				model.addObject("loginBean", new LoginBean());
				model.addObject("error", "Invalid credentials!!");
				request.setAttribute("message", "Invalid credentials!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

}
