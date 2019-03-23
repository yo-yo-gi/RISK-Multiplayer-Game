/**
 * 
 */
package com.soen.risk.view;

import java.util.ArrayList;
import java.util.List;

import com.soen.risk.model.RiskDominationObservable;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskDominationView implements RiskDominationObserver {

	private String percentMapContr;
	private List<String> continentsContr=new ArrayList<String>();
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
		System.out.println("*************************************************************");
		System.out.println("*                  Risk Domination View                     *");
		System.out.println("*************************************************************");
		System.out.println("The percentage of map controlled by player: " + this.percentMapContr);
		System.out.println("The continents controlled by player: " + this.continentsContr);
		System.out.println("The total number of armies owned by player: " + this.armiesOwned);
		System.out.println("*************************************************************");
	}

}
