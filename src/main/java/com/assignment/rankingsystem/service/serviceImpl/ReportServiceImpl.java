package com.assignment.rankingsystem.service.serviceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.assignment.rankingsystem.dto.Matches;
import com.assignment.rankingsystem.dto.Players;
import com.assignment.rankingsystem.service.ReportService;

/**
 * @author Merlin
 *
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

	@Value("classpath:files/names.txt") private Resource names;
	@Value("classpath:files/matches.txt") private Resource matches;
	
	private final Integer DEF_VAL = 0;
	private static Map<Integer, Players> playersmap = new TreeMap<>();
	private static Map<Integer, Integer> winmap     = new TreeMap<>();
	private static Map<Integer, Integer> losemap    = new TreeMap<>();

	/* (non-Javadoc)
	 * @see com.assignment.rankingsystem.service.ReportService#getAllPlayers()
	 * Generate a Map<Integer, Players> for storing Player object where id of
	 * the player is set to key and object itself is set to value
	 */
	@Override
	public Map<Integer, Players> getAllPlayers() {

		//Read names.txt for adding player id and names in Players object.
		readPlayers();
		//Read matches.txt for update Players and Matches object.
		readMatches();
		return playersmap;
	}
	/**
	 * Read names file and update players map by passing filename and action as 'Add'
	 */
	private void readPlayers() {
		
		readFiles(names, "Add");
	}
	/**
	 * Read matches file and update players map by passing filename and action as 'Update'
	 * also calculate score, number of wins, number of loses and update player onject.
	 */
	private void readMatches() {
		
		readFiles(matches, "Update");
		for ( Entry<Integer, Players> entry : playersmap.entrySet()) {
			
			Players player = entry.getValue();
			Integer noofwins = winmap.get(player.getId());
			Integer noofloses = losemap.get(player.getId());
			player.setScore(DEF_VAL);
			player.setNoofwins(DEF_VAL);
			player.setNoofloses(DEF_VAL);
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
	/**
	 * Read any file and calls different methods based on action passed
	 */
	private void readFiles(Resource resource, String action) {

		BufferedReader br = null;
		FileReader fr = null;
		String line;
		try {
			fr = new FileReader(resource.getFile());
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				if(line.length() > 1) {
					if(action.equalsIgnoreCase("Add")){
						addPlayers(line);
					}else {
						updatePlayers(line);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
				if (fr != null)fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Process each line in matches file. Update Player object with setting list of played matches
	 * Also set 2 maps, winmap and losemap for calculating number of wins and loses
	 */
	private void updatePlayers(String line) {

		String[] str = line.split(" ");
		int id1 = Integer.parseInt(str[0]);
		int id2 = Integer.parseInt(str[1]);
		
		List<Matches> matchlist1 = playersmap.get(id1).getMatchlist();
		List<Matches> matchlist2 = playersmap.get(id2).getMatchlist();
		matchlist1 = (matchlist1 == null ? new ArrayList<Matches>() : matchlist1);
		matchlist2 = (matchlist2 == null ? new ArrayList<Matches>() : matchlist2);
		
		Matches matches = new Matches( playersmap.get(id1).getName(), playersmap.get(id2).getName());
		matchlist1.add(matches);
		matchlist2.add(matches);
		playersmap.get(id1).setMatchlist(matchlist1);
		playersmap.get(id2).setMatchlist(matchlist2);

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
	/**
	 * Process each line in names file. Create Player object by adding player id and names.
	 */
	private void addPlayers(String line) {

		String[] str = line.split("    ");
		int playerid = Integer.parseInt(str[0]);
		Players player = new Players(playerid, str[1]);
		playersmap.put(playerid, player);
	}
}
