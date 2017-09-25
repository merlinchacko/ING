package com.assignment.rankingsystem.dto;

/**
 * @author Merlin
 *
 */
public class Matches {
	
	private int uniqueid;
	private Integer winner;
	private Integer looser;
	
	public Matches() {}
	
	public Matches(Integer winner, Integer looser) {
		
		this.winner = winner;
		this.looser = looser;
	}

	public int getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(int uniqueid) {
		this.uniqueid = uniqueid;
	}

	public Integer getWinner() {
		return winner;
	}

	public void setWinner(Integer winner) {
		this.winner = winner;
	}

	public Integer getLooser() {
		return looser;
	}

	public void setLooser(Integer looser) {
		this.looser = looser;
	}

}
