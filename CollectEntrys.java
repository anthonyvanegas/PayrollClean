import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
/*
 * This class collects Entrys, validates them, and stores them in an ArrayList
 */
public class CollectEntrys {
	//Variable declarations
	Entry[] entArr;						//Array of entry objects
	String fileName;						//Name of the file
	/*
	 * Constructor
	 */
	public CollectEntrys() {
		
		
	}
	public CollectEntrys(String fileName) throws IOException {
		
		//Assign file name
		this.fileName = fileName;
		
		//Instantiate array
		entArr = new Entry[getLineCount()+1];
		
		//Variable declarations
		String currentLine;			//Current line being read from BufferedReader
		StringTokenizer split;		//To collect subsections of currentLine
		int index = 0;				//Index
		
		//Open file
		File inputFile = new File(fileName);			//File being opened
		//Create I/O
		Scanner reader = new Scanner(inputFile);
		
		while(reader.hasNext()) {
			
			currentLine = reader.nextLine();
			
			//Verify that the entry has the correct values
			try {
				
				split = new StringTokenizer(currentLine, ",");
				if(split.countTokens() < 4)
					throw new IllegalArgumentException();
				
			}catch(IllegalArgumentException e) {
				
				JOptionPane.showMessageDialog(null, "Invalid Entry: " + currentLine);
				currentLine = reader.nextLine();
				
			}
			entArr[index] = new Entry(currentLine);
			
			index++;
		}
		reader.close();
	
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		String str = "";
		
		for(int i = 0; i < entArr.length; i++) {
			
			str += "\n[" + entArr[i].getEmpID() + "," +
			entArr[i].getEmpNm() + "," + entArr[i].getPayType() + ","
			+ entArr[i].getPayAm() + "," + entArr[i].getDept() + "],";
			
		}
		
		return str;
		
	}
	/*
	 * This method returns the number of lines in the file
	 * @param fileName the name/location of the file being processed
	 */
	public int getLineCount() throws FileNotFoundException {
		
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		String str;
		
		int count = 0;
		
		str = scanner.nextLine();
		
		while (scanner.hasNextLine()) {
			
			  if(Character.isDigit(str.charAt(0)))
				  count++;
			  str = scanner.nextLine();
			  
			}
		
		scanner.close();
		
		return count;
		
	}
	
	/*
	 * This method returns the entry array
	 */
	public Entry[] getEntArray() {
		
		return entArr;
		
	}
	
	/*
	 * This method sets the entry array
	 */
	public void setEntArray(Entry[] entArr) {
		
		
		this.entArr = entArr;
		
	}
}
