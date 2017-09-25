package com.assignment.rankingsystem.controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rankingsystem.dto.Matches;
import com.assignment.rankingsystem.dto.Players;

/**
 * @author Merlin
 *
 */
@RestController
public class ReportController {

	@Value("classpath:files/names.txt") private Resource names;
	@Value("classpath:files/matches.txt") private Resource matches;

	private final Integer DEF_VAL = 0;

	@RequestMapping(value = "/getallplayers", method = RequestMethod.GET)
	public List<Map<Integer, Players>> getResource() {

		BufferedReader br = null;
		FileReader fr = null;
		Players player = null;
		Map<Integer, Players> playersmap = null;
		List<Map<Integer, Players>> playerslist = new CopyOnWriteArrayList<>();
		try {

			fr = new FileReader(names.getFile());
			br = new BufferedReader(fr);

			String line;

			while ((line = br.readLine()) != null) {
				if(line.length() > 1) {

					String[] str = line.split("    ");
					int playerid = Integer.parseInt(str[0]);
					player = new Players(playerid, str[1]);
					playersmap = new TreeMap<>();
					playersmap.put(playerid, player);
					playerslist.add(playersmap);
				}
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
		for(Map<Integer, Players> map : playerslist) {
			map.forEach((k,v)->System.out.println("Key : " + k + " Value : " + v));
		}
		return fetchScore(playerslist);
	}

	private List<Map<Integer, Players>> fetchScore(List<Map<Integer, Players>> playerslist) {

		List<Matches> matchlist = new CopyOnWriteArrayList<Matches>();
		Map<Integer, Integer> winmap = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> losemap = new TreeMap<Integer, Integer>();
		BufferedReader br = null;
		FileReader fr = null;
		Integer id = 1;
		try {

			fr = new FileReader(matches.getFile());
			br = new BufferedReader(fr);

			String line;

			while ((line = br.readLine()) != null) {
				if(line.length() > 1) {

					String[] str = line.split(" ");
					int id1 = Integer.parseInt(str[0]);
					int id2 = Integer.parseInt(str[1]);

					Matches matches = new Matches(id1, id2);
					matchlist.add(matches);
					if(winmap.containsKey(id1)) {

						winmap.put(id1, winmap.get(id1)+1);
					}else {

						winmap.put(id1, 1);
					}
					if(losemap.containsKey(id2)) {

						losemap.put(id2, losemap.get(id2)+1);
					} else {

						losemap.put(id2, 1);
					}

				}
			}
			for(Map<Integer, Players> playermap : playerslist) {

				for ( Entry<Integer, Players> entry : playermap.entrySet()) {
					
					Integer key = entry.getKey();
					Players player = entry.getValue();

					player.setScore(DEF_VAL);
					player.setNoofwins(DEF_VAL);
					player.setNoofloses(DEF_VAL);

					Integer noofwins = winmap.get(player.getId());
					Integer noofloses = losemap.get(player.getId());

					if(noofwins != null) {
						player.setScore(player.getScore() + noofwins);
						player.setNoofwins(noofwins);
					}

					if(noofloses != null) {
						player.setScore(player.getScore() +noofloses);
						player.setNoofloses(noofloses);
					}
				}

			}
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();
			}
		}
		return playerslist;
	}

}
