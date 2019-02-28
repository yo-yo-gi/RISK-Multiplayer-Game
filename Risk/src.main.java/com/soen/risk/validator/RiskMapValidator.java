/**
 * 
 */
package com.soen.risk.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.soen.risk.controller.RiskMapBuilder;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2>Map File Validator</h2>
 * <ul>
 * <li>Check for wrong syntax of map file
 * <li>Check for wrong syntax of territory
 * <li>Check for wrong syntax of continent
 * <li>Check for duplicate territory
 * <li>Check for duplicate continent
 * <li>Check for duplicate adjacent territory
 * <li>Check for missing continent
 * <li>Check for connectivity of territory graph
 * </ul>
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0.0
 * @since 2019-02-12
 */
public class RiskMapValidator {
	

	
	private ArrayList<RiskContinent> continentList=new ArrayList<RiskContinent>();
	private ArrayList<RiskTerritory> terretoryList=new ArrayList<RiskTerritory>();
	private Map<String, List<String>> adjucencyMap=new HashMap<String, List<String>>();
	RiskMapBuilder riskMapBuilder;
	boolean validation=false;
	int indexOfMap=0, indexOfContinents=0, indexOfDash=0, indexOfTerritories=0, indexOfDSColon=0;
	private ArrayList<String> initContinentList;
	private ArrayList<String> initTerritoryList;
	private ArrayList<String> initAdjucencyList=new ArrayList<String>();
	private int adjVertices; // No. of vertices 
	private LinkedList<Integer> adjList[]; //Adjacency List 
	private LinkedList<Integer> adjListTranspose[]; //Adjacency List for transpose DFS
	
	/**
	 * Main validation method
	 * 
	 * @param parsedMapFile list of parsed map file
	 * 
	 * @return true if parsedMapFile is valid
	 */
	public boolean validateMap(List<String> parsedMapFile) {		
		if(validateMapSyntax(parsedMapFile)) {
			if(validateDuplicacy(initContinentList, initTerritoryList)) {
			riskMapBuilder = new RiskMapBuilder(); 
			riskMapBuilder.loadMapData(parsedMapFile);
			continentList=riskMapBuilder.getContinentList();
			terretoryList=riskMapBuilder.getTerritoryList();
			adjucencyMap=riskMapBuilder.getAdjucencyMap();
		if(validateContinents(continentList)) {
			if(validateTerritories(terretoryList, adjucencyMap)) {			
				validation=true;
			}else {validation=false;}
		}else {validation=false;}
	}else {validation=false;}		
		
		}else {validation=false;}
		

		return validation;
	}
	
	
	/**
		 * @param parsedMapFile list of parsed map file
		 * 
		 * @return true if map syntax is valid
		 */
		public boolean validateMapSyntax(List<String> parsedMapFile) {
			boolean syntaxValidation=false;
			
	//		 Check for null file 
			if (7<parsedMapFile.size() || null!=parsedMapFile ||  !parsedMapFile.isEmpty() ) {			
				if (checkMustTags(parsedMapFile) ) {
					if (checkContinentSyntax(parsedMapFile)) {
						if (checkTerritorySyntax(parsedMapFile)) {
							syntaxValidation=true;
						} else syntaxValidation=false;
					} else syntaxValidation=false;
				}else syntaxValidation=false;				
			}
			
			return syntaxValidation;
		}


