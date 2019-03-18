/**
 * 
 */
package com.soen.risk.model;

import com.soen.risk.view.RiskPhaseObserver;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public interface RiskPhaseObservable {

	public void addObserver(RiskPhaseObserver observer);
	public void removeObserver(RiskPhaseObserver observer);
	public void notifyAllObservers(); 
}
