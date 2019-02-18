/**
 * 
 */
package com.soen.risk.validator;

import java.util.List;

/**
 * <h2>Map File Validator</h2>
 * <ul>
 * <li>Running various validations
 *
 * </ul>
 *
 * @author Yogesh Nimbhorkar
 * @version 1.0.0
 * @since 2019-02-12
 */
public class RiskMapValidator {
	/*
	 * need to add code for 
	 * 1) Syntax of general map file. i.e. start and end with required tags
	 * 2) Syntax of continents
	 * 3) Syntax for territories
	 * 4) Duplicate territory
	 * 5) Duplicate continents
	 * 6) duplicate territory in continent
	 * 7) duplicate adjacent territory in territory object
	 * 8) connected continents
	 * 9) connected territories
	 * 10) some inputs from team
	 */
	/**
	 * @param parsedMapFile
	 * @return
	 */
	public boolean validateMap(List<String> parsedMapFile) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	/**
	 * @param parsedMapFile
	 * @return
	 */
	public boolean validateMapSyntax(List<String> parsedMapFile) {
		// TODO Auto-generated method stub
		return false;
	}


	
	/**
	 * @param continentName
	 * @return
	 */
	public boolean validateContinent(String continentName){
		
		return true;
	}

}
