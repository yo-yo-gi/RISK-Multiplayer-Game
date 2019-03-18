/**
 * 
 */
package com.soen.risk.view;

import java.util.List;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public interface RiskDominationObserver {
	public void update(String percentMapContr, List<String> continentsContr, int armiesOwned);
}
