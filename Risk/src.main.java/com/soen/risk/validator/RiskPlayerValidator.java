/*
 * 
 */
package com.soen.risk.validator;
// TODO: Auto-generated Javadoc

/**
 * The Class RiskPlayerValidator.
 *
 * @author pooja
 */
public class RiskPlayerValidator {
	
	/**
	 * Checks if is valid.
	 *
	 * @param n the n
	 * @return true, if is valid
	 */
	public  boolean isValid(int n)
	{
		if(n>6||n<3){
			System.out.println("Try Again!!");
			return false;
		}
		else return true;
	}
}
