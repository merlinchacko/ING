package com.assignment.rankingsystem.dto;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Merlin
 *
 */
@Component
public class Players {
	
	private Integer id;
	private String name;
	private Integer rating;
	private double score;
	private Integer noofwins;
	private Integer noofloses;
	private List<Matches> matchlist;
	private Integer rank;
	
	public Players() {}
	
	public Players(Integer id, String name, Integer rating) {
		
		this.id = id;
		this.name = name;
		this.rating = rating;
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

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "Players [id=" + id + ", name=" + name + ", score=" + score + ", noofwins=" + noofwins + ", noofloses="
				+ noofloses + ", matchlist=" + matchlist + "]";
	}

}
