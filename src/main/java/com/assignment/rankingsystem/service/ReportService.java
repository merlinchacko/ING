package com.assignment.rankingsystem.service;

import java.util.Map;

import com.assignment.rankingsystem.dto.Players;

/**
 * @author Merlin
 *
 */
public interface ReportService {

	Map<Integer, Players> getAllPlayers();

}
