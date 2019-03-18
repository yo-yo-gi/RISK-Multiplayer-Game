/**
 * 
 */
package com.soen.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.soen.risk.view.RiskDominationObserver;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskDomination implements RiskDominationObservable {

	private String percentMapContr;
	private List<String> continentsContr;
	private int armiesOwned;
	List<RiskDominationObserver> dominationObservers;

	public RiskDomination() {
		dominationObservers = new ArrayList<RiskDominationObserver>();
	}

	/**
	 * @param percentMapContr the percentMapContr to set
	 */
	public void setPercentMapContr(String percentMapContr) {
		this.percentMapContr = percentMapContr;
	}

	/**
	 * @param continentsContr the continentsContr to set
	 */
	public void setContinentsContr(List<String> continentsContr) {
		this.continentsContr = continentsContr;
	}

	/**
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
