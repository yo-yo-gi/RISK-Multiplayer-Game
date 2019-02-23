package com.soen.risk.test.controller;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.controller.RiskReinforcementPhase;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

public class RiskReinforcementPhaseTest {
	HashMap<RiskPlayer, ArrayList<RiskTerritory>> playerMap;
	ArrayList<RiskContinent> continentList =new ArrayList<RiskContinent>();
	ArrayList<RiskTerritory> terretoryList=new ArrayList<RiskTerritory>();
 	HashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;
	RiskReinforcementPhase riskReinforcementPhase ;
	RiskPlayer riskPlayer;
	RiskMapBuilder riskMapBuilder;
	ArrayList<String> mapFile=new ArrayList<String>();
	ArrayList<String> ownedContinents=new ArrayList<>() ;
	
	
	@Before
	public void setUp() {
		riskMapBuilder=new RiskMapBuilder();
		riskPlayer=new RiskPlayer("test");
		mapFile=(ArrayList<String>) riskMapBuilder.parseMapFile("D:\\map\\EarthMap.txt");		
		riskMapBuilder.loadMapData(mapFile);
		continentList=riskMapBuilder.getContinentList();
		terretoryList=riskMapBuilder.getTerritoryList();		
//		reinforcedMap	= new HashMap<RiskPlayer, ArrayList<RiskTerritory>>(playerMap);
		riskReinforcementPhase = new RiskReinforcementPhase();
	}
	@Test
	public void testArmyCalculationPerPlayer() {
		assertEquals(14,riskReinforcementPhase.calculateArmy(riskPlayer,terretoryList,continentList ));
	}
	
	@Test
	public void testArmyCalculationPerPlayer_controlValue() {
		ownedContinents.add("North_America");
		riskPlayer.setOccupiedContinents(ownedContinents);		
		assertEquals(19,riskReinforcementPhase.calculateArmy(riskPlayer,terretoryList,continentList ));
	}
	
	
//	RiskMapValidator.validate(mapFile);
}
