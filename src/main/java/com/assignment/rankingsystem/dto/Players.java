package com.assignment.rankingsystem.dto;

import java.util.List;

/**
 * @author Merlin
 *
 */
public class Players {
	
	private Integer id;
	private String name;
	private Integer score;
	private Integer noofwins;
	private Integer noofloses;
	private List<Matches> matchlist;
	
	public Players() {}
	
	public Players(Integer id, String name) {
		
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getNoofwins() {
		return noofwins;
	}

	public void setNoofwins(Integer noofwins) {
		this.noofwins = noofwins;
	}

	public Integer getNoofloses() {
		return noofloses;
	}

	public void setNoofloses(Integer noofloses) {
		this.noofloses = noofloses;
	}

	public List<Matches> getMatchlist() {
		return matchlist;
	}

	public void setMatchlist(List<Matches> matchlist) {
		this.matchlist = matchlist;
	}

	@Override
	public String toString() {
		return "Players [id=" + id + ", name=" + name + ", score=" + score + ", noofwins=" + noofwins + ", noofloses="
				+ noofloses + ", matchlist=" + matchlist + "]";
	}

}
