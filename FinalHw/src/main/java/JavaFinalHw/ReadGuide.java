package JavaFinalHw;

import java.util.ArrayList;
import java.io.*;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;

public class ReadGuide {

 
 public static void writeAFile(ArrayList<String> lines, String targetFileName) {
	
		try (CSVPrinter printer = new CSVPrinter(new FileWriter(targetFileName), CSVFormat.DEFAULT)) {
		    for(String line:lines) {
		    		String[] list = line.split("\t");
		    		if(list.length < 5) {
		    			String list2 = "\t";
		    			list2 += line;
		    			String lists[] = list2.split("\t");
		    			printer.printRecord(lists);
		    	}
		    		else {
		    			printer.printRecord(list);
		    		}
		    }
		 } catch (IOException e) {
		     e.printStackTrace();
		 }
	}

}
