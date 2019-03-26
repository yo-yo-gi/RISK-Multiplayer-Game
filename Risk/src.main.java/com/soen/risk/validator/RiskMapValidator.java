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
import com.soen.risk.helper.Constants;
import com.soen.risk.model.RiskContinent;
import com.soen.risk.model.RiskTerritory;

/**
 * <h2>Map File Validator</h2>
 * This class is used to check for wrong syntax of map file,
 * check for wrong syntax of territory,
 * check for wrong syntax of continent,
 * check for duplicate territory,
 * check for duplicate continent,
 * check for duplicate adjacent territory,
 * check for missing continent, and
 * check for connectivity of territory graph.
 *
 * @author Yogesh Nimbhorkar
 * @author Chirag Vora
 * @version 2.0
 */
public class RiskMapValidator {

	/** The continent list. */
	private ArrayList<RiskContinent> continentList=new ArrayList<RiskContinent>();

	/** The territory list. */
	private ArrayList<RiskTerritory> territoryList=new ArrayList<RiskTerritory>();

	/** The adjacency map. */
	private Map<String, List<String>> adjacencyMap=new HashMap<String, List<String>>();

	/** The risk map builder. */
	RiskMapBuilder riskMapBuilder;

	/** The validation. */
	boolean validation=false;

	/** The index of DS colon. */
	int indexOfMap=Constants.ZERO, indexOfContinents=Constants.ZERO, indexOfDash=Constants.ZERO, indexOfTerritories=Constants.ZERO, indexOfDSColon=Constants.ZERO;

	/** The initialize continent list. */
	private ArrayList<String> initContinentList;

	/** The initialize territory list. */
	private ArrayList<String> initTerritoryList;

	/** The initialize adjacency list. */
	private ArrayList<String> initAdjacencyList=new ArrayList<String>();

	/** The adjacency vertices. */
	private int adjVertices; // No. of vertices 

	/** The adjacency list. */
	private LinkedList<Integer> adjList[]; //Adjacency List 

	/** The adjacency list transpose. */
	private LinkedList<Integer> adjListTranspose[]; //Adjacency List for transpose DFS

	/**
	 * Main validation method.
	 *
	 * @param parsedMapFile list of parsed map file
	 * @return true if parsedMapFile is valid
	 */

	public boolean validateMap(List<String> parsedMapFile) {		
		if(validateMapSyntax(parsedMapFile)) {
			if(validateDuplicacy(initContinentList, initTerritoryList)) {
				riskMapBuilder = new RiskMapBuilder(); 
				riskMapBuilder.loadMapData(parsedMapFile);
				continentList=riskMapBuilder.getContinentList();
				territoryList=riskMapBuilder.getTerritoryList();
				adjacencyMap=riskMapBuilder.getAdjacencyMap();
				if(validateContinents(continentList)) {
					if(validateTerritories(territoryList, adjacencyMap)) {			
						validation=true;
					}else {validation=false;}
				}else {validation=false;}
			}else {validation=false;}		

		}else {validation=false;}


		return validation;
	}


	/**
	 * Validate map syntax.
	 *
	 * @param parsedMapFile list of parsed map file
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
	 * Validation for file syntax for tags.
	 *
	 * @param parsedMapFile list of parsed map file
	 * @return true if file is valid with tags
	 */

