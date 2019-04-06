/**
 * 
 */
package com.soen.risk.view;

import java.util.List;

/**
 * <h2> Risk Domination Observer </h2>
 * The interface RiskDominationObserver. An asynchronous update interface for receiving notifications
 * about RiskDomination information as the RiskDomination is constructed.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public interface RiskDominationObserver {
	
	/**
	 * This method is called when information about an RiskDomination
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param percentMapContr the percent map contr
	 * @param continentsContr the continents contr
	 * @param armiesOwned the armies owned
	 */
	public void update(String percentMapContr, List<String> continentsContr, long armiesOwned);
}
