package com.soen.risk.test.controller;

import static org.junit.Assert.*;
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
/**
 * Junit Test Case for the Risk Map Validator
 * @author Neha
 * @version 1.0
 */
public class RiskMapValidatorTest extends RiskMapValidator {

	RiskMapBuilder riskMapBuilder,riskMapBuilderIncorrect,riskMapBuilderConnectivity,riskMapBuilderDuplicacy;
	ArrayList<String> mapFile=new ArrayList<String>();
	ArrayList<String> mapFileIncorrrect=new ArrayList<String>();
	ArrayList<String> mapFileConnectivity=new ArrayList<String>();
	ArrayList<String> mapFileDuplicacy=new ArrayList<String>();
		
//	ArrayList<String> ownedContinents=new ArrayList<>() ;
	RiskMapValidator riskMapValidator,riskMapValidator1,riskMapValidator2,riskMapValidator3;
/**
 * Called before every test case
 * @throws Exception
 */
	@Before
	public void setUp() throws Exception {
		riskMapBuilder=new RiskMapBuilder();
		mapFile=(ArrayList<String>) riskMapBuilder.parseMapFile("D:\\map\\EarthMap.txt");		
		riskMapValidator = new RiskMapValidator();
		
		//Check for wrong syntax of map file
		riskMapBuilderIncorrect=new RiskMapBuilder();
		mapFileIncorrrect=(ArrayList<String>) riskMapBuilderIncorrect.parseMapFile("D:\\map\\EarthMapIncorrect.txt");		
		riskMapValidator1 = new RiskMapValidator();
		
		//Check for connectivity of territory graph
		riskMapBuilderConnectivity=new RiskMapBuilder();
		mapFileConnectivity=(ArrayList<String>) riskMapBuilderConnectivity.parseMapFile("D:\\map\\EarthMapConnectivity.txt");			
		riskMapValidator2 = new RiskMapValidator();
		
		//Check for duplicacy
		riskMapBuilderDuplicacy=new RiskMapBuilder();
		mapFileDuplicacy=(ArrayList<String>) riskMapBuilderDuplicacy.parseMapFile("D:\\map\\EarthMapDuplicacy.txt");		
		riskMapValidator3 = new RiskMapValidator();
	}
/**
 * Test Case to Validate map file - Correct File
 */
	@Test
	public void testValidateMap() {
		assertFalse(riskMapValidator.validateMap(mapFile));	
	}
	/**
	 * Test Case to Validate map file - Correct File
	 * Check for wrong syntax of map file
	 */
	@Test
	public void testValidateMapWrongSyntax() {
		assertFalse(riskMapValidator1.validateMap(mapFileIncorrrect));
	}
	/**
	 * Test Case to Validate map file - Check for connectivity of territory graph
	 * Check for wrong syntax of map file
	 */
	@Test
	public void testValidateMapConnectivity() {
		assertFalse(riskMapValidator2.validateMap(mapFileConnectivity));
	}
	/**
	 * Test Case to Validate map file - Check for Duplicacy 
	 * Check for wrong syntax of map file
	 */
	@Test
	public void testValidateMapDuplicate() {
		assertFalse(riskMapValidator3.validateMap(mapFileDuplicacy));
	}
}
