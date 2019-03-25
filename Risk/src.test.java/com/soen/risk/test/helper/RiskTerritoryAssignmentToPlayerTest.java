/*
 * 
 */
package com.soen.risk.test.helper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.soen.risk.helper.RiskTerritoryAssignmentToPlayer;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2> Risk Territory Assignment To Player Test</h2>
 * This class is used to test the number of the armies 
 * given to the player based on the number of players.
 * 
 * @author Neha Dighe
 * @author Chirag
 * @version 1.0
 */
public class RiskTerritoryAssignmentToPlayerTest {

	/**
	 * Test assign territory.
	 */
	@Test
	public void testAssignTerritory() {

		List<RiskPlayer> playersNames = new ArrayList<RiskPlayer>();
		List<RiskTerritory> territories = new ArrayList<RiskTerritory>();
		Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new HashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		RiskTerritoryAssignmentToPlayer riskAssignmentToPlayer = new RiskTerritoryAssignmentToPlayer();

		RiskPlayer player1 = new RiskPlayer("Player1");
		RiskPlayer player2 = new RiskPlayer("Player2");
		RiskPlayer player3 = new RiskPlayer("Player3");

		String[] parsedTerritory = {"Venezuela","South America","Brazil"};
		RiskTerritory tr1 = new RiskTerritory(parsedTerritory);

		String[] parsedTerritory1 = {"Brazil","South America","Venezuela","Peru"};
		RiskTerritory tr2 = new RiskTerritory(parsedTerritory1);

		String[] parsedTerritory2 = {"Peru","South America","Brazil"};
		RiskTerritory tr3 = new RiskTerritory(parsedTerritory2);

		playersNames.add(player1);
		playersNames.add(player2);
		playersNames.add(player3);

		territories.add(tr1);
		territories.add(tr2);
		territories.add(tr3);

		playerTerritoryMap = riskAssignmentToPlayer.assignTerritory(playersNames,territories);

		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> stringListEntry : playerTerritoryMap.entrySet()) {
			List<RiskTerritory> value = stringListEntry.getValue();

			assertEquals(1,value.size());
		}
	}
}
