package com.assignment.rankingsystem.service.serviceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
	private final Integer INITIAL_RATING = 1000;
	private static Map<Integer, Players> playersmap = new HashMap<>();
	private static Map<Integer, Integer> winmap     = null;
	private static Map<Integer, Integer> losemap    = null;

	/* (non-Javadoc)
	 * @see com.assignment.rankingsystem.service.ReportService#getAllPlayers()
	 * Generate a Map<Integer, Players> for storing Player object where id of
	 * the player is set to key and object itself is set to value
	 */
	@Override
	public List<Players> getAllPlayers() throws IOException {

		readPlayers();
		readMatches();
		//To sort the Players based on score convert map to arraylist
		List<Players> playersList = new ArrayList<Players>(playersmap.values());
		playersList.sort(Comparator.comparing(Players::getScore, Comparator.reverseOrder()).thenComparing(Players::getNoofloses));
		int count = 1;
		for(Players player : playersList) {
			player.setRank(count++);
		}
		
		return playersList;
	}
	/* (non-Javadoc)
	 * @see com.assignment.rankingsystem.service.ReportService#getSuggestedMatches(java.util.List)
	 * Generate a list of next suggested matches based on similar Elo rating (checked Elo rating difference of 1 or equal)
	 */
	@Override
	public List<Matches> getSuggestedMatches(List<Players> playersList) {
		
		List<Matches> matchList = new ArrayList<>();
		Matches match = null;
		playersList.sort(Comparator.comparing(Players::getRating, Comparator.reverseOrder()));
		for(Players player : playersList){
			System.out.println(player.getName()+"::"+player.getRating());
		}
		for(int i=0; i<playersList.size()-1; i++){

			int diff = playersList.get(i).getRating() - playersList.get(i+1).getRating();
			if(diff <= 1){
				match = new Matches(playersList.get(i).getName(), playersList.get(i+1).getName());
				matchList.add(match);
				i = i+1;//For avoiding players playing multiple matches
			}
		}
		return matchList;
	}
	/**
	 * Read names file and update players map by passing filename and action as 'Add'
	 * @throws IOException 
	 */
	private void readPlayers() throws IOException {
		
		readFiles(names, "Add");
	}
	/**
	 * Read matches file and update players map by passing filename and action as 'Update'
	 * also calculate score, number of wins, number of loses and update player onject.
	 * @throws IOException 
	 */
	private void readMatches() throws IOException {
		
		readFiles(matches, "Update");
		for ( Entry<Integer, Players> entry : playersmap.entrySet()) {
			
			Players player = entry.getValue();
			Integer noofwins = winmap.get(player.getId());
			Integer noofloses = losemap.get(player.getId());
			player.setScore(DEF_VAL);
			player.setNoofwins(DEF_VAL);
			player.setNoofloses(DEF_VAL);
			if(noofwins != null) {
				player.setScore(noofwins.doubleValue());
				player.setNoofwins(noofwins);
			}
			if(noofloses != null) {
				player.setNoofloses(noofloses);
			}
		}
	}
	/**
	 * Read any file and calls different methods based on action passed
	 * @throws IOException 
	 */
	private void readFiles(Resource resource, String action) throws IOException {

		winmap     = new TreeMap<>();
		losemap    = new TreeMap<>();
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
			throw new IOException();
		} finally {
			try {
				if (br != null)br.close();
				if (fr != null)fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new IOException();
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
		
		int player1Rating = playersmap.get(id1).getRating();
		int player2Rating = playersmap.get(id2).getRating();
		
		playersmap.get(id1).setRating(calculate2PlayersRating(player1Rating, player2Rating, "+"));
		playersmap.get(id2).setRating(calculate2PlayersRating(player2Rating, player1Rating, "-"));
	}
	/**
	 * Process each line in names file. Create Player object by adding player id and names.
	 */
	private void addPlayers(String line) {

		String[] str = line.split("    ");
		int playerid = Integer.parseInt(str[0]);
		Players player = new Players(playerid, str[1], INITIAL_RATING);
		playersmap.put(playerid, player);
	}
	
	/**
	 * @param player1Rating = current rating of first player
	 * @param player2Rating = current rating of second player
	 * @param outcome = winner/looser of said match
	 * @return modified rating of player 1
	 * This method will calculate new rating of a player using Elo rating algorithm
	 */
	private int calculate2PlayersRating(int player1Rating, int player2Rating, String outcome) {

        double actualScore;

        // winner
        if (outcome.equals("+")) {
            actualScore = 1.0;
            // draw
        } else if (outcome.equals("=")) {
            actualScore = 0.5;
            // lose
        } else if (outcome.equals("-")) {
            actualScore = 0.0;
            // invalid outcome
        } else {
            return player1Rating;
        }

        // calculate expected outcome
        double exponent = (double) (player2Rating - player1Rating) / 400;
        double expectedOutcome = (1 / (1 + (Math.pow(10, exponent))));

        // K-factor is given as a constant value , can be determined different way
        int K = 32;

        // calculate new rating
        int newRating = (int) Math.round(player1Rating + K * (actualScore - expectedOutcome));

        return newRating;
    }
	
}
