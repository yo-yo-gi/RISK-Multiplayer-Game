/**
 * 
 */
package com.soen.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.soen.risk.view.RiskPhaseObserver;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskPhase implements RiskPhaseObservable {

	private RiskPhaseType currentGamePhase;
	private String currentPlayerName;
	private String currentAction;
	private List<RiskPhaseObserver> riskPhaseObservers;
	
	
	public RiskPhase() {
		riskPhaseObservers=new ArrayList<RiskPhaseObserver>();
	}	
	
	/**
	 * @param currentGamePhase the currentGamePhase to set
	 */
	public void setCurrentGamePhase(RiskPhaseType currentGamePhase) {
		this.currentGamePhase = currentGamePhase;
	}


	/**
	 * @param currentPlayerName the currentPlayerName to set
	 */
	public void setCurrentPlayerName(String currentPlayerName) {
		this.currentPlayerName = currentPlayerName;
	}


	/**
	 * setting current action and calling notifyAllObservers method of Observable 
	 * 
	 * @param currentAction the currentAction to set
	 */
	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
		notifyAllObservers();
	}


	/* (non-Javadoc)
	 * @see com.soen.risk.model.RiskPhaseObservable#addObserver(com.soen.risk.view.RiskPhaseObserver)
	 */
	@Override
	public void addObserver(RiskPhaseObserver observer) {
		riskPhaseObservers.add(observer);
		
	}
	
	/* (non-Javadoc)
	 * @see com.soen.risk.model.RiskPhaseObservable#removeObserver(com.soen.risk.view.RiskPhaseObserver)
	 */
	@Override
	public void removeObserver(RiskPhaseObserver observer) {
		riskPhaseObservers.remove(observer);
		
	}
	
	/* (non-Javadoc)
	 * @see com.soen.risk.model.RiskPhaseObservable#notifyAllObservers()
	 */
	@Override
	public void notifyAllObservers() {
		for (RiskPhaseObserver riskPhaseObserver : riskPhaseObservers) {
			riskPhaseObserver.update(this.currentGamePhase, this.currentPlayerName, this.currentAction);
		}
		
	}
}