	/**
	 * Validation for file syntax for tags
	 * 
	 * @param parsedMapFile list of parsed map file
	 * 
	 * @return true if file is valid with tags
	 */
	private boolean checkMustTags(List<String> parsedMapFile) {
			boolean mustTagsValidation=false;			
			List<String> mustHaveTags=new ArrayList<String>(Arrays.asList("[Map]", "[Continents]","[Territories]","-",";;"));
	
	//		check for [Map], [Continents], [Territories],- and ;;
			if(parsedMapFile.containsAll(mustHaveTags)) {
				indexOfMap=parsedMapFile.indexOf("[Map]");
				indexOfContinents=parsedMapFile.indexOf("[Continents]");
				indexOfTerritories=parsedMapFile.indexOf("[Territories]");
				indexOfDash=parsedMapFile.indexOf("-");
				indexOfDSColon=parsedMapFile.indexOf(";;");
				int [] indexCompareArray= {indexOfMap,indexOfContinents,indexOfDash,indexOfTerritories,indexOfDSColon};
				int [] sortedArray={indexOfMap,indexOfContinents,indexOfDash,indexOfTerritories,indexOfDSColon};
				Arrays.sort(sortedArray);
				if(Arrays.equals(indexCompareArray, sortedArray)) {
					mustTagsValidation=true;
				}
			}else
			{
				mustTagsValidation=false;
			}
			return mustTagsValidation; 
		}


	/**
	 * Validation for file syntax for tags
	 * 
	 * @param parsedMapFile list of parsed map file
	 * 
	 * @return true continent syntax is valid
	 */
	private boolean checkContinentSyntax(List<String> parsedMapFile) {
			boolean continentValidation=false;			
			String currentContinent;
			initContinentList=new ArrayList<String>();
			
			for (int i=indexOfContinents+1;i<indexOfDash;i++) {
				currentContinent=parsedMapFile.get(i);
				String [] splitArray=currentContinent.split("=");
				if(splitArray.length==2) {
	//				checking for name_of_continent must be string and control value must be +ve integer
					if(splitArray[1].matches("^[1-9]+[0-9]*$") && splitArray[0].matches("^[a-zA-Z]+(([\\-\\_][a-zA-Z])?[a-zA-Z]*)*$")) {
						initContinentList.add(splitArray[0]);
						continentValidation=true;
					}
					else { continentValidation=false; break;}
					
				}else { continentValidation=false; break;}
				
			}
			
			return continentValidation;
		}


	/**
		 * Validation for Territories
		 * 
		 * @param adjMap adjacency map
		 * @param terretoryList list of territories
		 * 
		 * @return true if territories are connected
		 */
		private boolean validateTerritories(ArrayList<RiskTerritory> territoryList, Map<String, List<String>> adjMap) {
			boolean territoryValidation=false;
			int matchCounter=0;
			for (String key : adjMap.keySet()) {
				matchCounter=0;
			    for (String currAdj : adjMap.get(key)) {			    		
			    	  for (String newAdj :adjMap.get(currAdj)){			    		  
			    		  if (newAdj.equalsIgnoreCase(key)) {
			    			  matchCounter++;
						}
			    	  }			    	  
				}
			    if (!(matchCounter==adjMap.get(key).size())) {
			    	territoryValidation=false;
					break;
				}else territoryValidation=true;
			}
			
			if(territoryValidation) {
	//		graph initialize 
			initializeAdj(territoryList.size());
	//		adding edges in graph
			for (Entry<String, List<String>> entry : adjMap.entrySet())
			{	
				for (String currAdjTerritory : entry.getValue()) {
					addEdge(riskMapBuilder.getIdByTerritoryName(entry.getKey()), riskMapBuilder.getIdByTerritoryName(currAdjTerritory));
				}
			}
	//		checking if connected or not
			territoryValidation=checkTerritoryAdjucency();
			}
			return territoryValidation;
		}


