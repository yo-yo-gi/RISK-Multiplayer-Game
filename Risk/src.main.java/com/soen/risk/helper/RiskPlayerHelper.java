/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Helper class for main controller</h2>
 * 
 
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0.0
 * @since 2019-02-17
 */
public class RiskPlayerHelper {

	public int calculateInitialArmies(List<String> riskPlayersNames) {
		int numOfPlayers = riskPlayersNames.size();
		int armies = 0;

		if (numOfPlayers == 3)
			armies = 35;
		else if (numOfPlayers == 4)
			armies = 30; 
		else if (numOfPlayers == 5)
			armies = 25;
		else if (numOfPlayers == 6)
			armies = 20;
		
		return armies;
	}
}
