/**
 * 
 */
package com.soen.risk.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskSavedGameController;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskSavedGameControllerTest {
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> mainMap=new LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>>();
	ArrayList<RiskContinent> cotinentList=new ArrayList<RiskContinent>();
	
	/**
	 * Sets the data for the test methods.
	 */
	@Before
	public void setUp() {
		RiskPlayer player1=new RiskPlayer();
		player1.setPlayerName("Test");
		RiskTerritory territory=new RiskTerritory();
		territory.setTerritoryName("territory");
		ArrayList<RiskTerritory> territoryList=new ArrayList<RiskTerritory>();
		territoryList.add(territory);
		RiskContinent continent=new RiskContinent("testContinent", 3);
		mainMap.put(player1, territoryList);
		cotinentList.add(continent);
	}
	
	
	/**
	 * Test Saved game case
	 */
	@Test
	public void testSavedGameFunctinality() {
		RiskSavedGameController riskSavedGameController= new RiskSavedGameController();
		assertTrue(riskSavedGameController.saveGame(mainMap, cotinentList));
	}
	
}
