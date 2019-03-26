/**
 * 
 */
package com.soen.risk.model;

import com.soen.risk.view.RiskCardviewObserver;

/**
 * The Interface RiskCardviewObservable. An asynchronous update interface for receiving notifications
 * about RiskCardview information as the RiskCardview is constructed.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public interface RiskCardviewObservable {
	
	/**
	 * Adds the observer.
	 *
	 * @param observer the observer
	 */
	public void addObserver(RiskCardviewObserver observer);
	
	/**
	 * Removes the observer.
	 *
	 * @param observer the observer
	 */
	public void removeObserver(RiskCardviewObserver observer);
	
	/**
	 * Notify all observers.
	 */
	public void notifyAllObservers(); 
}
