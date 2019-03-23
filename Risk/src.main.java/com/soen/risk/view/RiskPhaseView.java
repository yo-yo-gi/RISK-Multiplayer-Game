/**
 * 
 */
package com.soen.risk.view;

import com.soen.risk.model.RiskPhaseObservable;
import com.soen.risk.model.RiskPhaseType;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskPhaseView implements RiskPhaseObserver {

	private RiskPhaseType currentGamePhase;
	private String currentPlayerName;
	private String currentAction;
	private RiskPhaseObservable phaseObservable;
	
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
	 * printing phase view
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
