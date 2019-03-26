/**
 * 
 */
package com.soen.risk.view;

import com.soen.risk.model.RiskPhaseType;

/**
 * <h2> Risk Phase Observer </h2>
 * The interface RiskPhaseObserver. An asynchronous update interface for receiving notifications
 * about RiskPhase information as the RiskPhase is constructed.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public interface RiskPhaseObserver {

	/**
	 * This method is called when information about an RiskPhase
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param currentGamePhase the current game phase
	 * @param currentPlayerName the current player name
	 * @param currentAction the current action
	 */
	public void update(RiskPhaseType currentGamePhase, String currentPlayerName, String currentAction);
}
