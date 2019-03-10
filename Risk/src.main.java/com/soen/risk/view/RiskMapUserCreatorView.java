package com.soen.risk.view;

import java.util.ArrayList;
import java.util.List;
/**
 * <h2>Risk map view</h2>
 * Shows view for user created map.
 *
 * @author Shashank Rao
 * @version 1.0
 */
public class RiskMapUserCreatorView {
	int continentStartIndex=0,continentEndIndex=0,territoryStartIndex=0,territoryEndIndex=0;
	ArrayList<String> displayList=new ArrayList<String>();
	public void displayMap(List<String> mapFile) {
		
		for (String currentLine : mapFile) {
			if (currentLine.equalsIgnoreCase("[Continents]")) {
				continentStartIndex=mapFile.indexOf(currentLine);
			}
			if (currentLine.equalsIgnoreCase("-")) {
				continentEndIndex=mapFile.indexOf(currentLine);
			}				
			if (currentLine.equalsIgnoreCase("[Territories]")) {
				territoryStartIndex=mapFile.indexOf(currentLine);
			}
			if (currentLine.equalsIgnoreCase(";;")) {
				territoryEndIndex=mapFile.indexOf(currentLine)-1;
			}			
		}
		
		for(int i=continentStartIndex;i<=continentEndIndex;i++)
			displayList.add(mapFile.get(i));
		for(int i=territoryStartIndex;i<=territoryEndIndex;i++)
			displayList.add(mapFile.get(i));
		
		for (String string : displayList) {
			System.out.println(string);
		}
		displayList.clear();
	}

}
