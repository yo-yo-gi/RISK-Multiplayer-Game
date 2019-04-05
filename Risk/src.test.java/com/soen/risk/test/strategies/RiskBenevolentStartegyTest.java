/**
 * 
 */
package com.soen.risk.test.strategies;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;

/**
 * @author Chirag
 * @version 3.0
 */
public class RiskBenevolentStartegyTest {
	
	LinkedHashMap<RiskPlayer, ArrayList<RiskTerritory>> gameMap;
	ArrayList<RiskContinent> riskContinentList =new ArrayList<RiskContinent>();
	RiskMapBuilder riskMapBuilder;
	
	@Before
	public void setUp() {
		riskMapBuilder=new RiskMapBuilder();
		riskContinentList=riskMapBuilder.getContinentList();
	}
	
	@Test
	public void testReinforceBenevolentStartegy() {
		
	}
}
