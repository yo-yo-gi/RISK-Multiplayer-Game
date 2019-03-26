/*
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * <h2>User Map Editor</h2>
 * This class is used for editing the user created map,
 * where user is given an option to Add or Delete continents or territories,
 * appropriate input is taken and continents/territories are deleted from the map file
 * and validated before uploading the map for the game.
 *
 * @author Shashank Rao
 * @version 1.0
 */
public class RiskMapEditor {

	/** The scanner. */
	Scanner scanner = new Scanner(System.in);

	/** The fullMap. */
	ArrayList<String> fullmap=new ArrayList<String>();

	/** The Map 1. */
	ArrayList<String> Map1=new ArrayList<String>();

	/** The Map 2. */
	ArrayList<String> Map2=new ArrayList<String>();

	/** The Map 3. */
	ArrayList<String> Map3=new ArrayList<String>();

	/** The Map 4. */
	ArrayList<String> Map4=new ArrayList<String>();

	/** The Map 5. */
	ArrayList<String> Map5=new ArrayList<String>();


	/** The territory list. */
	ArrayList<String> territoryList=new ArrayList<String>();

	/** The continent list. */
	ArrayList<String> continentList=new ArrayList<String>();

	/** The index. */
	int TerrStartIndex=Constants.ZERO, TerrEndIndex=Constants.ZERO, contiStartIndex=Constants.ZERO, 
			contEndIndex=Constants.ZERO,startIndexMap1=Constants.ZERO,endIndexMap1=Constants.ZERO,
			startIndexMap3=Constants.ZERO,endIndexMap3=Constants.ZERO,index=Constants.ZERO;

	/**
	 * <h2>User Map Editor Constructor</h2>
	 * The constructor is called when an object of the RiskMapEditor class is created.
	 * The constructor is responsible to parse the passed map file into different ArrayLists containing the continents and territories
	 * @param tempMapFile Contains the entire map.txt file in ArrayList format used for parsing into continent and territory list. 
	 *
	 */

	public RiskMapEditor(List<String> tempMapFile) {

		ArrayList<String> mapFile=new ArrayList<String>();
		for (String currLine : tempMapFile) {
			mapFile.add(currLine.toLowerCase());
		}
		for (String currLine : mapFile) {
			if (currLine.equalsIgnoreCase("[Territories]")) {
				TerrStartIndex=mapFile.indexOf(currLine)+1;
			}
			if (currLine.equalsIgnoreCase(";;")) {
				TerrEndIndex=mapFile.indexOf(currLine)-1;
			}
			if (currLine.equalsIgnoreCase("[Continents]")) {
				contiStartIndex=mapFile.indexOf(currLine)+1;
			}
			if (currLine.equalsIgnoreCase("-")) {
				contEndIndex=mapFile.indexOf(currLine)-1;
			}
		}

		for (int i = TerrStartIndex; i <= TerrEndIndex; i++) {
			territoryList.add(mapFile.get(i));
		}
		for (int i = contiStartIndex; i <= contEndIndex; i++) {
			continentList.add(mapFile.get(i));
		}
		for (String s : mapFile) {
			if(s.equalsIgnoreCase("[Map]"))
				startIndexMap1=mapFile.indexOf(s);
			if(s.equalsIgnoreCase("[Continents]"))
				endIndexMap1=mapFile.indexOf(s);
		}
		for(int i=startIndexMap1;i<=endIndexMap1;i++)
			Map1.add(mapFile.get(i));

		for (String s : mapFile) {
			if(s.equalsIgnoreCase("-"))
				startIndexMap3=mapFile.indexOf(s);
			if(s.equalsIgnoreCase("[Territories]"))
				endIndexMap3=mapFile.indexOf(s);
		}
		for(int i=startIndexMap3;i<=endIndexMap3;i++)
			Map3.add(mapFile.get(i));
		for (String s : mapFile) {
			if(s.equalsIgnoreCase(";;"))
				index=mapFile.indexOf(s);			
		}
		Map5.add(mapFile.get(index));

	}


	//		public ArrayList<String> editMap(ArrayList<String> mapList, ArrayList<String> continentList, ArrayList<String> territoryList) {
	/**
	 * <h2>Implementation of Edit Map functionality</h2>
	 * The method editMap() is used for interaction with user.
	 * The method takes input from user on addition/deletion of continents or territories.
	 * 
	 */

