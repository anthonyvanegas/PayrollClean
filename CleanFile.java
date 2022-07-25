import java.io.*;

public class CleanFile{
	

	/*
	 * Constructor
	 */
	public CleanFile(String fileNameT, String fileNameI) throws IOException {
		
		
		Clean clean = new Clean(fileNameT);
		clean.deleteAllLines(false);
		clean.cleanStrings();
		
		Clean cleanIKI = new Clean(fileNameI);
		cleanIKI.deleteAllLines(true);
		cleanIKI.cleanStrings();
	
	}

	/*
	 * main
	 */
	public static void main(String args[]) throws IOException {
		
		CleanFile clean = new CleanFile("T.csv","I.csv");
		
		CollectEntrys tjp = new CollectEntrys("T.csv");
		
		CollectEntrys iki = new CollectEntrys("I.csv");
		
		CleanEntrys cleanTJP = new CleanEntrys("T.csv",tjp.getEntArray(), false);
		cleanTJP.toFile("08/18/18 - 08/30/18");
		
		CleanEntrys cleanIKI = new CleanEntrys("I.csv",iki.getEntArray(), true);
		cleanIKI.toFile("08/18/18 - 08/30/18");
		
		
	}

}
