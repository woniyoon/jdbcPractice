package jdbc.empManagement;

import java.util.ArrayList;

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
}
