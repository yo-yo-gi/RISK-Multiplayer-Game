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
import com.soen.risk.validator.RiskMapValidator;

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

//	private String mapFilePath = "D:\\riskProject\\EarthMap.txt";
	private ArrayList<RiskContinent> continentList=new ArrayList<RiskContinent>();
	private ArrayList<RiskTerritory> terretoryList=new ArrayList<RiskTerritory>();
	private Map<String, List<String>> adjucencyMap=new HashMap<String, List<String>>();
//	static boolean isMapSyntaxValid;
//	static RiskMapValidator riskMapValidator=new RiskMapValidator();
	private boolean mapUploadStatus=false;
	
	RiskMapBuilder riskMapBuilder;
	
	/*
	 * public static void main(String[] args) { 
	 * riskMapBuilder = new RiskMapBuilder(); 
	 * List<String> parsedMapFile=riskMapBuilder.parseMapFile("D:\\riskProject\\EarthMap.txt");
	 * isMapSyntaxValid = riskMapValidator.validateMapSyntax(parsedMapFile);
	 * continentList=riskMapBuilder.addContinents(parsedMapFile);
	 * terretoryList=riskMapBuilder.addTerretories(parsedMapFile);
	 * riskMapValidator.validateMap(parsedMapFile);
	 * continentList=riskMapBuilder.addTerretoriesToContinents(continentList,terretoryList); 
	 * adjucencyMap=riskMapBuilder.buildAdjucencyMap(terretoryList);
	 * 
	 * }
	 */
	

	/**
	 * Load initial game data to before starting game.
	 */
	public void loadMapData() {
		riskMapBuilder = new RiskMapBuilder(); 
		List<String> parsedMapFile=riskMapBuilder.parseMapFile("D:\\riskProject\\EarthMap.txt");
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
	public List<String> parseMapFile(String mapFilePath) {
		List<String> mapFileList = new ArrayList<String>();
		try {
			mapFileList = Files.readAllLines(Paths.get(mapFilePath), StandardCharsets.UTF_8);
			setMapUploadStatus(true);
		} catch (IOException e) {
			System.out.println("Error while reading map file.");
			e.printStackTrace();
		}
		return mapFileList;
	}
	
	/**
     * Parse map file and initializes the continents.
     * @param mapFileList line by line list of map file
     * @return addedContinentList list of Continents objects.
     */
	private ArrayList<RiskContinent> addContinents(List<String> mapFileList) {		
		ArrayList<RiskContinent> addedContinentList;
		int startIndex = 0;
		int endIndex = 0;
		RiskContinent riskContinent;
		
		startIndex=mapFileList.indexOf("[Continents]")+1;
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
	private ArrayList<RiskTerritory> addTerretories(List<String> mapFileList) {
		ArrayList<RiskTerritory> addedTerretoryList;
		int startIndex = 0;
		int endIndex = 0;
		RiskTerritory riskTerritory;
		
		
		startIndex=mapFileList.indexOf("[Territories]")+1;
		endIndex=mapFileList.indexOf(";;")-1;
		addedTerretoryList=new ArrayList<RiskTerritory>();
		for(int i=startIndex;i<=endIndex;i++) {
			String[] parsedTerritory=mapFileList.get(i).split(",");
//			if(riskMapValidator.validateContinent(parsedTerritory[1])) {
				riskTerritory=new RiskTerritory(parsedTerritory);
				addedTerretoryList.add(riskTerritory);
//			}
//			else
//				System.out.println("Invalid Continent added for country : "+parsedTerritory[0]);
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




}
