package com.assignment.rankingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Merlin
 *
 */
@Controller
public class MainController {
	
	@RequestMapping(value="/",method = RequestMethod.GET)
    public String welcomePage() {
		
        return "index";
    }

}
