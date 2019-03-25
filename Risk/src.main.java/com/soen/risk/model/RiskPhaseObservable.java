/**
 * 
 */
package com.soen.risk.model;

import com.soen.risk.view.RiskPhaseObserver;

// TODO: Auto-generated Javadoc
/**
 * The Interface RiskPhaseObservable.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public interface RiskPhaseObservable {

	/**
	 * Adds the observer.
	 *
	 * @param observer the observer
	 */
	public void addObserver(RiskPhaseObserver observer);
	
	/**
	 * Removes the observer.
	 *
	 * @param observer the observer
	 */
	public void removeObserver(RiskPhaseObserver observer);
	
	/**
	 * Notify all observers.
	 */
	public void notifyAllObservers(); 
}
