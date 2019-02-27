/**
 * 
 */
package com.soen.risk.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <h2>Map Creation by user</h2>
 * <ul>
 * <li>This logic creates an ArrayList of map elements and will send it for validation.
 * <li>Once the validation is done for correctness, the said ArrayList will write into a new text file.
 * <li>This map will become the current map to be loaded.
 *
 * </ul>
 *
 * @author Shashank Rao
 * @version 1.0.0
 * @since 2019-02-20
 */
public class RiskMapUserCreator {

	public List<String> mapCreator() {

		
		Scanner scanner=new Scanner(System.in);
		boolean flag1=true;
		boolean flag2=true;
		ArrayList<String> map=new ArrayList<String>();
		
		System.out.print("Do you want to create the map manually?(Y/N)");
		char selection1=scanner.nextLine().charAt(0);
		if(selection1=='Y'||selection1=='y') {
			map.add("[Map]");
			map.add("");
			//map.add("\n");
			map.add("[Continent]");
			System.out.println("Enter the continent name and control value in the format<Continent Name=Control value>:");
			String text1=scanner.nextLine();
			map.add(text1);
			while(flag1) {
				System.out.print("Do you want to add more continents?(Y/N)");
				char selection2=scanner.nextLine().charAt(0);
				if(selection2=='Y'||selection2=='y') {
					System.out.print("Enter the continent name and control value in the format<Continent Name=Control value>:");
					String text2=scanner.nextLine();
					map.add(text2);
				}
				else {
					System.out.println("User does not want to enter more continents.Exit");
					flag1=false;
					//map.add("\n");
					map.add("-");
										
				}
			}
			System.out.println("Enter the territory details in the format:");
			String text3=scanner.nextLine();
			//map.add("\n");
			map.add("[Territories]");
			//map.add("\n");
			map.add(text3);
			while(flag2) {
				System.out.print("Do you want to add more territories?(Y/N)");
				char selection2=scanner.nextLine().charAt(0);
				if(selection2=='Y'||selection2=='y') {
					System.out.println("Enter the territory details in the format:");
					String text4=scanner.nextLine();
					map.add(text4);
				}
				else {
					System.out.println("User does not want to enter more territories.Exit");
					flag2=false;
					//map.add("\n");
					map.add(";;");
				}
			}
			
		}
		else {
			System.out.println("User selected N");
		}
		scanner.close();
		return map;
	}
}
