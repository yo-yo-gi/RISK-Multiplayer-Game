/**
 * 
 */
package com.soen.risk.view;

import com.soen.risk.model.RiskPhaseObservable;
import com.soen.risk.model.RiskPhaseType;

// TODO: Auto-generated Javadoc
/**
 * The Class RiskPhaseView. The phase view displays: 
 * (1) the name of the game phase currently being played 
 * (2) the current player’s name 
 * (3) information about actions that are taking place during this phase.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public class RiskPhaseView implements RiskPhaseObserver {

	/** The current game phase. */
	private RiskPhaseType currentGamePhase;
	
	/** The current player name. */
	private String currentPlayerName;
	
	/** The current action. */
	private String currentAction;
	
	/** The phase observable. */
	private RiskPhaseObservable phaseObservable;
	
	/**
	 * Instantiates a new risk phase view.
	 *
	 * @param phaseObservable the phase observable
	 */
	public RiskPhaseView(RiskPhaseObservable phaseObservable) {
		this.phaseObservable=phaseObservable;
		this.phaseObservable.addObserver(this);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soen.risk.view.RiskPhaseObserver#update()
	 */
	@Override
	public void update(RiskPhaseType currentGamePhase, String currentPlayerName, String currentAction) {
		this.currentGamePhase = currentGamePhase;
		this.currentPlayerName = currentPlayerName;
		this.currentAction = currentAction;
		showPhaseView();
	}

	/**
	 * Printing Risk Phase View.
	 */
	private void showPhaseView() {
		System.out.println("*************************************************************");
		System.out.println("*                    Risk Phase View                        *");
		System.out.println("*************************************************************");
		System.out.println("Current Phase: "+this.currentGamePhase);
		System.out.println("Current Player: "+this.currentPlayerName);
		System.out.println("Current Action: "+this.currentAction);
		System.out.println("*************************************************************");
	}

}
