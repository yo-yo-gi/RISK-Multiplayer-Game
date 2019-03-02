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

import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2>Map Builder</h2>
 * <ul>
 * <li>Parsing and validating map file.
 * <li>Saving countries and Continents to build map.
 * <li>Creating adjucency list and returning map file
 *
 * </ul>
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0.0
 * @since 2019-01-28
 */
public class RiskMapBuilder {


	private ArrayList<RiskContinent> continentList=new ArrayList<RiskContinent>();
	private ArrayList<RiskTerritory> terretoryList=new ArrayList<RiskTerritory>();
	private Map<String, List<String>> adjucencyMap=new HashMap<String, List<String>>();

	private boolean mapUploadStatus=false;	
	RiskMapBuilder riskMapBuilder;

	/**
	 * Load initial game data to before starting game.
	 * @param currentMapFile 
	 */
	public void loadMapData(List<String> MapFile) {
		ArrayList<String> parsedMapFile= new ArrayList<String>();
		for (String currLine : MapFile) {
			parsedMapFile.add(currLine.toLowerCase());
		}
		riskMapBuilder = new RiskMapBuilder(); 
		continentList=riskMapBuilder.addContinents(parsedMapFile);
		terretoryList=riskMapBuilder.addTerretories(parsedMapFile);
		continentList=riskMapBuilder.addTerretoriesToContinents(continentList,terretoryList); 
		adjucencyMap=riskMapBuilder.buildAdjucencyMap(terretoryList);
	}
	
	

	/**
     * Parse map file and create arraylist to store it.
	 * @param mapFilePath 
     * @return mapFileList list of strings with each line in map file.
     */
	public ArrayList<String> parseMapFile(String mapFilePath) {
		ArrayList<String> mapFileList = new ArrayList<String>();
		ArrayList<String> processedMapFileList = new ArrayList<String>();
		try {
			mapFileList = (ArrayList<String>) Files.readAllLines(Paths.get(mapFilePath), StandardCharsets.UTF_8);
			for (String currLine : mapFileList) {
				processedMapFileList.add(currLine.replaceAll("\\s", ""));
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
		int startIndex = 0;
		int endIndex = 0;
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
     * @return addedTerretoryList list of Territory objects.
     */
	public ArrayList<RiskTerritory> addTerretories(List<String> mapFileList) {
		ArrayList<RiskTerritory> addedTerretoryList;
		int startIndex = 0;
		int endIndex = 0;
		RiskTerritory riskTerritory;
		int idCounter=0;
		
		startIndex=mapFileList.indexOf("[territories]")+1;
		endIndex=mapFileList.indexOf(";;")-1;
		addedTerretoryList=new ArrayList<RiskTerritory>();
		for(int i=startIndex;i<=endIndex;i++) {
			String[] parsedTerritory=mapFileList.get(i).split(",");
				riskTerritory=new RiskTerritory(parsedTerritory);
				riskTerritory.setTerritoryId(idCounter);
				addedTerretoryList.add(riskTerritory);
				idCounter++;
		}
		return addedTerretoryList;
	}
	
	/**
     * Adding territories to continents.
     * @param continentList list of continents
     * @param territoryList list of territories
     * @return loadedContinentList list of continents with territory objects.
     */
	private ArrayList<RiskContinent> addTerretoriesToContinents(ArrayList<RiskContinent> continentList, List<RiskTerritory> territoryList) {
		ArrayList<RiskContinent> loadedContinentList = continentList;
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
		return loadedContinentList;
	}

	
	
	/**
	 * @param terretoryList list of territories
	 * @return loadedAdjMap 
	 */
	private Map<String, List<String>> buildAdjucencyMap(ArrayList<RiskTerritory> terretoryList) {
		Map<String, List<String>> loadedAdjMap=new HashMap<String, List<String>>();
		
		for (RiskTerritory currentTerritory : terretoryList) {
			loadedAdjMap.put(currentTerritory.getTerritoryName(), currentTerritory.getAdjacents());
		}
		return loadedAdjMap;
	}
	

	/**
	 * @return terretoryList 
	 */
	public ArrayList<RiskTerritory> getTerritoryList(){
	
		return terretoryList;
	}
	

	/**
	 * @return continentList 
	 */
	public ArrayList<RiskContinent> getContinentList(){
		
		return continentList;
	}
	

	/**
	 * @return adjucencyMap 
	 */
	public Map<String, List<String>> getAdjucencyMap(){
		
		return adjucencyMap;
	}


	/**
	 * @return the mapUploadStatus
	 */
	public boolean getMapUploadStatus() {
		return mapUploadStatus;
	}


	/**
	 * @param mapUploadStatus the mapUploadStatus to set
	 */
	public void setMapUploadStatus(boolean mapUploadStatus) {
		this.mapUploadStatus = mapUploadStatus;
	}


	public int getIdByTerritoryName(String riskTerritory) {
		int idForTerritory=0;
		for (RiskTerritory currTerritory : terretoryList) {
			if(riskTerritory.equalsIgnoreCase(currTerritory.getTerritoryName())) {
				idForTerritory=currTerritory.getTerritoryId();
			}
		}
		
		return idForTerritory;		
	}

}
