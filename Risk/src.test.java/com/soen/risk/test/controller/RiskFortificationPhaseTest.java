/*
 * 
 */
package com.soen.risk.test.controller;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.startegies.RiskHumanStrategy;

/**
 * <h2>Risk Fortification Phase Test</h2>
 * This class is used to test moving armies from adjacent territories to another.
 *
 * @author Neha Dighe
 * @version 1.0
 */

public class RiskFortificationPhaseTest {

	/** The territories. */
	ArrayList<RiskTerritory> territories;

	/** The territories. */
	RiskTerritory tr1,tr2,tr3;

	/** The player 1. */
	RiskPlayer player1;

	/** The player territory map. */
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap;

	/** The risk human strategy. */
	RiskHumanStrategy riskHumanStrategy;

	/** The terrirtory list. */
	List<RiskTerritory> terrirtoryList;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		territories = new ArrayList<RiskTerritory>();
		playerTerritoryMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		player1 = new RiskPlayer("Player1");
		player1.setCurrentPlayerTurn(true);
		String[] parsedTerritory = {"Venezuela","South America","Brazil"};
		tr1 = new RiskTerritory(parsedTerritory);
		tr1.setArmiesPresent(5);
		String[] parsedTerritory1 = {"Brazil","South America","Venezuela","Peru"};
		tr2 = new RiskTerritory(parsedTerritory1);
		tr2.setArmiesPresent(5);
		String[] parsedTerritory2 = {"Peru","South America","Brazil"};
		tr3 = new RiskTerritory(parsedTerritory2);
		tr3.setArmiesPresent(5);
	}

	/**
	 * Test get fortified map.
	 */
	@Test
	public void testGetFortifiedMap() {

		territories.add(tr1);
		territories.add(tr2);
		territories.add(tr3);
		playerTerritoryMap.put(player1, territories);

		riskHumanStrategy=new RiskHumanStrategy();
		playerTerritoryMap = riskHumanStrategy.fortify(playerTerritoryMap);

		terrirtoryList = playerTerritoryMap.get(player1);
		Long []  expected= {(long) 3,(long) 7};
		Long []  actual= {terrirtoryList.get(0).getArmiesPresent(),terrirtoryList.get(1).getArmiesPresent()};

		assertArrayEquals(expected, actual);
	}
}
