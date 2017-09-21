package com.assignment.rankingsystem.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rankingsystem.dto.Players;

/**
 * @author Merlin
 *
 */
@RestController
public class RestsController {



	@RequestMapping(value = "/getallplayers", method = RequestMethod.GET)
	public List<Players> getResource() {
		
		List<Players> list = new ArrayList<Players>();

		Players p1 = new Players(1, "Masha");
		Players p2 = new Players(2, "Richa");
		Players p3 = new Players(3, "Cyril");
		Players p4 = new Players(4, "Jose");

		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		
		return list;
	}

}
