import java.util.StringTokenizer;

/*
 * This class represents an entry from the cleaned file
 */
public class Entry{
	//Variable declarations
	int empID;				//ID of employee
	String empNm;			//Name of entry
	String payType;			//Pay Type
	double payAm;			//Amount of pay
	String dept = "N/A";		//Department for entry
	/*
	 * Constructor
	 */
	public Entry(String entry) {
		
		//Spilt the entry
		StringTokenizer split = new StringTokenizer(entry, ",", false);
		
		//Save the entry into the objects fields
		empID = Integer.parseInt(split.nextToken());
		empNm = split.nextToken();
		payType = split.nextToken();
		payAm = Double.parseDouble(split.nextToken());
		//Check to see if it has a department or not
		if(split.countTokens() == 1)
			dept = split.nextToken();
		
		
	}
	
	public Entry(Entry entry) {
		
		this.empID = entry.getEmpID();
		this.empNm = entry.getEmpNm();
		this.payType = entry.getPayType();
		this.payAm = entry.getPayAm();
		this.dept = entry.getDept();
		
	}
	/*
	 * toString method
	 */
	public String toString() {
		
		String str;
		
		str = "Employee ID: " + empID +
				"\nEmployee Name: " + empNm +
				"\nEmployee PayType: " + payType +
				"\nEmployee Pay Amount : " + payAm;
		
		return str;
		
	}
	/*
	 * equals method
	 */
	public boolean equals(Entry ent) {
		
		boolean eq = true;
		
		if(empID != ent.getEmpID())
			eq = false;
		else if(!empNm.equals(ent.getEmpNm()))
			eq = false;
		else if(!payType.equals(ent.getPayType()))
			eq = false;
		else if(payAm != ent.getPayAm())
			eq = false;
		else if(dept.equals(ent.getDept()))
			eq = false;
			
		return eq;
		
	}
	/*
	 * getEmpID method returns employee ID
	 */
	public int getEmpID() {
		
		return empID;
		
	}
	/*
	 * getEmpNm method returns employee name
	 */
	public String getEmpNm() {
	
		return empNm;
		
	}
	/*
	 * getPayType method returns employee payType
	 */
	public String getPayType() {
		
		return payType;
		
	}
	/*
	 * getPayAm returns pay amount
	 */
	public double getPayAm() {
		
		return payAm;
		
	}
	/*
	 * getDept returns department
	 */
	public String getDept() {
		
		return dept;
		
	}
	/*
	 * setEmpID set the employee ID
	 */
	public void setEmpID(int empID){
		
		this.empID = empID;
		
	}
	/*
	 * setPayType sets the entry pay type
	 */
	public void setPayType(String payType) {
		
		this.payType = payType;
		
	}
	/*
	 * setPayAm sets the entry pay amount
	 */
	public void setPayAm(double payAm) {
		
		this.payAm = payAm;
		
	}
}
