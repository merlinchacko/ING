package com.assignment.rankingsystem.service;

import java.io.IOException;
import java.util.List;

import com.assignment.rankingsystem.dto.Matches;
import com.assignment.rankingsystem.dto.Players;

/**
 * @author Merlin
 *
 */
public interface ReportService {

	List<Players> getAllPlayers() throws IOException;

	List<Matches> getSuggestedMatches(List<Players> playersList);

}
