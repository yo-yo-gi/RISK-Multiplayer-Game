/*
 * 
 */
package com.soen.risk.test.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.helper.RiskMapEditor;
import com.soen.risk.helper.RiskMapUserCreator;
// TODO: Auto-generated Javadoc

/**
 * The Class RiskMapEditorTest.
 *
 * @author Neha
 * Junit Test cases for Risk MapEditor Test
 */
public class RiskMapEditorTest {

	/** The risk map creator. */
	RiskMapUserCreator riskMapCreator;

	/** The map file. */
	ArrayList<String> mapFile=new ArrayList<String>();

	/** The risk map editor. */
	RiskMapEditor riskMapEditor;

	/** The continent list. */
	ArrayList<String> continentList = new ArrayList<String>();

	/** The continent list new. */
	ArrayList<String> continentListNew = new ArrayList<String>();

	/** The territory list. */
	ArrayList<String> territoryList = new ArrayList<String>();

	/** The Map list. */
	ArrayList<String> MapList = new ArrayList<String>();

	/** The risk map builder. */
	RiskMapBuilder riskMapBuilder;


	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		riskMapBuilder = new RiskMapBuilder();

		String mapFilePath=Paths.get(System.getProperty("user.dir") + "/src.main.resources/JUnitTestMaps").toAbsolutePath().toString();
		mapFile = (ArrayList<String>) riskMapBuilder.parseMapFile(mapFilePath.concat("//EditMapTest.txt"));
		riskMapEditor = new RiskMapEditor(mapFile);

	}


	/**
	 * Test add continent.
	 */
	@Test
	public void testAddContinent() {
		List<String> after=new ArrayList<String>();
		riskMapEditor.addContinent("Antartica=2");
		after=riskMapEditor.getContinentList();
		assertTrue(after.contains("Antartica=2"));
	}

	/**
	 * Test add territory.
	 */
	@Test
	public void testAddTerritory() {
		List<String> after=new ArrayList<String>();
		riskMapEditor.addTerritory("VenezuelaADD,Continent,adj1,adj2");
		after=riskMapEditor.getTerritoryList();
		assertTrue(after.contains("VenezuelaADD,Continent,adj1,adj2"));
	}

	/**
	 * Test delete continent.
	 */
	@Test
	public void testDeleteContinent() {
		List<String> after=new ArrayList<String>();
		riskMapEditor.deleteContinent("south_america=2");
		after=riskMapEditor.getContinentList();
		assertFalse(after.contains("south_america=2"));
	}

	/**
	 * Test delete territory.
	 */
	@Test
	public void testDeleteTerritory() {
		List<String> after=new ArrayList<String>();
		List<String> before=new ArrayList<String>();
		before = riskMapEditor.getTerritoryList();
		riskMapEditor.deleteTerritory("Venezuela");
		after=riskMapEditor.getTerritoryList();
		assertFalse(after.equals(before));
	}

}
