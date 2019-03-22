/**
 * 
 */
package com.soen.risk.model;

import com.soen.risk.view.RiskCardviewObserver;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public interface RiskCardviewObservable {
	public void addObserver(RiskCardviewObserver observer);
	public void removeObserver(RiskCardviewObserver observer);
	public void notifyAllObservers(); 
}
