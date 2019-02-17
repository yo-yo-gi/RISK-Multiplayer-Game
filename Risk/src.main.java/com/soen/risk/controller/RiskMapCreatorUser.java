package com.soen.risk.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RiskMapCreatorUser {
	
public static void main(String[] args) {
		
		Scanner scanner=new Scanner(System.in);
		boolean flag1=true;
		boolean flag2=true;
		
		
		//Logic for adding continents manually - START
		System.out.print("Do you want to create the map manually?(Y/N)");
		char selection1=scanner.nextLine().charAt(0);
		if(selection1=='Y'||selection1=='y') {
			Write("[Map]");
			Append("\n");
			Append("[Continent]");
			Append("\n");
			System.out.print("Enter the continent name and control value in the format<Continent Name=Control value>:");
			String text1=scanner.nextLine();
			Append(text1);
			while(flag1) {
				System.out.print("Do you want to add more continents?(Y/N)");
				char selection2=scanner.nextLine().charAt(0);
				if(selection2=='Y'||selection2=='y') {
					System.out.print("Enter the continent name:");
					String text2=scanner.nextLine();
					Append("\n");
					Append(text2);
					
				}
				else {
					flag1=false;
					Append("\n");
					Append("-");
					System.out.println("User selected N.Exit");
										
				}
			}
			System.out.println("Enter the territory details in the format:");
			String text3=scanner.nextLine();
			Append("\n");
			Append("[Territories]");
			Append("\n");
			Append(text3);
			while(flag2) {
				System.out.print("Do you want to add more territories?(Y/N)");
				char selection2=scanner.nextLine().charAt(0);
				if(selection2=='Y'||selection2=='y') {
					System.out.println("Enter the territory details in the format:");
					String text4=scanner.nextLine();
					Append("\n");
					Append(text4);
					
				}
				else {
					flag2=false;
					Append("\n");
					Append(";;");
					System.out.println("User selected N.Exit");
										
				}
			}
		}
		else {
			//append.Append("-");
			System.out.println("User selected N. Exit");
		}
		//Logic for adding continents manually - END		
		
	}

	
	public static void Write(String text) {
		FileWriter fWriter = null;
        BufferedWriter writer = null;
        try {
          fWriter = new FileWriter("text.txt");
          writer = new BufferedWriter(fWriter);
          writer.write(text);
          writer.newLine();
          writer.close();
          System.out.println("Input saved");
        } catch (Exception e) {
            System.out.println("Error!");
        }
	}
	
	public static void Append(String text) {
		try
        {
            String filename= "text.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(text);//appends the string to the file
            fw.close();
            System.out.println("Append done");
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
	}

}
