/**
 * 
 */
package com.soen.risk.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soen.risk.helper.Constants;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskTerritory;


// TODO: Auto-generated Javadoc
/**
 * <h2>Map Builder</h2>
 * This class parse and validate map file,
 * save countries and continents to build map,
 * create adjacency list and return map file.
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0
 */
public class RiskMapBuilder {

	/** The continent list. */
	private ArrayList<RiskContinent> continentList=new ArrayList<RiskContinent>();

	/** The territory list. */
	private ArrayList<RiskTerritory> territoryList=new ArrayList<RiskTerritory>();

	/** The adjacency map. */
	private Map<String, List<String>> adjacencyMap=new HashMap<String, List<String>>();

	/** The map upload status. */
	private boolean mapUploadStatus=false;	

	/** The risk map builder. */
	RiskMapBuilder riskMapBuilder;

	/**
	 * Load initial game data to before starting game.
	 *
	 * @param MapFile the map file
	 */

	public void loadMapData(List<String> MapFile) {
		ArrayList<String> parsedMapFile= new ArrayList<String>();
		for (String currLine : MapFile) {
			parsedMapFile.add(currLine.toLowerCase());
		}
		riskMapBuilder = new RiskMapBuilder(); 
		continentList=riskMapBuilder.addContinents(parsedMapFile);
		territoryList=riskMapBuilder.addTerritories(parsedMapFile);
		continentList=riskMapBuilder.addTerritoriesToContinents(continentList,territoryList); 
		adjacencyMap=riskMapBuilder.buildAdjacencyMap(territoryList);
	}

	/**
	 * Parse map file and create arrayList to store it.
	 *
	 * @param mapFilePath the map file path
	 * @return mapFileList list of strings with each line in map file.
	 */

	public ArrayList<String> parseMapFile(String mapFilePath) {
		ArrayList<String> mapFileList = new ArrayList<String>();
		ArrayList<String> processedMapFileList = new ArrayList<String>();
		try {
			mapFileList = (ArrayList<String>) Files.readAllLines(Paths.get(mapFilePath), StandardCharsets.UTF_8);
			for (String currLine : mapFileList) {
				processedMapFileList.add(currLine.replaceAll("\\s", "").toLowerCase());
			}			
			setMapUploadStatus(true);
		} catch (IOException e) {
			System.out.println("Error while reading map file.");
			e.printStackTrace();
		}
		return processedMapFileList;
	}

	/**
	 * Parse map file and initializes the continents.
	 * @param mapFileList line by line list of map file
	 * @return addedContinentList list of Continents objects.
	 */

	public ArrayList<RiskContinent> addContinents(List<String> mapFileList) {		
		ArrayList<RiskContinent> addedContinentList;
		int startIndex = Constants.ZERO;
		int endIndex = Constants.ZERO;
		RiskContinent riskContinent;

		startIndex=mapFileList.indexOf("[continents]")+1;
		endIndex=mapFileList.indexOf("-")-1;
		addedContinentList = new ArrayList<RiskContinent>();
		for(int i=startIndex;i<=endIndex;i++) {
			String[] parsedContinenet=mapFileList.get(i).split("=");
			riskContinent=new RiskContinent(parsedContinenet[0],Integer.parseInt(parsedContinenet[1]));
			addedContinentList.add(riskContinent);
		}

		return addedContinentList;
	}

	/**
	 * Parse map file and initializes the territories.
	 * @param mapFileList line by line list of map file
	 * @return addedTerritoryList list of Territory objects.
	 */

	public ArrayList<RiskTerritory> addTerritories(List<String> mapFileList) {
		ArrayList<RiskTerritory> addedTerritoryList;
		int startIndex = Constants.ZERO;
		int endIndex = Constants.ZERO;
		RiskTerritory riskTerritory;
		int idCounter=Constants.ZERO;

		startIndex=mapFileList.indexOf("[territories]")+1;
		endIndex=mapFileList.indexOf(";;")-1;
		addedTerritoryList=new ArrayList<RiskTerritory>();
		for(int i=startIndex;i<=endIndex;i++) {
			String[] parsedTerritory=mapFileList.get(i).split(",");
			riskTerritory=new RiskTerritory(parsedTerritory);
			riskTerritory.setTerritoryId(idCounter);
			addedTerritoryList.add(riskTerritory);
			idCounter++;
		}
		return addedTerritoryList;
	}

	/**
	 * Adding territories to continents.
	 * @param continentList list of continents
	 * @param territoryList list of territories
	 * @return loadedContinentList list of continents with territory objects.
	 */

	private ArrayList<RiskContinent> addTerritoriesToContinents(ArrayList<RiskContinent> continentList, List<RiskTerritory> territoryList) {
		ArrayList<RiskContinent> loadedContinentList = new ArrayList<RiskContinent>(continentList);
		ArrayList<String> tempRiskTerritories;

		for (RiskTerritory currentTerritory : territoryList) {
			for (RiskContinent currentContinent : loadedContinentList) {
				if((currentContinent.getContinentName()).equalsIgnoreCase(currentTerritory.getContinent())) {
					tempRiskTerritories=new ArrayList<String>();
					if(null!=currentContinent.getIncludedTerritories())
						tempRiskTerritories=currentContinent.getIncludedTerritories();
					tempRiskTerritories.add(currentTerritory.getTerritoryName());
					currentContinent.setIncludedTerritories(tempRiskTerritories);
				}
			}
		}
		for (RiskContinent currContinent : continentList) {
			if (null==currContinent.getIncludedTerritories()) {
				loadedContinentList.remove(currContinent);
			}
		}		
		return loadedContinentList;
	}

	/**
	 * Builds the adjacency map.
	 *
	 * @param territoryList list of territories
	 * @return loadedAdjMap
	 */

	private Map<String, List<String>> buildAdjacencyMap(ArrayList<RiskTerritory> territoryList) {
		Map<String, List<String>> loadedAdjMap=new HashMap<String, List<String>>();

		for (RiskTerritory currentTerritory : territoryList) {
			loadedAdjMap.put(currentTerritory.getTerritoryName(), currentTerritory.getAdjacents());
		}
		return loadedAdjMap;
	}

	/**
	 * Gets the territory list.
	 *
	 * @return territoryList
	 */

	public ArrayList<RiskTerritory> getTerritoryList(){

		return territoryList;
	}

	/**
	 * Gets the continent list.
	 *
	 * @return continentList
	 */

	public ArrayList<RiskContinent> getContinentList(){

		return continentList;
	}

	/**
	 * Gets the adjacency map.
	 *
	 * @return adjacencyMap
	 */

	public Map<String, List<String>> getAdjacencyMap(){

		return adjacencyMap;
	}

	/**
	 * Gets the map upload status.
	 *
	 * @return the mapUploadStatus
	 */

	public boolean getMapUploadStatus() {
		return mapUploadStatus;
	}

	/**
	 * Sets the map upload status.
	 *
	 * @param mapUploadStatus the mapUploadStatus to set
	 */

	public void setMapUploadStatus(boolean mapUploadStatus) {
		this.mapUploadStatus = mapUploadStatus;
	}

	/**
	 * Gets the id by territory name.
	 *
	 * @param riskTerritory the risk territory
	 * @return the id by territory name
	 */
	public int getIdByTerritoryName(String riskTerritory) {
		int idForTerritory=Constants.ZERO;
		for (RiskTerritory currTerritory : territoryList) {
			if(riskTerritory.equalsIgnoreCase(currTerritory.getTerritoryName())) {
				idForTerritory=currTerritory.getTerritoryId();
			}
		}

		return idForTerritory;		
	}

}
