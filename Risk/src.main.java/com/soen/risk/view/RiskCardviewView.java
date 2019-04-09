/**
 * 
 */
package com.soen.risk.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.soen.risk.model.RiskCard;
import com.soen.risk.model.RiskCardviewObservable;

/**
 * <h2> Risk Card View class </h2>
 * The Class RiskCardviewView. Shows the Cardview and updates the cardOwned.
 *
 * @author Yogesh Nimbhorkar
 * @version 2.0
 */
public class RiskCardviewView implements RiskCardviewObserver, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2983592628997945724L;

	/** The card owned. */
	private List<RiskCard> cardOwned;
	
	/** The cardview obsevable. */
	private RiskCardviewObservable cardviewObsevable;
	
	/**
	 * Instantiates a new risk cardview view.
	 *
	 * @param cardviewObsevable the cardview obsevable
	 */
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
	 * Show Cardview.
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