	private boolean checkMustTags(List<String> parsedMapFile) {
		boolean mustTagsValidation=false;			
		List<String> mustHaveTags=new ArrayList<String>(Arrays.asList("[map]", "[continents]","[territories]","-",";;"));

		//		check for [Map], [Continents], [Territories],- and ;;
		if(parsedMapFile.containsAll(mustHaveTags)) {
			indexOfMap=parsedMapFile.indexOf("[map]");
			indexOfContinents=parsedMapFile.indexOf("[continents]");
			indexOfTerritories=parsedMapFile.indexOf("[territories]");
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
	 * Validation for file syntax for tags.
	 *
	 * @param parsedMapFile list of parsed map file
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
				}else { continentValidation=false; break;}

			}else { continentValidation=false; break;}

		}

		return continentValidation;
	}


	/**
	 * Validation for Territories.
	 *
	 * @param territoryList the territory list
	 * @param adjMap adjacency map
	 * @return true if territories are connected
	 */

	private boolean validateTerritories(ArrayList<RiskTerritory> territoryList, Map<String, List<String>> adjMap) {
		boolean territoryValidation=false;
		int matchCounter=Constants.ZERO;
		if (territoryList.size()<3) {
			territoryValidation=false;
		}else territoryValidation=true;

		if(territoryValidation) {
			for (String key : adjMap.keySet()) {
				matchCounter=0;
				if(adjMap.containsKey(key)) {
					for (String currAdj : adjMap.get(key)) {
						if(adjMap.containsKey(currAdj)) {
							for (String newAdj :adjMap.get(currAdj)){			    		  
								if (newAdj.equalsIgnoreCase(key)) {
									matchCounter++;
								}
							}			    	  
						}
					}
					if (!(matchCounter==adjMap.get(key).size())) {
						territoryValidation=false;
						break;
					}else territoryValidation=true;
				}
			}
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
			territoryValidation=checkTerritoryAdjacency();
		}
		return territoryValidation;
	}


	/**
	 * Validate territory and continent duplicate.
	 *
	 * @param continents list of continent
	 * @param territories list of territories
	 * @return true if no duplicate territory or continent
	 */

	private boolean validateDuplicacy(ArrayList<String> continents, ArrayList<String> territories) {
		boolean duplicacyValidation=false;
		int continentCounter=Constants.ZERO;
		int territoryCounter=Constants.ZERO;
		int adjacentCounter=Constants.ZERO;
		int adjacentCounterSelf = Constants.ZERO;
		int continentCorrectCount=Constants.ZERO;
		//		name of territory and name of continent should not be same
		for (String currTerritory : territories) {
			for (String currContinent : continents) {
				if ((currTerritory.split(",")[0]).equalsIgnoreCase(currContinent)) {
					duplicacyValidation=false;
					break;
				}else {duplicacyValidation=true;}
			}
			if(duplicacyValidation==false) break;
			initAdjacencyList=new ArrayList<String>();
			initAdjacencyList.addAll(Arrays.asList((currTerritory.split(",",3)[2]).split(",")));
			for (String currAdjacent : initAdjacencyList) {
				for (String currContinent : continents) {
					if(currAdjacent.equalsIgnoreCase(currContinent)){
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
				continentCounter=Constants.ZERO;
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
				initAdjacencyList=new ArrayList<String>();
				initAdjacencyList.addAll(Arrays.asList((currTerritory.split(",",3)[2]).split(",")));
				for (String currAdjacent : initAdjacencyList) {
					adjacentCounter=Constants.ZERO;
					for (String anotherAdjacent : initAdjacencyList) {
						if(currAdjacent.equalsIgnoreCase(anotherAdjacent))
							adjacentCounter++;
					}if(adjacentCounter>2) {duplicacyValidation=false; break;}					
				}if(adjacentCounter>2) {duplicacyValidation=false; break;}
			}

			//			there can not be  adjacent territory with same name as territory name
			for (String currTerritory : territories) {
				initAdjacencyList=new ArrayList<String>();
				initAdjacencyList.addAll(Arrays.asList((currTerritory.split(",",3)[2]).split(",")));
				for (String currAdjacent : initAdjacencyList) {
					adjacentCounterSelf = Constants.ZERO;
					if(currAdjacent.equalsIgnoreCase(currTerritory))
						adjacentCounterSelf++;				
				}if(adjacentCounterSelf>0) {duplicacyValidation=false; break;}
			}

			//			continent name in territory should be present in continent list
			for (String currTerritory : territories) {
				continentCorrectCount=Constants.ZERO;
				for (String currContinent : continents) {
					if ((currTerritory.split(",")[1]).equalsIgnoreCase(currContinent)) {
						continentCorrectCount++;
					}
				}if(continentCorrectCount!=1) {duplicacyValidation=false; break;}
			}

			if(continentCounter<2 && territoryCounter<2 && adjacentCounter<2 && continentCorrectCount==1&&adjacentCounterSelf==0) {
				duplicacyValidation=true;
			}else {duplicacyValidation=false;}
		}

		return duplicacyValidation;
	}

	/**
	 * Validation for continents.
	 *
	 * @param continentList list of continent
	 * @return true if continents are valid
	 */

	public boolean validateContinents(ArrayList<RiskContinent> continentList){
		boolean ContinentValidation=false;
		if (continentList.size()>1) {
			ContinentValidation=true;
		}
		return ContinentValidation;
	}


	/**
	 * Validation for file syntax for tags.
	 *
	 * @param parsedMapFile list of parsed map file
	 * @return true territory syntax is valid
	 */	

	private boolean checkTerritorySyntax(List<String> parsedMapFile) {
		boolean territoryValidation=false;
		initTerritoryList=new ArrayList<String>();
		String currentTerritory;


		for (int i=indexOfTerritories+1;i<indexOfDSColon;i++) {
			currentTerritory=parsedMapFile.get(i);
			String [] splitArray=currentTerritory.split(",");
			//		checking that each territory line contains minimum 3 words
			if(splitArray.length>=3 ) {
				initTerritoryList.add(currentTerritory);
				territoryValidation=true;
				//		need to write further validation if required

			}else { territoryValidation=false; break;}

		}

		return territoryValidation;
	}


	/**
	 * Initializing adjacency.
	 *
	 * @param v total vertices in graph
	 */	

	public void initializeAdj(int v) 
	{ 
		adjVertices = v; 
		adjList = new LinkedList[v]; 
		for (int i=0; i<v; ++i) 
			adjList[i] = new LinkedList<Integer>(); 
	} 


	/**
	 * Function to add an edge into the graph .
	 *
	 * @param v current vertex in graph
	 * @param w next vertex in graph to draw edge
	 */	
	void addEdge(int v,int w) { 
		adjList[v].add(w); 
	} 


	/**
	 * A recursive function to print DFS starting from v .
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
	 * A recursive function to print DFS starting from v .
	 *
	 * @param v current vertex in graph
	 * @param visited array of visited vertices
	 */	

	public void DFSUtilTranspose(int v,Boolean visited[]) 
	{ 
		//		Mark the current node as visited and print it 
		visited[v] = true; 

		int n; 

		//		Recur for all the vertices adjacent to this vertex 
		Iterator<Integer> i = adjListTranspose[v].iterator(); 
		while (i.hasNext()) 
		{ 
			n = i.next(); 
			if (!visited[n]) 
				DFSUtil(n,visited); 
		} 
	} 


	/**
	 * Initializing transpose of adjacency matrix.
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
	 * The main function to check adjacency  .
	 *
	 * @return returns true if graph is strongly connected
	 */

	Boolean checkTerritoryAdjacency() 
	{ 
		//		Step 1: Mark all the vertices as not visited 
		//		(For first DFS) 
		Boolean visited[] = new Boolean[adjVertices]; 
		for (int i = 0; i < adjVertices; i++) 
			visited[i] = false; 

		//		Step 2: Do DFS traversal starting from first vertex. 
		DFSUtil(0, visited); 

		//		If DFS traversal doesn't visit all vertices, then 
		//		return false. 
		for (int i = 0; i < adjVertices; i++) 
			if (visited[i] == false) 
				return false; 

		//		Step 3: Create a reversed graph 
		initializeAdjTranspose(adjVertices);

		//		Step 4: Mark all the vertices as not visited (For second DFS) 
		for (int i = 0; i < adjVertices; i++) 
			visited[i] = false; 

		//		Step 5: Do DFS for reversed graph starting from 
		//		first vertex. Staring Vertex must be same starting 
		//		point of first DFS 
		DFSUtilTranspose(0, visited); 

		//		If all vertices are not visited in second DFS, then 
		//		return false 
		for (int i = 0; i < adjVertices; i++) 
			if (visited[i] == false) 
				return false; 

		return true; 
	} 

}
