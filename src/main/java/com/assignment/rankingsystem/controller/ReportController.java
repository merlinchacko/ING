package com.assignment.rankingsystem.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rankingsystem.dto.Players;
import com.assignment.rankingsystem.service.ReportService;

/**
 * @author Merlin
 *
 */
@RestController
public class ReportController {
	
	@Autowired
	ReportService reportService;


	@RequestMapping(value = "/getAllPlayers", method = RequestMethod.GET)
	public Map<Integer, Players> getAllPlayers() {
		
		Map<Integer, Players> playersmap = reportService.getAllPlayers();
		
		return playersmap;
	}


}
