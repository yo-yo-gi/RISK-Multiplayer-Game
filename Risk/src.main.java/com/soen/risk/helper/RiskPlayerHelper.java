/**
 * 
 */
package com.soen.risk.helper;

import java.util.List;

/**
 * <h2>Player Helper class for main controller</h2>
 * This class is used to assign initial armies for players.
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0
 */

public class RiskPlayerHelper {

	public int calculateInitialArmies(List<String> riskPlayersNames) {
		int numOfPlayers = riskPlayersNames.size();
		int armies = Constants.ZERO;

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
