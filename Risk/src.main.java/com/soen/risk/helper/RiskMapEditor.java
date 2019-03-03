package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * <h2>User Map Editor</h2>
 * <ul>
 * <li>Editing of user created map.
 * <li>User is given an option to Add or Delete continents or territories.
 * <li>Appropriate input is taken and continents/territories are deleted from the map file and validated before uploading the map for the game.
 *
 * </ul>
 *
 * @author Shashank Rao
 * @version 1.0.0
 */
public class RiskMapEditor {
	Scanner scanner = new Scanner(System.in);
	ArrayList<String> fullmap=new ArrayList<String>();
	ArrayList<String> Map1=new ArrayList<String>();
	ArrayList<String> Map2=new ArrayList<String>();
	ArrayList<String> Map3=new ArrayList<String>();
	ArrayList<String> Map4=new ArrayList<String>();
	ArrayList<String> Map5=new ArrayList<String>();


	ArrayList<String> territoryList=new ArrayList<String>();
	ArrayList<String> continentList=new ArrayList<String>();
	int TerrStartIndex=Constants.ZERO, TerrEndIndex=Constants.ZERO, contiStartIndex=Constants.ZERO, 
			contEndIndex=Constants.ZERO,startIndexMap1=Constants.ZERO,endIndexMap1=Constants.ZERO,
			startIndexMap3=Constants.ZERO,endIndexMap3=Constants.ZERO,index=Constants.ZERO;

	/**
	 * <h2>User Map Editor Constructor</h2>
	 * 
	 * <li>The constructor is called when an object of the RiskMapEditor class is created.
	 * <li>The constructor is responsible to parse the passed map file into different ArrayLists containing the continents and territories
	 * @param mapFile Contains the entire map.txt file in ArrayList format used for parsing into continent and territory list. 
	 *
	 */

	public RiskMapEditor(List<String> mapFile) {

		for (String currLine : mapFile) {
			if (currLine.equalsIgnoreCase("[Territories]")) {
				TerrStartIndex=mapFile.indexOf("[Territories]")+1;
			}
			if (currLine.equalsIgnoreCase(";;")) {
				TerrEndIndex=mapFile.indexOf(";;")-1;
			}
			if (currLine.equalsIgnoreCase("[Continents]")) {
				contiStartIndex=mapFile.indexOf("[Continents]")+1;
			}
			if (currLine.equalsIgnoreCase("-")) {
				contEndIndex=mapFile.indexOf("-")-1;
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


	//	public ArrayList<String> editMap(ArrayList<String> mapList, ArrayList<String> continentList, ArrayList<String> territoryList) {
	/**
	 * <h2>Implementation of Edit Map functionality</h2>
	 * <li> The method editMap() is used for interaction with user.
	 * <li> The method takes input from user on addition/deletion of continents or territories.
	 * @return A edited arrayList containing the updates requested by the user.
	 * 
	 */
	public void editMap() {

		//		ArrayList<String> continent=new ArrayList<String>();
		//		ArrayList<String> territory=new ArrayList<String>();
		//		continent=continentList;
		//		territory=territoryList;
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		while (flag1) {
			System.out.println("Please select an option from below:");
			System.out.println("1. ADD");
			System.out.println("2. DELETE");
			System.out.println("3. EXIT");
			while (!scanner.hasNextInt()) {
		 		System.out.println("Try Again!!");
		 		scanner.next();
		 	}
			int choiceofEdit = scanner.nextInt();
			switch (choiceofEdit){
			case 1:
				System.out.println("Add selected");
				while(flag2){
					System.out.println("Please select what you want to add from the options below:");
					System.out.println("1. Continent");
					System.out.println("2. Territory");
					System.out.println("3. Exit");
					while (!scanner.hasNextInt()) {
				 		System.out.println("Try Again!!");
				 		scanner.next();
				 	}
					while (!scanner.hasNextInt()) {
				 		System.out.println("Try Again!!");
				 		scanner.next();
				 	}
					int choiceofAdd=scanner.nextInt();
					switch (choiceofAdd){
					case 1:
						System.out.println("Add continent selected");
						System.out.println();
						System.out.println("Add continent name in the format:<Continent Name>=<Control Value>");
						while (!scanner.hasNextInt()) {
					 		System.out.println("Try Again!!");
					 		scanner.next();
					 	}
						String continent=scanner.next();
						addContinent(continent);
						//                                continentList.add(continent);
						break;
					case 2:
						System.out.println("Add terrritory selected");
						System.out.println();
						System.out.println("Enter the territory details in the format:<TerritoryName,ContinentName,AdjacentCountry1,AdjacentCountry2...AdjacentCountryN>");
						while (!scanner.hasNextInt()) {
					 		System.out.println("Try Again!!");
					 		scanner.next();
					 	}
						String territory=scanner.next();
						addTerritory(territory);
						//                                territoryList.add(territory);
						break;
					case 3:
						//                            	continentList.add(continentList.size(),"-");
						//                                territoryList.add(territoryList.size(),"-");
						flag2=false;
						//                                System.out.println(continentList);
						//                                System.out.println(territoryList);
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
					while (!scanner.hasNextInt()) {
				 		System.out.println("Try Again!!");
				 		scanner.next();
				 	}
					int choiceofAdd=scanner.nextInt();
					switch (choiceofAdd){
					case 1:
						System.out.println("Delete continent selected");
						System.out.println("Enter the continent you want to remove:");
						String removeContinent=scanner.next();
						//                                DeleteContinent(continentList,territoryList,removeContinent);
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
						//                                System.out.println(continentList);
						//                                System.out.println(territoryList);
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
		//        newMap.addAll(mapList);
		//        newMap.addAll(continentList);
		//        newMap.addAll(territoryList);

		//		return newMap;
	}

	/**
	 * <h2>Concatenation of ArrayList/h2>
	 * <li> The fullMapList() method is used to create a concatenated list of ArrayList.
	 * @return A concatenated ArrayList containing the elements of map.
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

		//return fullmap;
	}


	public List<String> getContinentList(){
		return continentList;
	}

	public List<String> getTerritoryList(){
		return territoryList;
	}

	public List<String> getFullMap(){
		return fullmap;
	}


	public void addTerritory(String territory) {

		territoryList.add(territory);

	}

	public void addContinent(String continent) {

		continentList.add(continent);

	}

	/**
	 * <h2>Logic to delete a territory from the territory ArrayList</h2>
	 * 
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
//				continentList=deletedContinentList;
				territoryList=deletedTerritoryList;
				System.out.println("Deleted successfully");
			}else System.err.println("Territory not found. Please check again.");

		territoryList=deletedTerritoryList;
		
	}

	/**
	 * <h2>Logic to delete a continent from the continent ArrayList and territory ArrayList</h2>
	 * 
	 */

	/*Logic to delete a continent from the continent ArrayList and territory ArrayList-START*/
	//private void DeleteContinent(ArrayList<String> continentList, ArrayList<String> territoryList , String continentToRemove) {

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
			//			else {System.out.println("Continent not found.");}
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
				//			else {System.out.println("Continent not found.");}
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
