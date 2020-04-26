package jdbc.empManagement;

import java.util.*;

public interface InterEmpDAO {
	public ArrayList<EmployeeDTO> getEmps();
	public ArrayList<EmployeeDTO> getContacts();
	public ArrayList<EmployeeDTO> getBPeople();
	public ArrayList<EmployeeDTO> getLoyalEmps();
	public EmployeeDTO getSupervisor(HashMap<String, String> name);
	public AdminDTO adminLogin(HashMap<String, String> adminMap);
	public EmployeeDTO inquireSalary(String emp_firstName, String emp_lastName);
	public EmployeeDTO inquireRetirementMoney(String first_name, String last_name);
}
