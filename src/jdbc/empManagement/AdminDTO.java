package jdbc.empManagement;

public class AdminDTO {

	private int empID;
	private String firstName;
	private String lastName;
	private String fullName;
	private String passwd;
	private String email;	
	private DepartmentDTO departmentInfo;
	
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public void setFullName(String fisrtName, String lastName) {
		this.fullName = firstName + " " + lastName;
	}
	public String getFullName() {
		return this.fullName;
	}
	public void setDepartmentInfo(DepartmentDTO departmentInfo) {
		this.departmentInfo = departmentInfo;
	}
	public DepartmentDTO getDepartmentInfo() {
		return this.departmentInfo;
	}

}
