package jdbc.day03.board;

import java.util.*;

public class BoardView_Main {
	//	게시판의 내용(메뉴, 결과물,...)을 보여주는 곳
	//  무엇인가 입력화면이나 출력결과물을 보여주는 곳 :  View단

	static Scanner sc = new Scanner(System.in);
	
	//	시작메뉴
	static void showMenu() {
		System.out.println("\n------------------ 시작메뉴 ------------------");
		System.out.println(" 1.로그인		2.회원가입		3.프로그램 종료");
		System.out.println("--------------------------------------------\n");
		System.out.print("▷ 메뉴 선택 : ");
	}	
	
	
	//	로그인 메뉴
	static MemberDTO login() {
		MemberDTO member = null;
		
		System.out.print("▷ 아이디 : ");
		String userid = sc.nextLine();
		System.out.print("▷ 암호 : ");
		String passwd = sc.nextLine();
		
		InterMemberDAO mbrDAO = new MemberDAO();
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userid", userid);
		paraMap.put("passwd", passwd);
		
		member = mbrDAO.login(paraMap);
		
		if(member != null) {
			System.out.println("\n>>> 로그인 성공 ! ");
		} else {
			System.out.println("\n>>> 로그인 실패 ! ");
		}
		return member;
	}
	
		
	//	회원가입 메뉴
	static void signUp() {
		System.out.println("\n >>> --- 회원가입 --- <<<");
		
		System.out.print("1. 아이디 : ");
		String userid = sc.nextLine();
		System.out.print("2. 암호 : ");
		String passwd = sc.nextLine();
		System.out.print("3. 회원명 : ");
		String name = sc.nextLine();
		System.out.print("4. 연락처 : ");
		String mobile = sc.nextLine();
	
		MemberDTO member = new MemberDTO();
		member.setUserid(userid);
		member.setPasswd(passwd);
		member.setName(name);
		member.setMobile(mobile);
		
		InterMemberDAO mbrDAO = new MemberDAO();
		
		int n = mbrDAO.register(member);
	
		if(n == 1) {
			System.out.println("\n >>> 회원가입 축하 !");
		} else {
			System.out.println("\n >>> 회원가입 실패 !");
		}
	}
	
	
	public static void main(String[] args) {
		String mainMenuNo;
		
		do {
			showMenu();
			
			mainMenuNo = sc.nextLine();
			
			switch (mainMenuNo) {
			case "1":
				MemberDTO loginUser = login();
				break;
			case "2":
				signUp();
				break;
			case "3":
				
				break;

			default:
				System.out.println(">> 시작 메뉴에 없는 번호임");
				break;
			}
			
		} while(!"3".equals(mainMenuNo));
		
		
		System.out.println(">>>> 프로그램 종료! ");
		sc.close();
		
	}

}
