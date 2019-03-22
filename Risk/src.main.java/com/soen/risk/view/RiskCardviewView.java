/**
 * 
 */
package com.soen.risk.view;

import java.util.ArrayList;
import java.util.List;

import com.soen.risk.model.RiskCard;
import com.soen.risk.model.RiskCardviewObservable;

/**
 * @author Yogesh Nimbhorkar
 *
 */
public class RiskCardviewView implements RiskCardviewObserver{

	private List<RiskCard> cardOwned;
	private RiskCardviewObservable cardviewObsevable;
	
	public RiskCardviewView(RiskCardviewObservable cardviewObsevable) {
		this.cardviewObsevable=cardviewObsevable;
		this.cardviewObsevable.addObserver(this);
	}
	
	/* (non-Javadoc)
	 * @see com.soen.risk.view.RiskCardviewObserver#update(java.util.ArrayList)
	 */
	@Override
	public void update(ArrayList<RiskCard> cardOwned) {
		this.cardOwned=cardOwned;
		showCardview();
	}

	/**
	 * 
	 */
	private void showCardview() {
		System.out.println("New card has been added to players card list. Updated cards are /n");
		for (RiskCard riskCard : cardOwned) {
			System.out.println(riskCard.name());
		}
	}

}