	public void editMap() {


		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		int choiceofEdit =Constants.ZERO;
		int choiceofAdd=Constants.ZERO;
		while (flag1) {
			System.out.println("Please select an option from below:");
			System.out.println("1. ADD");
			System.out.println("2. DELETE");
			System.out.println("3. EXIT");
			do {
				while (!scanner.hasNextInt()) {
					System.out.println("Try Again!!");
					scanner.next();
				}
				choiceofEdit = scanner.nextInt();
				if(choiceofEdit>3 || choiceofEdit<1) {
					System.out.println("Try Again!!");
				}
			}while(choiceofEdit>3 || choiceofEdit<1);			

			switch (choiceofEdit){
			case 1:
				System.out.println("Add selected");
				while(flag2){
					System.out.println("Please select what you want to add from the options below:");
					System.out.println("1. Continent");
					System.out.println("2. Territory");
					System.out.println("3. Exit");
					do {
						while (!scanner.hasNextInt()) {
							System.out.println("Try Again!!");
							scanner.next();
						}
						choiceofAdd=scanner.nextInt();
						if(choiceofAdd>3 || choiceofAdd<1) {
							System.out.println("Try Again!!");
						}
					}while(choiceofAdd>3 || choiceofAdd<1);
					switch (choiceofAdd){
					case 1:
						System.out.println("Add continent selected");
						System.out.println();
						System.out.println("Add continent name in the format:<Continent Name>=<Control Value>");
						String continent=scanner.next();
						addContinent(continent);

						break;
					case 2:
						System.out.println("Add terrritory selected");
						System.out.println();
						System.out.println("Enter the territory details in the format:<TerritoryName,ContinentName,AdjacentCountry1,AdjacentCountry2...AdjacentCountryN>");
						String territory=scanner.next();
						addTerritory(territory);
						break;
					case 3:

						flag2=false;

						break;
					default:
						System.out.println("Invalid input");
					}
				}
				break;
			case 2:
				System.out.println("Delete selected");
				while(flag3){
					System.out.println("Please select what you want to delete from the options below:");
					System.out.println("1. Continent");
					System.out.println("2. Territory");
					System.out.println("3. Exit");
					do {
						while (!scanner.hasNextInt()) {
							System.out.println("Try Again!!");
							scanner.next();
						}
						choiceofAdd=scanner.nextInt();
						if(choiceofAdd>3 || choiceofAdd<1) {
							System.out.println("Try Again!!");
						}
					}while(choiceofAdd>3 || choiceofAdd<1);	

					switch (choiceofAdd){
					case 1:
						System.out.println("Delete continent selected");
						System.out.println("Enter the continent you want to remove:");
						String removeContinent=scanner.next();

						deleteContinent(removeContinent);
						break;
					case 2:
						System.out.println("Delete terrritory selected");
						System.out.println("Enter the name of territory you wish to delete:");
						String territoryToRemove=scanner.next();
						deleteTerritory(territoryToRemove);
						break;
					case 3:
						flag3=false;

						break;
					default:
						System.out.println("Invalid input");
					}
				}
				break;
			case 3:
				fullMapList();
				flag1=false;
				break;
			default:
				System.out.println("Invalid input");
			}


		}

	}

	/**
	 * <h2>Concatenation of ArrayList</h2>
	 * The fullMapList() method is used to create a concatenated list of ArrayList.
	 * 
	 */

	public void fullMapList() {
		Map2=(ArrayList<String>) getContinentList();
		Map4=(ArrayList<String>) getTerritoryList();

		fullmap.addAll(Map1);
		fullmap.addAll(Map2	);
		fullmap.addAll(Map3);
		fullmap.addAll(Map4);
		fullmap.addAll(Map5);
	}


	/**
	 * Gets the continent list.
	 *
	 * @return the continent list
	 */
	public List<String> getContinentList(){
		return continentList;
	}

	/**
	 * Gets the territory list.
	 *
	 * @return the territory list
	 */
	public List<String> getTerritoryList(){
		return territoryList;
	}

