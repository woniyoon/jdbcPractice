package jdbc.empManagement;

import java.util.*;

public class AppMain {
	
	static AdminDTO administrator = null;
	static Scanner sc = new Scanner(System.in);
	static Controller controller = Controller.getController();

	public static void main(String[] args) {
		boolean isOn = true;
		
		do {
			showMainMenu();
			String option = sc.nextLine();
			
			switch (option) {
			case "1":
				ArrayList<EmployeeDTO> empList = controller.getEmps();
				System.out.println("-------------------------------------------------");
				System.out.println("사원번호\t부서명\t\t사원명\t\t생년월일");
				
				for(EmployeeDTO emp : empList) {
					System.out.println(emp.showEmployeeInfo());
				}
				
				System.out.println("-------------------------------------------------\n");
				break;
				
			case "2":
				ArrayList<EmployeeDTO> contactsList = controller.getContacts();
				
				System.out.println("-------------------------------------------------");
				System.out.println(" 부서명\t\t사원명\t\t전화번호\t이메일");
				for(EmployeeDTO emp : contactsList) {
					System.out.println(emp.showContacts());
				}
				System.out.println("-------------------------------------------------\n");

				break;
			case "3":
				
				break;
			case "4":
				
				break;
			case "5":
				
				break;
			case "6":
				
				break;
			case "0":
				isOn = false;
				break;

			default:
				System.out.println(">>> 메뉴에 없는 번호를 선택했습니다. ");
				break;
			}
			
			
		} while(isOn);
		
		System.out.println(">>> 시스템이 종료됐습니다. <<<");
		sc.close();
	}
	
	public static void showMainMenu() {
		System.out.println("----------메뉴----------");
		System.out.println(" 1. 사원 목록 ");
		System.out.println(" 2. 사원 연락망 ");
		System.out.println(" 3. 행사 ");
		System.out.println(" 4. 사원별 직속상사 ");
		if(administrator == null) {
			System.out.println(" 5. HR 전용 ");			
		} else {
			System.out.println(" 5. 사원별 급여 확인 ");
			System.out.println(" 6. 퇴직금 확인 ");
		}
		System.out.println(" 0. 시스템 종료 ");
	}

}





//1. 연락처(사원 이름을 넣었을때 부서/연락처/이메일 뜨게)
//2. 행사(예시 : 4월 한달간 생일자, 입사자 정보 띄우게 하기/ 년도는 무시)
//3. 직속상사가 누구인지(표시되는 정보는
//사원번호, 이름, 이메일, 연락처, 부서명)
//4. 급여(이것은 steven king만 접속 가능, 암호는 스티븐킹만 부여해서 진행)
//4-1 급여계산
//4-2 퇴직금계산