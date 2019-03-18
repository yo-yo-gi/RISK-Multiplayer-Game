/**
 * 
 */
package com.soen.risk.view;

import com.soen.risk.model.RiskPhaseType;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public interface RiskPhaseObserver {

	public void update(RiskPhaseType currentGamePhase, String currentPlayerName, String currentAction);
}
