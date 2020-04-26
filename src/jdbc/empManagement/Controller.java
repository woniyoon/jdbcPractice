package jdbc.empManagement;

import java.util.*;

public class Controller {

	private static Controller controller = new Controller();
	private static InterEmpDAO empDAO = new EmpDAO();
	
	private Controller() {}

	public static Controller getController (){
		return controller;
	}

	public ArrayList<EmployeeDTO> getEmps() {		
		return empDAO.getEmps();
	}

	public ArrayList<EmployeeDTO> getContacts() {		
		return empDAO.getContacts();
	}

	public ArrayList<EmployeeDTO> getBPeople() {
		return empDAO.getBPeople();
	}
	
	public ArrayList<EmployeeDTO> getLoyalEmps() {
		return empDAO.getLoyalEmps();
	}

	public EmployeeDTO getSupervisor(HashMap<String, String> paraMap) {
		return empDAO.getSupervisor(paraMap);
	}

	public AdminDTO adminLogin(HashMap<String, String> adminMap) {		
		return empDAO.adminLogin(adminMap);
	}

	public EmployeeDTO inquireSalary(String emp_firstName, String emp_lastName) {
		return empDAO.inquireSalary(emp_firstName, emp_lastName);
	}
}
