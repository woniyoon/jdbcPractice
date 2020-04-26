package jdbc.empManagement;

import java.util.*;

public class AppMain {
	
	static AdminDTO administrator = null;
	static Scanner sc = new Scanner(System.in);
	static Controller controller = Controller.getController();

	public static void main(String[] args) {
		boolean isOn = true;
		HashMap<String, String> paraMap = new HashMap<String, String>();
		
		do {
			showMainMenu();
			String option = sc.nextLine();
			String first_name = "";
			String last_name = "";
			
			switch (option) {
			case "1":
				ArrayList<EmployeeDTO> empList = controller.getEmps();
				System.out.println("--------------------------------------------------");
				System.out.println("사원번호\t부서명\t\t사원명\t\t생년월일");
				
				for(EmployeeDTO emp : empList) {
					System.out.println(emp.showEmployeeInfo());
				}
				
				System.out.println("--------------------------------------------------\n");
				break;
				
			case "2":
				ArrayList<EmployeeDTO> contactsList = controller.getContacts();
				
				System.out.println("--------------------------------------------------");
				System.out.println(" 부서명\t\t사원명\t\t전화번호\t이메일");
				
				for(EmployeeDTO emp : contactsList) {
					System.out.println(emp.showContacts());
				}
				
				System.out.println("--------------------------------------------------\n");

				break;
			case "3":
				ArrayList<EmployeeDTO> bPeople = controller.getBPeople();
				ArrayList<EmployeeDTO> loyalEmps = controller.getLoyalEmps();
				
				System.out.println("---------------------🎉이번달의 생일자🎉---------------------");
				
				for(EmployeeDTO emp : bPeople) {
					System.out.println(emp.showBPeople());
				}
				
				System.out.println("\n--------------------------------------------------\n");
				
				System.out.println("---------------------🎉입사 기념일🎉---------------------");
				
				for(EmployeeDTO emp : loyalEmps) {
					System.out.println(emp.showLoyalEmps());
				}
				
				System.out.println("\n--------------------------------------------------\n");
				
				break;
			case "4":
				System.out.print("▷ 조회를 원하는 사원의 이름을 입력하세요 : ");
				first_name = sc.nextLine();
				System.out.print("▷ 조회를 원하는 사원의 성을 입력하세요 : ");
				last_name = sc.nextLine();
				
				if("Steven".equalsIgnoreCase(first_name) && "King".equalsIgnoreCase(last_name)) {
					System.out.println(">>> Steven King의 직속 사수는 존재하지 않습니다. \n");
				} else {
					paraMap.put("first_name", first_name);
					paraMap.put("last_name", last_name);
					
					EmployeeDTO supervisor = controller.getSupervisor(paraMap);					

					if(supervisor != null) {
						System.out.println("------------" + first_name + " " + last_name + "의 직속 사수 ------------");
					 	System.out.println(supervisor.showSupervisor());
					 	System.out.println(" ");
					} else {
						System.out.println(">>> " + first_name + " " + last_name + "는 존재하지 않는 사원입니다.\n");
					}
				}
				
				break;
			case "5":
				if(administrator == null) {

					System.out.print("▷ 관리자 아이디 : ");
					String adminID = sc.nextLine();
					System.out.print("▷ 비밀번호 : ");
					String password = sc.nextLine();
					HashMap<String, String> adminMap = new HashMap<String, String>();
					
					adminMap.put("adminID", adminID);
					adminMap.put("password", password);
					
					AdminDTO admin = controller.adminLogin(adminMap);
					
					if(admin == null) {
						System.out.println(">>> 관리자만 접근 권한이 있습니다!\n");
					} else {
						administrator = admin;
						System.out.println(">>> " + admin.getFirstName() + " 관리자님 로그인 성공!\n");
					}
					
				} else {
					System.out.print("▷ 조회를 원하는 사원의 이름을 입력하세요 : ");
					first_name = sc.nextLine();
					System.out.print("▷ 조회를 원하는 사원의 성을 입력하세요 : ");
					last_name = sc.nextLine();
					
					EmployeeDTO emp = controller.inquireSalary(first_name, last_name);
					
					if (emp != null && emp.getSalaryInfo().getSalary() != 0) {
						System.out.println("----------------------------------------------------------------");
						System.out.println(" 부서명\t\t 이름\t\t 급여\t");
						System.out.println("----------------------------------------------------------------");
						System.out.println(" " + emp.getDepartmentInfo().getDepartment() + "\t" + emp.getFullName() + "\t$" + emp.getSalaryInfo().getSalary());
						System.out.println("----------------------------------------------------------------\n");

					} else {
						System.out.println(">>> 존재하지 않은 사원의 이름을 입력하셨습니다! \n");
					}
					
				}
				
				break;
			case "6":
				System.out.print("▷ 조회를 원하는 사원의 이름을 입력하세요 : ");
				first_name = sc.nextLine();
				System.out.print("▷ 조회를 원하는 사원의 성을 입력하세요 : ");
				last_name = sc.nextLine();
				
				EmployeeDTO emp = controller.inquireRetirementMoney(first_name, last_name);
				
				if (emp != null && emp.getSalaryInfo().getRetirementMoney() != 0) {
					System.out.println("----------------------------------------------------------------");
					System.out.println(" 부서명\t\t 이름\t\t 퇴직금\t");
					System.out.println("----------------------------------------------------------------");
					System.out.println(" " + emp.getDepartmentInfo().getDepartment() + "\t\t" + emp.getFullName() + "\t$" + emp.getSalaryInfo().getRetirementMoney());
					System.out.println("----------------------------------------------------------------\n");

				} else {
					System.out.println(">>> 존재하지 않은 사원의 이름을 입력하셨습니다! \n");
				}
				
				break;
			case "7":
				administrator = null;
				System.out.println(">>> 로그아웃 됐습니다! \n");
				break;
			case "0":
				isOn = false;
				break;

			default:
				System.out.println(">>> 메뉴에 없는 번호를 선택했습니다. \n");
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
		System.out.println(" 3. 이 달의 행사 ");
		System.out.println(" 4. 사원별 직속상사 ");
		if(administrator == null) {
			System.out.println(" 5. HR 전용 ");			
		} else {
			System.out.println(" 5. 사원별 급여 확인 ");
			System.out.println(" 6. 퇴직금 확인 ");
			System.out.println(" 7. 로그아웃");
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