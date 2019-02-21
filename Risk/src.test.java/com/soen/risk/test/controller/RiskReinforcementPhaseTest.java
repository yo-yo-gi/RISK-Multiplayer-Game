package com.soen.risk.test.controller;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskReinforcementPhase;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

public class RiskReinforcementPhaseTest {
	HashMap<RiskPlayer, ArrayList<RiskTerritory>> playerMap;
	ArrayList<RiskContinent> riskContinentList;
	HashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;
	RiskReinforcementPhase risk ;
	
	@Before
	public void setUp() throws Exception {
		reinforcedMap	= new HashMap<RiskPlayer, ArrayList<RiskTerritory>>(playerMap);
		risk = new RiskReinforcementPhase();
	}
	@Test
	public void testArmyCalculationPerPlayer() {
		assertEquals("",risk.armyCalculationPerPlayer(playerMap, riskContinentList));
	}
}
