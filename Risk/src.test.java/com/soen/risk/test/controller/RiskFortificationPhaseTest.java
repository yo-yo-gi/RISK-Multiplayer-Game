package com.soen.risk.test.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.soen.risk.controller.RiskFortificationPhase;
import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.helper.RiskArmyAllocationToPlayers;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
/**
 * 
 * @author Neha
 *
 */
public class RiskFortificationPhaseTest {

	@Test
	public void testGetFortifiedMap() {
		HashMap<RiskPlayer, ArrayList<RiskTerritory>> playerMap;
		
		RiskPlayer playerName = new RiskPlayer();
		ArrayList<RiskTerritory> territories = new ArrayList<RiskTerritory>();
		RiskMapBuilder riskMapBuilder = new RiskMapBuilder();
		Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new HashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		RiskFortificationPhase riskFortificationTest = new RiskFortificationPhase();
		
		RiskPlayer player1 = new RiskPlayer("Player1");
		
		String[] parsedTerritory = {"Venezuela","South America","Brazil"};
		RiskTerritory tr1 = new RiskTerritory(parsedTerritory);
		
		String[] parsedTerritory1 = {"Brazil","South America","Venezuela","Peru"};
		RiskTerritory tr2 = new RiskTerritory(parsedTerritory1);
		
		String[] parsedTerritory2 = {"Peru","South America","Brazil"};
		RiskTerritory tr3 = new RiskTerritory(parsedTerritory2);
		
		territories.add(tr1);
		territories.add(tr2);
		territories.add(tr3);
		
		//playerTerritoryMap.put(player1,(ArrayList<RiskTerritory>) territories);
		
		playerTerritoryMap = riskFortificationTest.getFortifiedMap(player1,territories);
		
		for (Entry<RiskPlayer, ArrayList<RiskTerritory>> stringListEntry : playerTerritoryMap.entrySet()) {
			String player = stringListEntry.getKey().getPlayerName();
			List<RiskTerritory> value = stringListEntry.getValue();
			
			assertEquals(1,value.size());
		}
	}

}
