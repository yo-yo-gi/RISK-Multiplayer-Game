/**
 * 
 */
package com.soen.risk.model;

import com.soen.risk.view.RiskPhaseObserver;

/**
 * <h2> Risk Phase Observable </h2>
 * The Interface RiskPhaseObservable. An asynchronous update interface for receiving notifications
 * about RiskPhase information as the RiskPhase is constructed.
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