	/**
	 * Validate territory and continent duplicate
	 * 
	 * @param territories list of territories
	 * @param continents list of continent
	 * 
	 * @return true if no duplicate territory or continent
	 */
	private boolean validateDuplicacy(ArrayList<String> continents, ArrayList<String> territories) {
		boolean duplicacyValidation=false;
		int continentCounter=0;
		int territoryCounter=0;
		int adjucentCounter=0;
		int continentCorrectCount=0;
//		name of territory and name of continent should not be same
		for (String currTerritory : territories) {
			for (String currContinent : continents) {
				if ((currTerritory.split(",")[0]).equalsIgnoreCase(currContinent)) {
					duplicacyValidation=false;
					break;
				}else {duplicacyValidation=true;}
			}
			if(duplicacyValidation==false) break;
			initAdjucencyList=new ArrayList<String>();
			initAdjucencyList.addAll(Arrays.asList((currTerritory.split(",",3)[2]).split(",")));
			for (String currAdjucent : initAdjucencyList) {
				for (String currContinent : continents) {
					if(currAdjucent.equalsIgnoreCase(currContinent)){
						duplicacyValidation=false;
						break;
						}else {duplicacyValidation=true;}
				}		
				if(duplicacyValidation==false) break;
			}
			if(duplicacyValidation==false) break;
		}
		
		if(duplicacyValidation) {
//			there can not be more than 1 continent with same name
			for (String currContinent : continents) {
				continentCounter=0;
				for (String anotherContinent : continents) {					
					if(currContinent.equalsIgnoreCase(anotherContinent))
						continentCounter++;
				}
				if(continentCounter>2) {duplicacyValidation=false; break;}
			}
//			there can not be more than 1 territory with same name
			for (String currTerritory : territories) {
				territoryCounter=0;
				for (String anotheTerritory : territories) {
					if(currTerritory.split(",")[0].equalsIgnoreCase(anotheTerritory.split(",")[0]))
						territoryCounter++;
				}
				if(territoryCounter>2) {duplicacyValidation=false; break;}
			}
//			there can not be more than 1 adjacent territory with same name
			for (String currTerritory : territories) {
				initAdjucencyList=new ArrayList<String>();
				initAdjucencyList.addAll(Arrays.asList((currTerritory.split(",",3)[2]).split(",")));
				for (String currAdjucent : initAdjucencyList) {
					adjucentCounter=0;
					for (String anotherAdjucent : initAdjucencyList) {
						if(currAdjucent.equalsIgnoreCase(anotherAdjucent))
							adjucentCounter++;
					}if(adjucentCounter>2) {duplicacyValidation=false; break;}					
				}if(adjucentCounter>2) {duplicacyValidation=false; break;}
			}
			
//			continent name in territory should be present in continent list
			for (String currTerritory : territories) {
				continentCorrectCount=0;
				for (String currContinent : continents) {
					if ((currTerritory.split(",")[1]).equalsIgnoreCase(currContinent)) {
						continentCorrectCount++;
					}
				}if(continentCorrectCount!=1) {duplicacyValidation=false; break;}
			}
			
			if(continentCounter<2 && territoryCounter<2 && adjucentCounter<2 && continentCorrectCount==1) {
				duplicacyValidation=true;
			}else {duplicacyValidation=false;}
		}
		
		return duplicacyValidation;
	}

	/**
	 * Validation for continents
	 * 
	 * @param continentList list of continent
	 * 
	 * @return true if continents are valid
	 */
	public boolean validateContinents(ArrayList<RiskContinent> continentList){
		boolean ContinentValidation=true;
//		need to think about this
		return ContinentValidation;
	}


	/**
	 * Validation for file syntax for tags
	 * 
	 * @param parsedMapFile list of parsed map file
	 * 
	 * @return true territory syntax is valid
	 */	
	private boolean checkTerritorySyntax(List<String> parsedMapFile) {
			boolean territoryValidation=false;
			initTerritoryList=new ArrayList<String>();
			String currentTerritory;
			
			
			for (int i=indexOfTerritories+1;i<indexOfDSColon;i++) {
				currentTerritory=parsedMapFile.get(i);
				String [] splitArray=currentTerritory.split(",");
	//			checking that each territory line contains minimum 3 words
				if(splitArray.length>=3 ) {
					initTerritoryList.add(currentTerritory);
					territoryValidation=true;
	//				need to write further validation if required
					
				}else { territoryValidation=false; break;}
				
			}
			
			return territoryValidation;
		}


