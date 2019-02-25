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
	RiskMapBuilder riskMapBuilder,riskMapBuilderIncorrect,riskMapBuilderConnectivity,riskMapBuilderDuplicacy;
	ArrayList<String> mapFile=new ArrayList<String>();
	ArrayList<String> mapFileIncorrrect=new ArrayList<String>();
	ArrayList<String> mapFileConnectivity=new ArrayList<String>();
	ArrayList<String> mapFileDuplicacy=new ArrayList<String>();
		
	ArrayList<String> ownedContinents=new ArrayList<>() ;
	RiskMapValidator riskMapValidator,riskMapValidator1,riskMapValidator2,riskMapValidator3;
/**
 * Called before every test case
 * @throws Exception
 */
	@Before
	public void setUp() throws Exception {
		riskMapBuilder=new RiskMapBuilder();
		riskPlayer=new RiskPlayer("test");
		mapFile=(ArrayList<String>) riskMapBuilder.parseMapFile("D:\\map\\EarthMap.txt");		
		riskMapBuilder.loadMapData(mapFile);
		continentList=riskMapBuilder.getContinentList();
		terretoryList=riskMapBuilder.getTerritoryList();	
		riskMapValidator = new RiskMapValidator();
		
		//Check for wrong syntax of map file
		riskMapBuilderIncorrect=new RiskMapBuilder();
		riskPlayer=new RiskPlayer("test");
		mapFileIncorrrect=(ArrayList<String>) riskMapBuilderIncorrect.parseMapFile("D:\\map\\EarthMapIncorrect.txt");		
		riskMapBuilderIncorrect.loadMapData(mapFileIncorrrect);
		continentList=riskMapBuilderIncorrect.getContinentList();
		terretoryList=riskMapBuilderIncorrect.getTerritoryList();	
		riskMapValidator1 = new RiskMapValidator();
		
		//Check for connectivity of territory graph
		riskMapBuilderConnectivity=new RiskMapBuilder();
		riskPlayer=new RiskPlayer("test");
		mapFileConnectivity=(ArrayList<String>) riskMapBuilderConnectivity.parseMapFile("D:\\map\\EarthMapConnectivity.txt");		
		riskMapBuilderConnectivity.loadMapData(mapFileConnectivity);
		continentList=riskMapBuilderConnectivity.getContinentList();
		terretoryList=riskMapBuilderConnectivity.getTerritoryList();	
		riskMapValidator2 = new RiskMapValidator();
		
		//Check for duplicacy
		riskMapBuilderDuplicacy=new RiskMapBuilder();
		riskPlayer=new RiskPlayer("test");
		mapFileDuplicacy=(ArrayList<String>) riskMapBuilderDuplicacy.parseMapFile("D:\\map\\EarthMapDuplicacy.txt");		
		riskMapBuilderDuplicacy.loadMapData(mapFileDuplicacy);
		continentList=riskMapBuilderDuplicacy.getContinentList();
		terretoryList=riskMapBuilderDuplicacy.getTerritoryList();	
		riskMapValidator3 = new RiskMapValidator();
		
	}
/**
 * Test Case to Validate map file - Correct File
 */
	@Test
	public void testValidateMap() {
		assertTrue(riskMapValidator.validateMap(mapFile));
		
	}
	/**
	 * Test Case to Validate map file - Correct File
	 * Check for wrong syntax of map file
	 */
	public void testValidateMapWrongSyntax() {
		assertFalse(riskMapValidator1.validateMap(mapFileIncorrrect));
	}
	/**
	 * Test Case to Validate map file - Check for connectivity of territory graph
	 * Check for wrong syntax of map file
	 */
	public void testValidateMapConnectivity() {
		assertFalse(riskMapValidator2.validateMap(mapFileConnectivity));
	}
	
	/**
	 * Test Case to Validate map file - Check for Duplicacy 
	 * Check for wrong syntax of map file
	 */
	public void testValidateMapDuplicate() {
		assertFalse(riskMapValidator3.validateMap(mapFileDuplicacy));
	}
	

}
