/*
 * 
 */
package com.soen.risk.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.controller.RiskReinforcementPhase;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
// TODO: Auto-generated Javadoc

/**
 * The Class RiskReinforcementPhaseTest.
 *
 * @author Neha
 * @version 1.0
 * Junit Test case for Reinforcement
 */
public class RiskReinforcementPhaseTest {
	
	/** The player map. */
	HashMap<RiskPlayer, ArrayList<RiskTerritory>> playerMap;
	
	/** The continent list. */
	ArrayList<RiskContinent> continentList =new ArrayList<RiskContinent>();
	
	/** The territory list. */
	ArrayList<RiskTerritory> territoryList=new ArrayList<RiskTerritory>();
 	
	 /** The reinforced map. */
	 HashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;
	
	/** The risk reinforcement phase. */
	RiskReinforcementPhase riskReinforcementPhase ;
	
	/** The risk player. */
	RiskPlayer riskPlayer;
	
	/** The risk map builder. */
	RiskMapBuilder riskMapBuilder;
	
	/** The map file. */
	ArrayList<String> mapFile=new ArrayList<String>();
	
	/** The owned continents. */
	ArrayList<String> ownedContinents=new ArrayList<>() ;
	
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		riskMapBuilder=new RiskMapBuilder();
		riskPlayer=new RiskPlayer("test");
		mapFile=(ArrayList<String>) riskMapBuilder.parseMapFile(Paths.get(System.getProperty("user.dir") + "/src.main.resources/maps/EarthMap.txt").toAbsolutePath().toString());		
		riskMapBuilder.loadMapData(mapFile);
		continentList=riskMapBuilder.getContinentList();
		territoryList=riskMapBuilder.getTerritoryList();		
		riskReinforcementPhase = new RiskReinforcementPhase();
	}
	
	/**
	 * Test army calculation per player.
	 */
	@Test
	public void testArmyCalculationPerPlayer() {
		assertEquals(14,riskReinforcementPhase.calculateArmy(riskPlayer,territoryList,continentList ));
	}
	
	/**
	 * Test army calculation per player fail.
	 */
	@Test
	public void testArmyCalculationPerPlayerFail() {
		assertNotEquals(15,riskReinforcementPhase.calculateArmy(riskPlayer,territoryList,continentList ));
	}
	
	/**
	 * Test army calculation per player control value.
	 */
	@Test
	public void testArmyCalculationPerPlayer_controlValue() {
		ownedContinents.add("North_America");
		riskPlayer.setOccupiedContinents(ownedContinents);		
		assertEquals(19,riskReinforcementPhase.calculateArmy(riskPlayer,territoryList,continentList ));
	}
	
	/**
	 * Test army calculation per player control value fail.
	 */
	@Test
	public void testArmyCalculationPerPlayer_controlValueFail() {
		ownedContinents.add("North_America");
		riskPlayer.setOccupiedContinents(ownedContinents);		
		assertNotEquals(33,riskReinforcementPhase.calculateArmy(riskPlayer,territoryList,continentList ));
	}
}
