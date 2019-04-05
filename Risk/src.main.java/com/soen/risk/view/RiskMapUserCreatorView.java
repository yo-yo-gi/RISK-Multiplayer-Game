/*
 * 
 */
package com.soen.risk.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.soen.risk.helper.Constants;

/**
 * <h2>Risk map view</h2>
 * This class is used to show view for user created map.
 *
 * @author Shashank Rao
 * @author Chirag Vora
 * @version 1.0
 */
public class RiskMapUserCreatorView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5596428415250055023L;

	/** The territory end index. */
	int continentStartIndex=Constants.ZERO,continentEndIndex=Constants.ZERO,territoryStartIndex=Constants.ZERO,territoryEndIndex=Constants.ZERO;
	
	/** The display list. */
	ArrayList<String> displayList=new ArrayList<String>();
	
	/**
	 * Display map.
	 *
	 * @param mapFile the map file
	 */
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
