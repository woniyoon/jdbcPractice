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

	public EmployeeDTO inquireSalary(String first_name, String last_name) {
		return empDAO.inquireSalary(first_name, last_name);
	}

	public EmployeeDTO inquireRetirementMoney(String first_name, String last_name) {
		return empDAO.inquireRetirementMoney(first_name, last_name);
	}
}
