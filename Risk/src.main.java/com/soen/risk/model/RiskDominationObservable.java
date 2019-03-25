/**
 * 
 */
package com.soen.risk.model;

import com.soen.risk.view.RiskDominationObserver;

/**
 * The Interface RiskDominationObservable.
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
