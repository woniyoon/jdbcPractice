package jdbc.empManagement;

public class AdminDTO {

	private int empID;
	private String firstName;
	private String lastName;
	private String passwd;
	private String email;	
	
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
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPasswd() {
		return passwd;
	}
	

}
