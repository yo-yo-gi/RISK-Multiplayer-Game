package com.soen.risk.test.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.helper.RiskMapEditor;
import com.soen.risk.helper.RiskMapUserCreator;
/**
 * 
 * @author Neha
 * Junit Test cases for Risk MapEditor Test
 *
 */
public class RiskMapEditorTest {
	RiskMapUserCreator riskMapCreator;
	ArrayList<String> mapFile=new ArrayList<String>();
	RiskMapEditor riskMapEditor;
	ArrayList<String> continentList = new ArrayList<String>();
	ArrayList<String> continentListNew = new ArrayList<String>();
	ArrayList<String> territoryList = new ArrayList<String>();
	ArrayList<String> MapList = new ArrayList<String>();
	RiskMapBuilder riskMapBuilder;

	
	@Before
	public void setUp() throws Exception {
		riskMapBuilder = new RiskMapBuilder();
		mapFile=(ArrayList<String>) riskMapBuilder.parseMapFile("D:\\map\\EditMap\\TestMap.txt");
		riskMapEditor = new RiskMapEditor(mapFile);
		
		
		/*
		 * continentList = (ArrayList<String>) riskMapEditor.getContinentList();
		 * riskMapEditor.addContinent("TESTContinent");
		 * 
		 * continentList.add("South_America=2");
		 * territoryList.add("Venezuela,South_America,Central_America,Peru,Brazil");
		 * territoryList.add("Peru,South_America,Venezuela,Brazil,Argentina");
		 * territoryList.add(
		 * "Brazil,South_America,Venezuela,Peru,Argentina,North_Africa");
		 * territoryList.add("Argentina,South_America,Peru,Brazil");
		 * MapList.add("[Map]"); MapList.add(""); MapList.add("[Continents]");
		 * MapList.addAll(continentList); MapList.add("-");
		 * MapList.add("[Territories]"); MapList.addAll(territoryList);
		 * MapList.add(";;");
		 */
	}

	
	@Test
	public void testAddContinent() {
		List<String> after=new ArrayList<String>();
		riskMapEditor.addContinent("Antartica=2");
		after=riskMapEditor.getContinentList();
		assertTrue(after.contains("Antartica=2"));
	}
	@Test
	public void testAddTerritory() {
		List<String> after=new ArrayList<String>();
		riskMapEditor.addTerritory("VenezuelaADD,Continent,adj1,adj2");
		after=riskMapEditor.getTerritoryList();
		assertTrue(after.contains("VenezuelaADD,Continent,adj1,adj2"));
	}

	@Test
	public void testDeleteContinent() {
		List<String> after=new ArrayList<String>();
		riskMapEditor.deleteContinent("South_America=2");
		after=riskMapEditor.getContinentList();
		assertFalse(after.contains("South_America=2"));
	}

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
