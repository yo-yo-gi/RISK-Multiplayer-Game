/**
 * 
 */
package com.soen.risk.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * <h2>User Map Creator</h2>
 * This class is used for creation of map by user,
 * Creation of map by user taking appropriate values for continents and territories.
 *
 * @author Shashank Rao
 * @version 1.0
 */
public class RiskMapUserCreator implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5124274417312752360L;
	/** The create status. */
	boolean createStatus=true;

	/**
	 * Map creator.
	 *
	 * @return finalMap Returns a full list of map file containing the continents and territories entered by the user.
	 */
	public List<String> mapCreator() {


		Scanner scanner=new Scanner(System.in);
		boolean flag1=true;
		boolean flag2=true;
		char selection1, selection2;

		ArrayList<String> Map1=new ArrayList<String>();
		ArrayList<String> Map2=new ArrayList<String>();
		ArrayList<String> Map3=new ArrayList<String>();
		ArrayList<String> Map4=new ArrayList<String>();
		ArrayList<String> Map5=new ArrayList<String>();
		Map1.add("[Map]");
		Map1.add("");
		Map1.add("[Continents]");

		Map3.add("-");
		Map3.add("[Territories]");

		Map5.add(";;");
		ArrayList<String> finalmap=new ArrayList<String>();

		System.out.print("Do you want to create the map manually?(Y/N)");
		
		
		do {
			selection1=scanner.next().charAt(0);
			if(!(selection1=='Y' || selection1=='y' || selection1=='n' || selection1=='N')) {
				System.out.println("Try Again!!");
			}
		}while(!(selection1=='Y' || selection1=='y' || selection1=='n' || selection1=='N'));
		
		
		if(selection1=='Y'||selection1=='y') {
			createStatus=true;
			System.out.println("*****Note : Please use underscore(_) instead of spaces..*****");
			System.out.println("Enter the continent name and control value in the format<Continent Name=Control value>:");
			String text1=scanner.next();
			Map2.add(text1);
			while(flag1) {
				System.out.print("Do you want to add more continents?(Y/N)");
				do {
					selection2=scanner.next().charAt(0);
					if(!(selection2=='Y' || selection2=='y' || selection2=='n' || selection2=='N')) {
						System.out.println("Try Again!!");
					}
				} while(!(selection2=='Y' || selection2=='y' || selection2=='n' || selection2=='N'));
				if(selection2=='Y'||selection2=='y') {
					System.out.println("Enter the continent name and control value in the format:<Continent Name=Control value>");
					String text2=scanner.next();
					Map2.add(text2);
				}else {
					
					flag1=false;


				}
			}

			System.out.println("Enter the territory details in the format:<TerritoryName,ContinentName,AdjacentCountry1,AdjacentCountry2...AdjacentCountryN>");
			String text3=scanner.next();
			Map4.add(text3);

			while(flag2) {
				System.out.print("Do you want to add more territories?(Y/N)");
				do {
					selection2=scanner.next().charAt(0);
					if(!(selection2=='Y' || selection2=='y' || selection2=='n' || selection2=='N')) {
						System.out.println("Try Again!!");
					}
				} while(!(selection2=='Y' || selection2=='y' || selection2=='n' || selection2=='N'));
				if(selection2=='Y'||selection2=='y') {
					System.out.println("Enter the territory details in the format:<TerritoryName,ContinentName,AdjacentCountry1,AdjacentCountry2...AdjacentCountryN>");
					String text4=scanner.next();
					Map4.add(text4);
				}else {
					
					flag2=false;

				}
			}

		}else {
			createStatus=false;
			System.out.println("User entered No[N]");
		}

		if(Map2.isEmpty()&&Map4.isEmpty()) {
			System.out.println("Nothing has been created. File is without continent or territory");
		}
		finalmap.addAll(Map1);
		finalmap.addAll(Map2);
		finalmap.addAll(Map3);
		finalmap.addAll(Map4);
		finalmap.addAll(Map5);

		return finalmap;
	}

	/**
	 * Gets the creates the status.
	 *
	 * @return the creates the status
	 */
	public boolean getcreateStatus() {
		return createStatus;
	}
}
