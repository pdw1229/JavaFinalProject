package JavaFinalHw;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class ZipReader extends Thread {
	
	String input;	
	String output;
	String output2;
	boolean help;	
	private String[] arg;
	private File[] result;
	
	ArrayList<String> file = new ArrayList<String>();
	ArrayList<String> file2 = new ArrayList<String>();
	
		public static void main(String[] args) throws Exception {
			int numThreads = 5;
			Thread[] t = new Thread[numThreads];
			
			for(int i=0;i<numThreads;i++) {
				ZipReader zipReader = new ZipReader(); 
				zipReader.setArg(args);
				t[i] = new Thread(zipReader);
				t[i].start();
			}
			
		}
		
		class Files <T>{
			private T t;
			
			public void set(T t) {
				this.t = t;
			}
			
			public T get() {
				return t;
			}
		}
		
	
		public void setArg(String[] args) {
			arg = args;
		}
	
	public void run() {
		
		Options options = createOptions();
		
		try {
			if(arg.length<2)
				throw new ExceptionCheck();
			
			if(parseOptions(options, arg)){
				if (help){
					printHelp(options);
					return;
				}else {
					Files<String> myFile = new Files<String>();
					
					myFile.set(input);
					
					getZipFileList(myFile.get());
					
					for(File f:result) {
						if(f.getName().contains("zip")) {
							file.add(f.getName());
							file2.add(f.getName());
							readZip(myFile.get() + f.getName());
						}
					}
					
					ReadGuide.writeAFile(file, output);
					ReadGuide.writeAFile(file2, output2);
					
				}
			}
			
		} catch (ExceptionCheck e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		
	}

	public void readZip(String path) {
		ZipFile zipFile;
		int count=0;
		try {
			zipFile = new ZipFile(path);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

		    while(entries.hasMoreElements()){
		    	
		    	ZipArchiveEntry entry = entries.nextElement();
		        InputStream stream = zipFile.getInputStream(entry);
		    
		        ExcelReader myReader = new ExcelReader();
		        
		        for(String value:myReader.getData(stream)) {
	
		        	if(count ==0)
		        		file.add(value);
		        	else if(count == 1)
		        		file2.add(value);

		        }
		        count ++;
		        file.add("");
		        file2.add("");
		        
		    }
		   zipFile.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			output2 = cmd.getOptionValue("o2");
			help = cmd.hasOption("h");


		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}      
	
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("input path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an first output file path")
				.hasArg()
				.argName("output path")
				.required()
				.build());

		options.addOption(Option.builder("o2").longOpt("output2")
				.desc("Set an second output file path")
				.hasArg()
				.argName("output path")
				.required()
				.build());

		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());

		return options;
	}

	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "Reader";
		String footer ="";
		formatter.printHelp("Reader", header, options, footer, true);
	}
	public File[] getZipFileList(String path) {
		File file = new File(path);
		result = file.listFiles();
		
		return result;
	}

}