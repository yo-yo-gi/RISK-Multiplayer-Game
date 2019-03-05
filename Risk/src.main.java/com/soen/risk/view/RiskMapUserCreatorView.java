package com.soen.risk.view;

import java.util.ArrayList;
import java.util.List;

public class RiskMapUserCreatorView {
	int continentStartIndex=0,continentEndIndex=0,territoryStartIndex=0,territoryEndIndex=0;
	ArrayList<String> displayList=new ArrayList<String>();
	public void displayMap(List<String> mapFile) {
		
		for (String currentLine : mapFile) {
			if (currentLine.equalsIgnoreCase("[Continents]")) {
				continentStartIndex=mapFile.indexOf("[Continents]");
			}
			if (currentLine.equalsIgnoreCase("-")) {
				continentEndIndex=mapFile.indexOf("-");
			}				
			if (currentLine.equalsIgnoreCase("[Territories]")) {
				territoryStartIndex=mapFile.indexOf("[Territories]");
			}
			if (currentLine.equalsIgnoreCase(";;")) {
				territoryEndIndex=mapFile.indexOf(";;")-1;
			}			
		}
		
		for(int i=continentStartIndex;i<=continentEndIndex;i++)
			displayList.add(mapFile.get(i));
		for(int i=territoryStartIndex;i<=territoryEndIndex;i++)
			displayList.add(mapFile.get(i));
		System.out.println("\nYour Map is shown below:");
		for (String string : displayList) {
			System.out.println(string);
		}
		displayList.clear();
		
//		for(int i=startIndexContinent;i<=endIndexContinent;i++)
//			System.out.println(mapFile.get(i));
//		for(int i=startIndexTerritory;i<=endIndexTerritory;i++)
//			System.out.println(mapFile.get(i));
	}

}
