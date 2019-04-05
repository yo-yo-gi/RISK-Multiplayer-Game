/*
 * 
 */
package com.soen.risk.test.controller;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
/**
 * Fortification done from Venezuela with army 5 to Brazil
 */
public class RiskFortificationPhaseTest {

	/**
	 * Test get fortified map.
	 */
	@Test
	public void testGetFortifiedMap() {

		ArrayList<RiskTerritory> territories = new ArrayList<RiskTerritory>();
		LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();


		RiskPlayer player1 = new RiskPlayer("Player1");
		player1.setCurrentPlayerTurn(true);
		String[] parsedTerritory = {"Venezuela","South America","Brazil"};
		RiskTerritory tr1 = new RiskTerritory(parsedTerritory);
		tr1.setArmiesPresent(5);
		String[] parsedTerritory1 = {"Brazil","South America","Venezuela","Peru"};
		RiskTerritory tr2 = new RiskTerritory(parsedTerritory1);
		tr2.setArmiesPresent(5);
		String[] parsedTerritory2 = {"Peru","South America","Brazil"};
		RiskTerritory tr3 = new RiskTerritory(parsedTerritory2);
		tr3.setArmiesPresent(5);

		territories.add(tr1);
		territories.add(tr2);
		territories.add(tr3);
		playerTerritoryMap.put(player1, territories);

		RiskHumanStrategy riskHumanStrategy=new RiskHumanStrategy();
		playerTerritoryMap = riskHumanStrategy.fortify(playerTerritoryMap);

		List<RiskTerritory> terrirtoryList = playerTerritoryMap.get(player1);
		Integer []  expected= {3,7};
		Integer []  actual= {terrirtoryList.get(0).getArmiesPresent(),terrirtoryList.get(1).getArmiesPresent()};


		assertArrayEquals(expected, actual);

	}

}
