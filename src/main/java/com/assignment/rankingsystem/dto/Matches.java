package com.assignment.rankingsystem.dto;

import org.springframework.stereotype.Component;

/**
 * @author Merlin
 *
 */
@Component
public class Matches {
	
	private String playerA;
	private String playerB;
	
	public Matches() {}
	
	public Matches(String playerA, String playerB) {
		
		this.playerA = playerA;
		this.playerB = playerB;
	}

	public String getplayerA() {
		return playerA;
	}

	public void setplayerA(String playerA) {
		this.playerA = playerA;
	}

	public String getplayerB() {
		return playerB;
	}

	public void setplayerB(String playerB) {
		this.playerB = playerB;
	}

}
