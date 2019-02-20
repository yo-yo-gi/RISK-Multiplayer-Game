/**
 * 
 */
package com.soen.risk.helper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * <h2>Writing of map file elements in a text file</h2>
 * <ul>
 * <li>This logic creates a .txt file with the elements in the ArrayList. This file is used to load the map.
 *
 * </ul>
 *
 * @author Shashank Rao
 * @version 1.0.0
 * @since 2019-02-20
 */
public class RiskMapFileWriter {

	/**
	 * @param mapFile
	 */
	public void writeMapToTextFile(List<String> mapFile) {

	    PrintWriter writer = null;
		try {
			writer = new PrintWriter("CurrentMap.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    for (String line : mapFile) {
	        writer.println(line);
	    }
	    writer.close();
	
		
	}

}
