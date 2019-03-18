/**
 * 
 */
package com.soen.risk.view;

import java.util.List;

import com.soen.risk.model.RiskDominationObservable;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskDominationView implements RiskDominationObserver {

	private String percentMapContr;
	private List<String> continentsContr;
	private int armiesOwned;
	RiskDominationObservable riskDominationObservable;

	public RiskDominationView(RiskDominationObservable riskDominationObservable) {
		this.riskDominationObservable = riskDominationObservable;
		this.riskDominationObservable.addObserver(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soen.risk.view.RiskDominationObserver#update(java.lang.String,
	 * java.util.List, int)
	 */
	@Override
	public void update(String percentMapContr, List<String> continentsContr, int armiesOwned) {
		this.percentMapContr = percentMapContr;
		this.continentsContr = continentsContr;
		this.armiesOwned = armiesOwned;

		showPhaseView();
	}

	/**
	 * printing phase view
	 */
	private void showPhaseView() {
		System.out.println("the percentage of the map controlled by player is: " + this.percentMapContr);
		System.out.println("the continents controlled by player is: " + this.continentsContr);
		System.out.println("the total number of armies owned by player is: " + this.armiesOwned);
	}

}
