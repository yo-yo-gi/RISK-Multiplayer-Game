/**
 * 
 */
package com.soen.risk.view;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public interface RiskCardviewObserver {
	public void addObserver(RiskCardviewObserver observer);
	public void removeObserver(RiskCardviewObserver observer);
	public void notifyAllObservers(); 
}
