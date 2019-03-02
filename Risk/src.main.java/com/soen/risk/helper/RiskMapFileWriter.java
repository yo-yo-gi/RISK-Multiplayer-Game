package com.soen.risk.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

/**
 * <h2>Map file generator</h2>
 * <ul>
 * <li>A text file containing the user created map elements.
 *
 * </ul>
 *
 * @author Shashank Rao
 * @version 1.0.0
 */
public class RiskMapFileWriter {

	/**
	 * @param mapFile
	 */
	public void writeMapToTextFile(List<String> mapFile,String filename) {

	    PrintWriter writer = null;
	    String mapFilePath=Paths.get(System.getProperty("user.dir") + "/src.main.resources/maps/"+filename+"txt").toAbsolutePath().toString();
		try {
			File file = new File (mapFilePath);
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    for (String line : mapFile) {
	        writer.println(line);
	    }
	    writer.close();
	
		
	}

}