	/**
	 * Gets the full map.
	 *
	 * @return the full map
	 */
	public List<String> getFullMap(){
		return fullmap;
	}

	/**
	 * Adds the territory.
	 *
	 * @param territory the territory
	 */
	public void addTerritory(String territory) {

		territoryList.add(territory);

	}

	/**
	 * Adds the continent.
	 *
	 * @param continent the continent
	 */
	public void addContinent(String continent) {

		continentList.add(continent);

	}

	/**
	 * <h2>Logic to delete a territory from the territory ArrayList</h2>.
	 *
	 * @param deleteTerritory the delete territory
	 */

	public void deleteTerritory(String deleteTerritory) {
		ArrayList<String> deletedTerritoryList=new  ArrayList<String>(territoryList);
		int indexOfterritory=-1;
		boolean flag=false;
		ArrayList<String> adjList;
		int indexOfAdjacent=-1;
		ArrayList<String> currTerritoryList;

		for (String currTerritory : territoryList) {
			indexOfterritory=-1;
			if((currTerritory.split(",")[0]).equalsIgnoreCase(deleteTerritory)) {
				indexOfterritory=territoryList.indexOf(currTerritory);
				deletedTerritoryList.remove(indexOfterritory);
				flag=true;
			}			
		}	
		territoryList=deletedTerritoryList;

		for (String currTerritory : territoryList) {
			adjList=new ArrayList<String>(Arrays.asList((currTerritory.split(",",3)[2]).split(",")));
			currTerritoryList=new ArrayList<String>(Arrays.asList(currTerritory.split(",")));
			for (String currAdjucency : adjList) {
				indexOfAdjacent=-1;
				if (currAdjucency.equalsIgnoreCase(deleteTerritory)) {
					indexOfAdjacent=currTerritoryList.indexOf(deleteTerritory);
					currTerritoryList.remove(indexOfAdjacent);
					deletedTerritoryList.set(territoryList.indexOf(currTerritory), String.join(",", currTerritoryList));
					flag=true;
				}					
			}

		}						
		if (flag) {
			//		continentList=deletedContinentList;
			territoryList=deletedTerritoryList;
			System.out.println("Deleted successfully");
		}else System.err.println("Territory not found. Please check again.");

		territoryList=deletedTerritoryList;

	}

	/**
	 * <h2>Logic to delete a continent from the continent ArrayList and territory ArrayList</h2>.
	 *
	 * @param continentToRemove the continent to remove
	 */

	/*Logic to delete a continent from the continent ArrayList and territory ArrayList-START*/


	public void deleteContinent(String continentToRemove) {
		ArrayList<String> deletedContinentList=new  ArrayList<String>(continentList);
		ArrayList<String> deletedTerritoryList=new  ArrayList<String>(territoryList);
		int indexOfContinent=-1;
		int indexOfTerritory=-1;

		boolean deletedFlag=false;
		ArrayList<String> tempContinentList;
		ArrayList<String> tempTerritoryList;

		tempContinentList=new ArrayList<String>(deletedContinentList);
		for (String currContinent : deletedContinentList) {
			indexOfContinent=-1;
			if((currContinent.split("=")[0]).equalsIgnoreCase(continentToRemove)) {

				indexOfContinent=tempContinentList.indexOf(currContinent);
				if(indexOfContinent>=0) {
					deletedFlag=true;
					tempContinentList.remove(indexOfContinent);
				}
			}

		}

		deletedContinentList=tempContinentList;

		if(deletedFlag) {
			tempTerritoryList=new ArrayList<String>(deletedTerritoryList);
			for (String currTerritory : deletedTerritoryList) {
				indexOfTerritory=-1;
				if((currTerritory.split(",")[1]).equalsIgnoreCase(continentToRemove)) {
					indexOfTerritory=tempTerritoryList.indexOf(currTerritory);
					if(indexOfTerritory>=0)
						tempTerritoryList.remove(indexOfTerritory);
				}

			}
			deletedTerritoryList=tempTerritoryList;
		}

		if (deletedFlag) {
			continentList=deletedContinentList;
			territoryList=deletedTerritoryList;
			System.out.println("Deleted successfully");
		}else System.err.println("Continent not found. Please check again.");


	}



}
