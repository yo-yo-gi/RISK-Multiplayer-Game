package com.soen.risk.test.controller;
/**
 * Junit Test Case for the Risk Map Validator
 * @author Neha
 * @version 1.0
 */
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.controller.RiskReinforcementPhase;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskPlayer;
import com.soen.risk.model.RiskTerritory;
import com.soen.risk.validator.RiskMapValidator;

public class RiskMapValidatorTest extends RiskMapValidator {
	HashMap<RiskPlayer, ArrayList<RiskTerritory>> playerMap;
	ArrayList<RiskContinent> continentList =new ArrayList<RiskContinent>();
	ArrayList<RiskTerritory> terretoryList=new ArrayList<RiskTerritory>();
 	HashMap<RiskPlayer, ArrayList<RiskTerritory>> reinforcedMap;
	RiskReinforcementPhase riskReinforcementPhase ;
	RiskPlayer riskPlayer;
	RiskMapBuilder riskMapBuilder,riskMapBuilderIncorrect;
	ArrayList<String> mapFile=new ArrayList<String>();
	ArrayList<String> mapFileIncorrrect=new ArrayList<String>();
	ArrayList<String> mapFileIncorrect=new ArrayList<String>();
	ArrayList<String> ownedContinents=new ArrayList<>() ;
	RiskMapValidator riskMapValidator;
/**
 * Called before every test case
 * @throws Exception
 */
	@Before
	public void setUp() throws Exception {
		riskMapBuilder=new RiskMapBuilder();
		riskPlayer=new RiskPlayer("test");
		mapFile=riskMapBuilder.parseMapFile("D:\\riskProject\\EarthMap.txt");		
		riskMapBuilder.loadMapData(mapFile);
		continentList=riskMapBuilder.getContinentList();
		terretoryList=riskMapBuilder.getTerritoryList();	
		riskMapValidator = new RiskMapValidator();
		
		//False case
		riskMapBuilderIncorrect=new RiskMapBuilder();
		riskPlayer=new RiskPlayer("test");
		mapFileIncorrrect=riskMapBuilderIncorrect.parseMapFile("D:\\riskProject\\EarthMapIncorrect.txt");		
		riskMapBuilderIncorrect.loadMapData(mapFileIncorrrect);
		continentList=riskMapBuilderIncorrect.getContinentList();
		terretoryList=riskMapBuilderIncorrect.getTerritoryList();	
		riskMapValidator = new RiskMapValidator();
		
	}
/**
 * Test Case to Validate map file
 */
	@Test
	public void testValidateMap() {
		assertTrue(riskMapValidator.validateMap(mapFile));
		assertFalse(riskMapValidator.validateMap(mapFileIncorrrect));
	}

}
