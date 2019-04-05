/**
 * 
 */
package com.soen.risk.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.soen.risk.model.RiskDominationObservable;

/**
 * <h2> Risk Domination View class </h2>
 * The Class RiskDominationView. The players world domination view displays: 
 * (1) the percentage of the map controlled by every player 
 * (2) the continents controlled by every player 
 * (3) the total number of armies owned by every player.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public class RiskDominationView implements RiskDominationObserver, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7516841628080122976L;

	/** The percent map contr. */
	private String percentMapContr;
	
	/** The continents contr. */
	private List<String> continentsContr=new ArrayList<String>();
	
	/** The armies owned. */
	private int armiesOwned;
	
	/** The risk domination observable. */
	RiskDominationObservable riskDominationObservable;

	/**
	 * Instantiates a new risk domination view.
	 *
	 * @param riskDominationObservable the risk domination observable
	 */
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
	 * printing phase view.
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
