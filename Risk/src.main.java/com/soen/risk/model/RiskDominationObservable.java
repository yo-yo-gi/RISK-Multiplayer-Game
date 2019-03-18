/**
 * 
 */
package com.soen.risk.model;

import com.soen.risk.view.RiskDominationObserver;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public interface RiskDominationObservable {

	public void addObserver(RiskDominationObserver observer);
	public void removeObserver(RiskDominationObserver observer);
	public void notifyAllObservers(); 
}
