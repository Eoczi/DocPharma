package pharmacy;

public class Employee {
	private int empID,empSal;
	private String empName,empStatus;
	
	public Employee(int empID,String empName, int empSal,  String empStatus) {
		super();
		this.empID = empID;
		this.empSal = empSal;
		this.empName = empName;
		this.empStatus = empStatus;
	}

	public int getEmpID() {
		return empID;
	}

	public int getEmpSal() {
		return empSal;
	}

	public String getEmpName() {
		return empName;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}

	public void setEmpSal(int empSal) {
		this.empSal = empSal;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	
	
}
