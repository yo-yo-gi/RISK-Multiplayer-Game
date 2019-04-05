/**
 * 
 */
package com.soen.risk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.soen.risk.view.RiskPhaseObserver;

/**
 * <h2> Risk Phase </h2>
 * The Class RiskPhase.The phase view displays: 
 * (1) the name of the game	phase currently being played 
 * (2) the current player’s name 
 * (3) information about actions that are taking place during this phase.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public class RiskPhase implements RiskPhaseObservable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 589122797906094388L;

	/** The current game phase. */
	private RiskPhaseType currentGamePhase;
	
	/** The current player name. */
	private String currentPlayerName;
	
	/** The current action. */
	private String currentAction;
	
	/** The risk phase observers. */
	private List<RiskPhaseObserver> riskPhaseObservers;
	
	
	/**
	 * Instantiates a new risk phase.
	 */
	public RiskPhase() {
		riskPhaseObservers=new ArrayList<RiskPhaseObserver>();
	}	
	
	/**
	 * Sets the current game phase.
	 *
	 * @param currentGamePhase the currentGamePhase to set
	 */
	public void setCurrentGamePhase(RiskPhaseType currentGamePhase) {
		this.currentGamePhase = currentGamePhase;
	}


	/**
	 * Sets the current player name.
	 *
	 * @param currentPlayerName the currentPlayerName to set
	 */
	public void setCurrentPlayerName(String currentPlayerName) {
		this.currentPlayerName = currentPlayerName;
	}


	/**
	 * setting current action and calling notifyAllObservers method of Observable .
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
