/**
 * 
 */
package com.soen.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.soen.risk.view.RiskDominationObserver;

/**
 * <h2> Risk Domination </h2>
 * The Class RiskDomination. The players world domination view displays: 
 * (1) the percentage of the map controlled by every player 
 * (2) the continents controlled by every player 
 * (3) the total number of armies owned by every player.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public class RiskDomination implements RiskDominationObservable {

	/** The percent map contr. */
	private String percentMapContr;

	/** The continents contr. */
	private List<String> continentsContr;

	/** The armies owned. */
	private int armiesOwned;

	/** The domination observers. */
	List<RiskDominationObserver> dominationObservers;

	/**
	 * Instantiates a new risk domination.
	 */
	public RiskDomination() {
		dominationObservers = new ArrayList<RiskDominationObserver>();
	}

	/**
	 * Sets the percent map contr.
	 *
	 * @param percentMapContr the percentMapContr to set
	 */
	public void setPercentMapContr(String percentMapContr) {
		this.percentMapContr = percentMapContr;
	}

	/**
	 * Sets the continents contr.
	 *
	 * @param continentsContr the continentsContr to set
	 */
	public void setContinentsContr(List<String> continentsContr) {
		this.continentsContr = continentsContr;
	}

	/**
	 * Sets the armies owned.
	 *
	 * @param armiesOwned the armiesOwned to set
	 */
	public void setArmiesOwned(int armiesOwned) {
		this.armiesOwned = armiesOwned;
		notifyAllObservers();
	}

	/* (non-Javadoc)
	 * @see com.soen.risk.controller.RiskDominationObservable#addObserver(com.soen.risk.view.RiskDominationObserver)
	 */
	@Override
	public void addObserver(RiskDominationObserver observer) {
		dominationObservers.add(observer);

	}

	/* (non-Javadoc)
	 * @see com.soen.risk.controller.RiskDominationObservable#removeObserver(com.soen.risk.view.RiskDominationObserver)
	 */
	@Override
	public void removeObserver(RiskDominationObserver observer) {
		dominationObservers.remove(observer);

	}

	/* (non-Javadoc)
	 * @see com.soen.risk.controller.RiskDominationObservable#notifyAllObservers()
	 */
	@Override
	public void notifyAllObservers() {
		for (RiskDominationObserver riskDominationObserver : dominationObservers) {
			riskDominationObserver.update(this.percentMapContr, this.continentsContr, this.armiesOwned);
		}

	}

}
