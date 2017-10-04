package com.assignment.rankingsystem.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rankingsystem.dto.Matches;
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
	public ResponseEntity<List<Players>> getAllPlayers() {
		
		List<Players> playersList = new ArrayList<>();
		
		try {
			playersList = reportService.getAllPlayers();
		} catch (IOException e) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(playersList, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSuggestedMatches", method = RequestMethod.POST)
	public List<Matches> getSuggestedMatches(@RequestBody List<Players> playersList) {
		
		List<Matches> matchList = reportService.getSuggestedMatches(playersList);
		
		return matchList;
	}
}
