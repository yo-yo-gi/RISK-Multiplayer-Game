/**
 * 
 */
package com.soen.risk.view;

import java.util.ArrayList;

import com.soen.risk.model.RiskCard;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public interface RiskCardviewObserver {
	
	public void update(ArrayList<RiskCard> cardOwned);
}
