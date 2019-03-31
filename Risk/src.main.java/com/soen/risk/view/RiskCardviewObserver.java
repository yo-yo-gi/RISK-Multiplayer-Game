/**
 * 
 */
package com.soen.risk.view;

import java.util.ArrayList;

import com.soen.risk.model.RiskCard;

/**
 * <h2> Risk Card View Observer </h2>
 * The interface RiskCardviewObserver. An asynchronous update interface for receiving notifications
 * about RiskCardview information as the RiskCardview is constructed.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public interface RiskCardviewObserver {
	
	/**
	 * This method is called when information about an RiskCardview
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param cardOwned the card owned
	 */
	public void update(ArrayList<RiskCard> cardOwned);
}
