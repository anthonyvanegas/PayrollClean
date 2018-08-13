import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * This class cleans payroll files.
 */
public class Clean {
	String fileName;			//File name
	int lineCount;			//Line count
	
	/*
	 * Constructor
	 */
	public Clean(String fileName) throws FileNotFoundException {
		
		this.fileName = fileName;
		this.lineCount = getLineCount();
	}
	/*
	 * This method deletes junk lines from the file
	 */
	private void deleteLine(String line) throws IOException {
		
		File inputFile = new File(fileName);
		File tempFile = new File("Changes.csv");
		 
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		 
		String lineToRemove = line;
		String currentLine;
		 
		while((currentLine = reader.readLine()) != null) {
		 
		// trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		   
		    if(trimmedLine.equals(lineToRemove)) continue;
		 
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		 
		writer.close(); 
		reader.close(); 
		
		tempFile.renameTo(inputFile);
	}
	/*
	 * This method finds a string beginning with a comma and EMPLOYEE and send to deleteLine

	 */
	public void deleteAllLines(boolean isIKI) throws IOException {
		
			//Remove empty lines from file
			emptyLineRmv(fileName);
			
			if(isIKI)
				initialRmv(true);
		
			File file = new File(fileName);
			
			Scanner readFile = new Scanner(file);
			
			String lineRead = "";
			
			ArrayList<String> linesToDelete = new ArrayList<String>();
			
			if(!isIKI) {
				//Delete if it has a comma
				while(readFile.hasNext()) {
					
					lineRead = readFile.nextLine();
					
					if(lineRead.startsWith(","))
						linesToDelete.add(lineRead);
					if(lineRead.startsWith("EMPLOYEE"))
						linesToDelete.add(lineRead);
					if(lineRead.startsWith("2,,"))
						linesToDelete.add(lineRead);
				}
			}else if(isIKI){
				
				while(readFile.hasNext()) {
					
					lineRead = readFile.nextLine();
						
					if(lineRead.startsWith(","))
						linesToDelete.add(lineRead);
					else if(lineRead.regionMatches(lineRead.length()-4, ",,,", 0, 3))
						linesToDelete.add(lineRead);
					else if(lineRead.endsWith("DEPARTMENT,"))
						linesToDelete.add(lineRead);
					else if(lineRead.startsWith("5"))
						linesToDelete.add(lineRead);
					else continue;
						
				}
				
			}
			readFile.close();
			
			for(String line : linesToDelete)
				deleteLine(line);
			
		}
	/*
	 * This method cleans all the columns from the string
	 */
	public String addCommas(String str) {
		
		boolean flag = false;	//To flag when something is found
		int index = 0;		//To hold index
		
		//Find the first "
		for(int i = 0; i < str.length() && !flag; i++) {
			
			char c = str.charAt(i);
			
			@SuppressWarnings("deprecation")
			Character ch = new Character(c);
			
			//If the char = "
			if(ch.equals('"')) {
				
				index = i;
				flag = true;
				
			}
		}
		
		//Add the comma to it
		if(index != 0) {
			
			str = str.substring(0,index) + "," + str.substring(index, str.length());
			
			
		}
		
		//Find the second "
		flag = false;
		for(int i = index+2; i < str.length() && !flag; i++) {
			
			char c = str.charAt(i);
			
			@SuppressWarnings("deprecation")
			Character ch = new Character(c);
			
			//If the char = "
			if(ch.equals('"')) {
				
				index = i+1;
				flag = true;
				
			}
		}
		
		if(index != 0) {
			
			str = str.substring(0,index) + "," + str.substring(index, str.length());
			
			
		}
		
		//Find the first number
		flag = false;
		for(int i = index+1; i < str.length() && !flag; i++) {
			
			char c = str.charAt(i);
			
			@SuppressWarnings("deprecation")
			Character ch = new Character(c);
			
			//If the char = "
			if(Character.isDigit(ch)) {
				
				index = i;
				flag = true;
				
			}
		}
		
		if(index != 0) {
			
			str = str.substring(0,index) + "," + str.substring(index, str.length());
			
			
		}
		
		//Find the first letter
		flag = false;
		for(int i = index+1; i < str.length() && !flag; i++) {
			
			char c = str.charAt(i);
			
			@SuppressWarnings("deprecation")
			Character ch = new Character(c);
			
			//If the char = "
			if(Character.isLetter(ch)) {
				
				index = i;
				flag = true;
				
			}
		}
		
		if(index != 0 && flag == true) {
			
			str = str.substring(0,index) + "," + str.substring(index, str.length());
			
			
		}
			
		return str;
	}
	/*
	 * This method cleans the Strings a file
	 *@param fileName The name of the file 
	 */
	public void cleanStrings() throws IOException {

			
			//Variable decorations 
			File file = new File(fileName);						//Input file coming in
			File tempFile = new File("Changes.csv");				//Temp file coming out
			String lineRead;										//Line read by scanner
			String cleanedLine;									//Contains contents of readLine after it has been cleaned
			ArrayList<String> cleaned = new ArrayList<String>();	//Contains array with cleanedLines
			
			
			//Scanner to read file
			Scanner readFile = new Scanner(file);
			
			//Create BufferedWriter to write new lines to output file
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			
			//Loop over until there no lines left
			while(readFile.hasNext()) {
				
				//Load first line into lineRead
				lineRead = readFile.nextLine();
				
				//Remove all the commas
				cleanedLine = lineRead.replaceAll(",","");
				
				//Add new commas
				cleanedLine = addCommas(cleanedLine);
				
				//Add final string to array
				cleaned.add(cleanedLine);
				
			}
			
			//Close scanner
			readFile.close();
			
			//Load strings into temp file
			for(String str : cleaned) {
				
				//Write string to output file
			    writer.write(str + System.getProperty("line.separator"));
				
			}
			
			//Close Buffered Writer
			writer.close();
			
			//Change tempFile name to file name to keep the same file
			tempFile.renameTo(file);
			
		}
	/*
	 * This method removes substrings from a file based on if it has a comma, or removes a whole line if it starts with a number.
	 * @param fileName The name of the file
	 * @param isComma Flag to determine whether a comma is removed, or a whole line if it starts with a number.
	 */
	public void initialRmv(boolean isComma) throws IOException {
		
		File inputFile = new File(fileName);
		File tempFile = new File("Changes.csv");
		 
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
		String currentLine;
		currentLine = reader.readLine();
		
		int count = 0;
		
		while(currentLine != null && (lineCount-1) != count) {
			
	    		String trimmedLine = currentLine.trim();
		    String finalLine;
		    finalLine = trimmedLine;
		    
		    if(trimmedLine.startsWith(",") && isComma)
		    		finalLine = trimmedLine.substring(1);
		    
		    
		    writer.write(finalLine + System.getProperty("line.separator"));
		    count++;
			currentLine = reader.readLine();
		}
		 
		writer.close(); 
		reader.close(); 
		
		tempFile.renameTo(inputFile);
		
	}
	/*
	 * This method removes empty lines from the file
	 * @param fileName the name/location of the file being processed
	 */
	public void emptyLineRmv(String fileName) throws IOException{
		
		//For debugging
		
		File inputFile = new File(fileName);
		File tempFile = new File("Changes.csv");
		 
		Scanner reader = new Scanner(inputFile);
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
		while (reader.hasNext()) {
			
			String line = reader.nextLine();
			if(!line.isEmpty()) {
				
				writer.write(line);
				writer.write("\n");
			}
			
		}
		
		reader.close();
		writer.close();
		
		tempFile.renameTo(inputFile);
		
	}
	/*
	 * This method returns the number of lines in the file
	 * @param fileName the name/location of the file being processed
	 */
	public int getLineCount() throws FileNotFoundException {
		
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		
		int count = 0;
		
		while (scanner.hasNextLine()) {
			  count++;
			  scanner.nextLine();
			}
		
		scanner.close();
		
		return count;
		
		
	}
}

