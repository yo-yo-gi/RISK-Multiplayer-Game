package com.soen.risk.test.helper;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.helper.RiskMapEditor;
import com.soen.risk.helper.RiskMapUserCreator;

public class RiskMapEditorTest {
	RiskMapUserCreator riskMapCreator;
	ArrayList<String> mapFile=new ArrayList<String>();
	RiskMapEditor riskMapEditor;
	ArrayList<String> continentList = new ArrayList<String>();
	ArrayList<String> territoryList = new ArrayList<String>();
	ArrayList<String> MapList = new ArrayList<String>();
	RiskMapBuilder riskMapBuilder;

	
	@Before
	public void setUp() throws Exception {
		riskMapBuilder = new RiskMapBuilder();
		mapFile=(ArrayList<String>) riskMapBuilder.parseMapFile("D:\\map\\EditMap\\TestMap.txt");
		riskMapEditor = new RiskMapEditor(mapFile);
		
		continentList.add("South_America=2");
		territoryList.add("Venezuela,South_America,Central_America,Peru,Brazil");
		territoryList.add("Peru,South_America,Venezuela,Brazil,Argentina");
		territoryList.add("Brazil,South_America,Venezuela,Peru,Argentina,North_Africa");
		territoryList.add("Argentina,South_America,Peru,Brazil");
		MapList.add("[Map]");
		MapList.add("");
		MapList.add("[Continents]");
		MapList.addAll(continentList);
		MapList.add("-");
		MapList.add("[Territories]");
		MapList.addAll(territoryList);
		MapList.add(";;");
		
	}

	@Test
	public void testEditMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContinentList() {
		assertEquals(continentList,riskMapEditor.getContinentList());
	}

	@Test
	public void testGetTerritoryList() {
		assertEquals(territoryList,riskMapEditor.getTerritoryList());
	}

	@Test
	public void testGetFullMap() {
		assertEquals(MapList,riskMapEditor.getFullMap());
	}

}
