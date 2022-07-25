import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CleanEntrys extends CollectEntrys{
	
	//Variable declarations
	private ArrayList<Entry> entArry;
	private boolean isIKI;
	private String fileName;
	
	public CleanEntrys(String fileName, Entry[] entArry, boolean isI) {
		
		//Save params
		this.fileName = fileName;
		this.entArry = new ArrayList<Entry>(Arrays.asList(entArry));
		this.isIKI = isIKI;
		
		//Get rid of 0 entry's
		zeroEntry();
		
		//Check differentials
		if(isI)
			differential();
		
		//set super class entArr
		toArray(this.entArry);
		
		//Organize the entry's
		organize();
		
	}
	
	/*
	 * This method cleans any 
	 * empty pay amounts inside the entry array
	 */
	public void zeroEntry() {
		
		
		for(int i = 0; i < entArry.size(); i++) {
			
			
			if(entArry.get(i).getPayAm() == 0.0)
				entArry.remove(i);
				
				
		}
		
	}
	
	/*
	 * differential checks for differentials in the entry
	 * list
	 */
	public void differential() {
		
		//Loop over entry array
		for(int i = 0; i < entArry.size(); i++) {
			
			if(i < entArry.size()-2) {
				
				//Check if there's a double hourly entry
				if(entArry.get(i).getPayType().equals(entArry.get(i+1).getPayType()) && 
						entArry.get(i).getEmpID() == entArry.get(i+1).getEmpID()){
					
					//Change it to a Differential payType
					entArry.get(i+1).setPayType("DIFFERENTIAL");
					
					//Put conditional in here to make sure I doesn't go over bounds
					if(i < entArry.size()-4) {
						
						//Check to see if there is OT
						if(entArry.get(i+2).getPayType().equals("OT")) {
							
							//Get the amount of OT and add it to the differential
							//payAm
							entArry.get(i+1).setPayAm(entArry.get(i+1).getPayAm() + entArry.get(i+2).getPayAm());
							
							//Delete the secondary OT
							entArry.remove(i+3);
							
						}
					
					}
					
				}
				
			}
				
		}
		
	}
	
	/*
	 * toArray method changes the entry ArrayList to an entry Array
	 * @param tempArr the ArrayList to be turned into an Array
	 */
	public void toArray(ArrayList<Entry> tempArr) {
		
		entArr = new Entry[tempArr.size()];
		
		for(int i = 0; i < entArr.length; i++) {
			
			entArr[i] = tempArr.get(i);
			
		}
		
		
	}
	
	/*
	 * organize method organizes the entry's
	 */
	public void organize() {
		
		//Variable declarations
		Entry temp;		//Holds temp position in organizing algorithm
		
		//Special cases
		if(isI) {
			
			for(int i = 0; i < entArr.length; i++) {
				
				if(entArr[i].getEmpID() == 0)			//Removed numbers to censor
					entArr[i].setEmpID(0);
				else if(entArr[i].getEmpID() == 0)
					entArr[i].setEmpID(0);
				
			}
			
		}
		int i;
		for(i = 0; i < entArr.length; i++) {
			
			for(int j = i + 1; j < entArr.length; j++) {
				
				if(entArr[i].getEmpID() > entArr[j].getEmpID()) {
					
					temp = new Entry(entArr[i]);
					entArr[i] = new Entry(entArr[j]);
					entArr[j] = new Entry(temp);
				}
			}
			
		}
		
	}
	
	/*
	 * toFile method outputs the entry array into a file
	 * @param payWeek the pay week the user has entered
	 */
	public void toFile(String payWeek) throws IOException {
		
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file);
		
		//Config top row of file (based on CSV)
		if(isIKI)
			writer.write(",,IKI" + " PP:" + payWeek + ",," + "\n");
		else
			writer.write(",,TJP" + " PP:" + payWeek + ",," + "\n");
		writer.write("ID #, Name, Paytype, Pay Amount, Department" + "\n");
		
		for(int i = 0; i < entArr.length; i++) {
			
			if(entArr[i].getPayType().equals("DIFFERENTIAL"))
				writer.write(entArr[i].getEmpID() + "," +
							entArr[i].getEmpNm() + "," +
							entArr[i].getPayType() + "," +
							entArr[i].getPayAm() + "$," +
							entArr[i].getDept() + "\n");
			else
				writer.write(entArr[i].getEmpID() + "," +
						entArr[i].getEmpNm() + "," +
						entArr[i].getPayType() + "," +
						entArr[i].getPayAm() + "," +
						entArr[i].getDept() + "\n");
				
			
		}
		
		writer.close();
		
	}
	
}
