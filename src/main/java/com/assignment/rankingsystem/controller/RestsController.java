package com.assignment.rankingsystem.controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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

	@Value("classpath:files/names.txt") private Resource names;
	@Value("classpath:files/matches.txt") private Resource matches;

	@RequestMapping(value = "/getallplayers", method = RequestMethod.GET)
	public List<Players> getResource() {
		
		BufferedReader br = null;
		FileReader fr = null;
		List<Players> list = new CopyOnWriteArrayList<Players>();
		try {

			fr = new FileReader(names.getFile());
			br = new BufferedReader(fr);

			String line;

			while ((line = br.readLine()) != null) {
				if(line.length() > 1) {
				
					String[] str = line.split("    ");
					System.out.println( str[1]);
					
					Players p1 = new Players(Integer.parseInt(str[0]), str[1]);
					list.add(p1);
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

		
		/*List<Players> list = new ArrayList<Players>();

		Players p1 = new Players(1, "Masha");
		Players p2 = new Players(2, "Richa");
		Players p3 = new Players(3, "Cyril");
		Players p4 = new Players(4, "Jose");

		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		*/

		return fetchScore(list);
	}
	
	private List<Players> fetchScore(List<Players> list) {
		
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> winmap = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> losemap = new TreeMap<Integer, Integer>();
		BufferedReader br = null;
		FileReader fr = null;
		
		try {

			fr = new FileReader(matches.getFile());
			br = new BufferedReader(fr);

			String line;

			while ((line = br.readLine()) != null) {
				if(line.length() > 1) {
				
					String[] str = line.split(" ");
					int id1 = Integer.parseInt(str[0]);
					int id2 = Integer.parseInt(str[1]);
					
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
					

					/*if(map.containsKey(id1)) {
						
						map.put(id1, map.get(id1)+1);
					}else {

						map.put(id1, 1);
					}
					if(map.containsKey(id2)) {
						
						map.put(id2, map.get(id2)+1);
					} else {

						map.put(id2, 1);
					}*/
				}
			}
			
			for(Players player : list) {

				for (Map.Entry<Integer, Integer> winentry : winmap.entrySet())
				{
					for (Map.Entry<Integer, Integer> loseentry : losemap.entrySet()) {

						if(winentry.getKey() == loseentry.getKey()) {

							if(winentry.getKey() == player.getId()) {

								player.setScore(winentry.getValue()+loseentry.getValue());
								player.setNoofwins(winentry.getValue());
								player.setNoofloses(loseentry.getValue());
							}
						}else {
							if(loseentry.getKey() == player.getId()) {
								player.setScore(loseentry.getValue());
								player.setNoofwins(0);
								player.setNoofloses(loseentry.getValue());
							}
						}
					}

					/*if(winentry.getKey() == player.getId()) {

						player.setScore(winentry.getValue());
						player.setNoofwins(winentry.getValue());
						player.setNoofloses(0);
					}*/
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
		return list;

		
	}

}
