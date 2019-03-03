package com.soen.risk.test.controller;

import static org.junit.Assert.*;

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
		
	RiskMapValidator riskMapValidator,riskMapValidator1,riskMapValidator2,riskMapValidator3;
/**
 * Called before every test case
 * @throws Exception
 */
	@Before
	public void setUp() throws Exception {
		riskMapBuilder=new RiskMapBuilder();
		String mapFilePath=Paths.get(System.getProperty("user.dir") + "/src.main.resources/JUnitTestMaps/").toAbsolutePath().toString();
		mapFile = (ArrayList<String>) riskMapBuilder.parseMapFile(mapFilePath.concat("//ValidEarthMap.txt"));
		riskMapValidator = new RiskMapValidator();
		
		//Check for wrong syntax of map file
		riskMapBuilderIncorrect=new RiskMapBuilder();
		mapFileIncorrrect=(ArrayList<String>) riskMapBuilderIncorrect.parseMapFile(mapFilePath.concat("//EarthMapIncorrect.txt"));		
		riskMapValidator1 = new RiskMapValidator();
		
		//Check for connectivity of territory graph
		riskMapBuilderConnectivity=new RiskMapBuilder();
		mapFileConnectivity=(ArrayList<String>) riskMapBuilderConnectivity.parseMapFile(mapFilePath.concat("//EarthMapConnectivity.txt"));			
		riskMapValidator2 = new RiskMapValidator();
		
		//Check for duplicacy
		riskMapBuilderDuplicacy=new RiskMapBuilder();
		mapFileDuplicacy=(ArrayList<String>) riskMapBuilderDuplicacy.parseMapFile(mapFilePath.concat("//EarthMapDuplicacy.txt"));		
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
