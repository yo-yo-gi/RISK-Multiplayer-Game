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

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.helper.RiskTerritoryAssignmentToPlayer;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
// TODO: Auto-generated Javadoc

/**
 * The Class RiskTerritoryAssignmentToPlayerTest.
 * 
 * @author Neha
 * @version 1.0
 * 
 */
public class RiskTerritoryAssignmentToPlayerTest {

	/**
	 * Test assign territory.
	 */
	@Test
	public void testAssignTerritory() {
		HashMap<RiskPlayer, ArrayList<RiskTerritory>> playerMap;

		List<RiskPlayer> playersNames = new ArrayList<RiskPlayer>();
		List<RiskTerritory> territories = new ArrayList<RiskTerritory>();
		RiskMapBuilder riskMapBuilder = new RiskMapBuilder();
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
			String player = stringListEntry.getKey().getPlayerName();
			List<RiskTerritory> value = stringListEntry.getValue();

			assertEquals(1,value.size());
		}
	}
}
