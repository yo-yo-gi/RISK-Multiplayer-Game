package com.soen.risk.test.helper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.helper.RiskArmyAllocationToPlayers;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

public class RiskArmyAllocationToPlayersTest {

	@Test
	public void testAssignArmiesToPlayers() {
		HashMap<RiskPlayer, ArrayList<RiskTerritory>> playerMap;
		
		RiskPlayer playerName = new RiskPlayer();
		List<RiskTerritory> territories = new ArrayList<RiskTerritory>();
		RiskMapBuilder riskMapBuilder = new RiskMapBuilder();
		Map<RiskPlayer, ArrayList<RiskTerritory>> playerTerritoryMap = new HashMap<RiskPlayer, ArrayList<RiskTerritory>>();
		RiskArmyAllocationToPlayers riskAssignmentToPlayer = new RiskArmyAllocationToPlayers();
		
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
		
		playerTerritoryMap.put(player1,(ArrayList<RiskTerritory>) territories);
		
		playerTerritoryMap = riskAssignmentToPlayer.assignArmiesToPlayers(playerTerritoryMap);
		
		for (RiskTerritory riskTerritory : territories) {
			System.out.println(riskTerritory.getTerritoryName() + " armies in " + riskTerritory.getArmiesPresent());	
			
			assertEquals(0,riskTerritory.getArmiesPresent());
		}	
	}
}
