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
		System.out.println("*************************************************************");
		System.out.println("*                    Risk Card View                         *");
		System.out.println("*************************************************************");
		System.out.println("New card has been assigned to player.\nUpdated card deck as: \n");
		System.out.println(this.cardOwned);
		System.out.println("\n*************************************************************");
	}

}
