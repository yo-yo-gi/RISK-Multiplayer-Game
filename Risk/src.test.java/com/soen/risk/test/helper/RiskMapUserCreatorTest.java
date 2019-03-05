/*
 * 
 */
package com.soen.risk.test.helper;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.helper.RiskMapEditor;
import com.soen.risk.helper.RiskMapUserCreator;

// TODO: Auto-generated Javadoc
/**
 * The Class RiskMapUserCreatorTest.
 * @author Neha
 * @version 1.0
 */
public class RiskMapUserCreatorTest {

	/** The risk map creator. */
	RiskMapUserCreator riskMapCreator;
	
	/** The map file. */
	ArrayList<String> mapFile=new ArrayList<String>();
	
	/** The risk map editor. */
	RiskMapEditor riskMapEditor;
	
	/** The continent list. */
	ArrayList<String> continentList = new ArrayList<String>();
	
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
		
		riskMapCreator = new RiskMapUserCreator();
		
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

	/**
	 * Test map creator.
	 */
	@Test
	public void testMapCreator() {
		assertEquals(MapList,riskMapCreator.mapCreator());
	}

}
