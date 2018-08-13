import java.io.*;

public class CleanFile{
	

	/*
	 * Constructor
	 */
	public CleanFile(String fileNameTJP, String fileNameIKI) throws IOException {
		
		
		Clean clean = new Clean(fileNameTJP);
		clean.deleteAllLines(false);
		clean.cleanStrings();
		
		Clean cleanIKI = new Clean(fileNameIKI);
		cleanIKI.deleteAllLines(true);
		cleanIKI.cleanStrings();
	
	}

	/*
	 * main
	 */
	public static void main(String args[]) throws IOException {
		
		CleanFile clean = new CleanFile("TJP.csv","IKI.csv");
		
		CollectEntrys tjp = new CollectEntrys("TJP.csv");
		
		CollectEntrys iki = new CollectEntrys("IKI.csv");
		
		CleanEntrys cleanTJP = new CleanEntrys("TJP.csv",tjp.getEntArray(), false);
		cleanTJP.toFile("08/18/18 - 08/30/18");
		
		CleanEntrys cleanIKI = new CleanEntrys("IKI.csv",iki.getEntArray(), true);
		cleanIKI.toFile("08/18/18 - 08/30/18");
		
		
	}

}