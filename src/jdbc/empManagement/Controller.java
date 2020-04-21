package jdbc.empManagement;

import java.util.ArrayList;

public class Controller {

	private static Controller controller = new Controller();
	private static InterEmpDAO empDAO = new EmpDAO();
	
	private Controller() {
		
	}

	public static Controller getController (){
		return controller;
	}

	public ArrayList<EmployeeDTO> getEmps() {
		ArrayList<EmployeeDTO> empList = null;
		
		empList = empDAO.getEmps();
		return empList;
	}

	public ArrayList<EmployeeDTO> getContacts() {
		ArrayList<EmployeeDTO> contactsList = null;
		
		contactsList = empDAO.getContacts();
		return contactsList;
	}
}
