package jdbc.empManagement;

public class Controller {

	private static Controller controller = new Controller();

	private Controller() {
		
	}

	public static Controller getController (){
		return controller;
	}
}
