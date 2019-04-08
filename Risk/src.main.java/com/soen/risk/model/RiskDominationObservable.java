/**
 * 
 */
package com.soen.risk.model;

import com.soen.risk.view.RiskDominationObserver;

// TODO: Auto-generated Javadoc
/**
 * <h2> Risk Domination Observable</h2>
 * The Interface RiskDominationObservable.An asynchronous update interface for receiving notifications
 * about RiskDomination information as the RiskDomination is constructed.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public interface RiskDominationObservable {

	/**
	 * Adds the observer.
	 *
	 * @param observer the observer
	 */
	public void addObserver(RiskDominationObserver observer);
	
	/**
	 * Removes the observer.
	 *
	 * @param observer the observer
	 */
	public void removeObserver(RiskDominationObserver observer);
	
	/**
	 * Notify all observers.
	 */
	public void notifyAllObservers(); 
}
