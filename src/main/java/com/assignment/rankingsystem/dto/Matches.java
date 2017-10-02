package com.assignment.rankingsystem.dto;

/**
 * @author Merlin
 *
 */
public class Matches {
	
	private String winner;
	private String looser;
	
	public Matches() {}
	
	public Matches(String winner, String looser) {
		
		this.winner = winner;
		this.looser = looser;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getLooser() {
		return looser;
	}

	public void setLooser(String looser) {
		this.looser = looser;
	}

}
