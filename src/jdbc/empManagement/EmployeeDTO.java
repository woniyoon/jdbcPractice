package jdbc.empManagement;

public class EmployeeDTO {
	
	private int empID;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String birthDate;
	private String hiredDate;
	private DepartmentDTO departmentInfo;
	private SalaryDTO salaryInfo;
	private String fullName;
	
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public DepartmentDTO getDepartmentInfo() {
		return departmentInfo;
	}
	public void setDepartmentInfo(DepartmentDTO departmentInfo) {
		this.departmentInfo = departmentInfo;
	}
	public SalaryDTO getSalaryInfo() {
		return salaryInfo;
	}
	public void setSalaryInfo(SalaryDTO salaryInfo) {
		this.salaryInfo = salaryInfo;
	}
	public String getHiredDate() {
		return hiredDate;
	}
	public void setHiredDate(String hiredDate) {
		this.hiredDate = hiredDate;
	}
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(String firstName, String lastName) {
		this.fullName = firstName + " " + lastName;
	}
	
	
	public String showEmployeeInfo() {
//		String fullName = firstName + " " + lastName + "\t\t" ;
		
		return empID +"\t" + departmentInfo.getDepartment()+ "\t" + fullName + birthDate; 
	}
	
	public String showContacts() {
//		String fullName = firstName + " " + lastName + "\t\t" ;

		return departmentInfo.getDepartment()+ "\t" + fullName + "\t" + email + "\t" + mobile; 
	}
	
	public String showBPeople() {
		return birthDate + "\t" + departmentInfo.getDepartment() + "\t" + fullName;
	}
	
	public String showLoyalEmps() {
		return hiredDate + "\t" + departmentInfo.getDepartment() + "\t" + fullName;
	}

	public String showSupervisor() {
		String supervisorInfo = "부서 : " + departmentInfo.getDepartment() +"\n성함 : " + fullName + "\n";
		
		return supervisorInfo;
	}
}
