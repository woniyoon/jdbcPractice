package jdbc.empManagement;

import java.util.*;

public interface InterEmpDAO {
	public ArrayList<EmployeeDTO> getEmps();
	public ArrayList<EmployeeDTO> getContacts();
	public ArrayList<EmployeeDTO> getBPeople();
	public ArrayList<EmployeeDTO> getLoyalEmps();
}
