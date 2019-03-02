/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <h2>User Map Creator</h2>
 * <ul>
 * <li>Creation of map by user.
 * <li>Creation of map by user taking appropriate values for continents and territories.
 *
 * </ul>
 *
 * @author Shashank Rao
 * @version 1.0.0
 * @return finalMap Returns a full list of mapfile containing the continents and territories entered by the user.
 */
public class RiskMapUserCreator {
	

	boolean createStatus=true;
	public List<String> mapCreator() {

		
		Scanner scanner=new Scanner(System.in);
		boolean flag1=true;
		boolean flag2=true;
		
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
		char selection1=scanner.nextLine().charAt(0);
		if(selection1=='Y'||selection1=='y') {
			createStatus=true;
			System.out.println("Enter the continent name and control value in the format<Continent Name=Control value>:");
			String text1=scanner.nextLine();
			Map2.add(text1);
			while(flag1) {
				System.out.print("Do you want to add more continents?(Y/N)");
				char selection2=scanner.nextLine().charAt(0);
				if(selection2=='Y'||selection2=='y') {
					System.out.println("Enter the continent name and control value in the format:<Continent Name=Control value>");
					String text2=scanner.nextLine();
					Map2.add(text2);
				}
				else {
					System.out.println("User does not want to enter more continents.Exit");
					flag1=false;
				
										
				}
			}
			System.out.println("Enter the territory details in the format:<TerritoryName,ContinentName,AdjacentCountry1,AdjacentCountry2...AdjacentCountryN>");
			String text3=scanner.nextLine();
			Map4.add(text3);
			while(flag2) {
				System.out.print("Do you want to add more territories?(Y/N)");
				char selection2=scanner.nextLine().charAt(0);
				if(selection2=='Y'||selection2=='y') {
					System.out.println("Enter the territory details in the format:<TerritoryName,ContinentName,AdjacentCountry1,AdjacentCountry2...AdjacentCountryN>");
					String text4=scanner.nextLine();
					Map4.add(text4);
				}
				else {
					System.out.println("User does not want to enter more territories.Exit");
					flag2=false;
					
				}
			}
			
		}
		else {
			createStatus=false;
			System.out.println("User entered No[N]");
		}
//		scanner.close();
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
	public boolean getcreateStatus() {
		return createStatus;
	}
}