	/**
	 * Initializing adjacency
	 * 
	 * @param v total vertices in graph
	 * 
	 */	
		public void initializeAdj(int v) 
		{ 
			adjVertices = v; 
			adjList = new LinkedList[v]; 
			for (int i=0; i<v; ++i) 
				adjList[i] = new LinkedList<Integer>(); 
		} 
		
	
		/**
		 * Function to add an edge into the graph 
		 * 
		 * @param v current vertex in graph
		 * @param w next vertex in graph to draw edge
		 */	
		void addEdge(int v,int w) { 
			adjList[v].add(w); 
			} 
		
		
		/**
		 * A recursive function to print DFS starting from v 
		 * 
		 * @param v current vertex in graph
		 * @param visited array of visited vertices
		 */	
		void DFSUtil(int v,Boolean visited[]) 
		{ 
			// Mark the current node as visited and print it 
			visited[v] = true; 
			int n; 
			// Recur for all the vertices adjacent to this vertex 
			Iterator<Integer> i = adjList[v].iterator(); 
			while (i.hasNext()) 
			{ 
				n = i.next(); 
				if (!visited[n]) 
					DFSUtil(n,visited); 
			} 
		} 
		
		/**
		 * A recursive function to print DFS starting from v 
		 * 
		 * @param v current vertex in graph
		 * @param visited array of visited vertices
		 */	
		public void DFSUtilTranspose(int v,Boolean visited[]) 
		{ 
			// Mark the current node as visited and print it 
			visited[v] = true; 

			int n; 

			// Recur for all the vertices adjacent to this vertex 
			Iterator<Integer> i = adjListTranspose[v].iterator(); 
			while (i.hasNext()) 
			{ 
				n = i.next(); 
				if (!visited[n]) 
					DFSUtil(n,visited); 
			} 
		} 

	
		/**
		 * Initializing transpose of adjacency matrix
		 * 
		 * @param v current vertex in graph
		 */	
		public void initializeAdjTranspose(int v) 
		{ 
			adjVertices = v; 
			adjListTranspose = new LinkedList[v]; 
			for (int i=0; i<v; ++i) 
				adjListTranspose[i] = new LinkedList<Integer>(); 
			
			for (int i = 0; i < adjVertices; i++) 
			{ 
				// Recur for all the vertices adjacent to this vertex 
				Iterator<Integer> j = adjList[i].listIterator(); 
				while (j.hasNext()) 
					adjListTranspose[j.next()].add(i); 
			} 
			
		} 
	
	
		/**
		 * The main function to check adjacency  
		 * 
		 * @return returns true if graph is strongly connected 
		 */
		Boolean checkTerritoryAdjucency() 
		{ 
			// Step 1: Mark all the vertices as not visited 
			// (For first DFS) 
			Boolean visited[] = new Boolean[adjVertices]; 
			for (int i = 0; i < adjVertices; i++) 
				visited[i] = false; 

			// Step 2: Do DFS traversal starting from first vertex. 
			DFSUtil(0, visited); 

			// If DFS traversal doesn't visit all vertices, then 
			// return false. 
			for (int i = 0; i < adjVertices; i++) 
				if (visited[i] == false) 
					return false; 

			// Step 3: Create a reversed graph 
			initializeAdjTranspose(adjVertices);

			// Step 4: Mark all the vertices as not visited (For second DFS) 
			for (int i = 0; i < adjVertices; i++) 
				visited[i] = false; 

			// Step 5: Do DFS for reversed graph starting from 
			// first vertex. Staring Vertex must be same starting 
			// point of first DFS 
			DFSUtilTranspose(0, visited); 

			// If all vertices are not visited in second DFS, then 
			// return false 
			for (int i = 0; i < adjVertices; i++) 
				if (visited[i] == false) 
					return false; 

			return true; 
		} 

}
