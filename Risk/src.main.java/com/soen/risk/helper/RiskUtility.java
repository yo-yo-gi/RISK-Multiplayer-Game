/**
 * 
 */
package com.soen.risk.helper;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskUtility {

	public static String calculateDominationMapControlled(long totalTerritories, long ownedTerritories) {
		String mapControll = null;
		
		long calculation = Math.round((totalTerritories/ownedTerritories)*100.0);
		mapControll=Long.toString(calculation);
		return mapControll;
	}	
}
